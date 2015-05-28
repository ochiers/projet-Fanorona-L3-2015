package engine;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import AI.HumanPlayer;
import IHM.Affichage;

/**
 * Classe representant une partie. Pour lancer le jeu il faut faire appel a la methode jouer()
 * 
 * @author soulierc
 *
 */
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -1634914624217639082L;

	/**
	 * Indique que e jeu est arreter, le passage a true a pour effet de terminer la partie
	 */
	public boolean						stopped;
	/**
	 * Indique que la partie s'est terminee normalement avec un vainqueur, la partie est arretée apres cela
	 */
	public boolean						finish;
	/**
	 * Indique si la partie est en pause
	 */
	private boolean						paused;
	/**
	 * Indique si un combo est en cours, le pion correspondant est dans l'attribu this.pionCombo
	 */
	public boolean						enCombo;
	/**
	 * Le joueur qui est en train de jouer
	 */
	public Player						joueurCourant;
	/**
	 * Le joueur qui les pions blancs
	 */
	public Player						joueurBlanc;
	/**
	 * Le joueur qui a les pions noirs
	 */
	public Player						joueurNoir;
	/**
	 * Le joueur qui a gagner, est renseigné uniquement quand la partie c'est terminee normalement (this.finish == true)
	 */
	private Player						winner;

	/**
	 * Tableau de case representant tous le plateau
	 */
	public Case[][]						matricePlateau;

	/**
	 * Nombre de pions blancs restants sur le plateau
	 */
	public int							nombrePionBlanc;
	/**
	 * Nombre de pions noirs restants sur le plateau
	 */
	public int							nombrePionNoir;
	/**
	 * Hauteur du plateau
	 */
	public int							nbLignes;
	/**
	 * Largeur du plateau
	 */
	public int							nbColonnes;
	/**
	 * Le module d'affichage
	 */
	public transient Affichage			display;

	/**
	 * Liste des coups du combo courant, sert à respecter la regle qui dit qu'on ne peut pas revenir sur une case deja jouee
	 */
	public transient ArrayList<Case>	combo;

	/**
	 * Module d'annuler refaire
	 */
	public UndoRedo<Game>				annulerRefaire;

	/**
	 * Pion qui fait le combo en cours ( == null si this.enCombo==False)
	 */
	public Case							pionCombo;

	/**
	 * Permet de savoir si le joueur veut terminer son tour (uniquement possible durant un combo enCombo==True)
	 */
	private boolean						finirSonTour;

	/**
	 * Cree une nouvelle partie avec un module d'affichage, deux joueurs blanc et noirs et un plateu de largeur*hauteur
	 * 
	 * @param affichage
	 *            L'affichage du jeu
	 * @param undoRedo
	 *            Le module de gestion du annuler/refaire
	 * 
	 * @param joueurQuiCommence
	 *            0 -> Blanc qui commence, 1 -> Noir qui commence
	 * @param p1
	 *            Joueur Blanc
	 * @param p2
	 *            Joueur Noir
	 * @param size
	 *            Taille du plateau size.height = nombre de lignes size.width = nombre de colonnes
	 */
	public Game(Affichage affichage, UndoRedo<Game> undoRedo, int joueurQuiCommence, Player p1, Player p2, Dimension size)
	{
		this.stopped = false;
		this.finish = false;
		this.enCombo = false;
		this.paused = true;
		this.joueurBlanc = p1;
		this.joueurNoir = p2;
		if (joueurQuiCommence == 0)
			this.joueurCourant = p1;
		else
			this.joueurCourant = p2;

		this.combo = new ArrayList<Case>();
		this.display = affichage;
		this.nbColonnes = size.width;
		this.nbLignes = size.height;
		this.nombrePionBlanc = ((nbLignes * nbColonnes) - 1) / 2;
		this.nombrePionNoir = this.nombrePionBlanc;
		this.annulerRefaire = undoRedo;
		initialisation(nbLignes, nbColonnes);
	}

	/**
	 * Constructeur par copie Ne copie pas les joueurs
	 * 
	 * @param game
	 *            La partie a copier
	 */
	public Game(Game game)
	{
		this.annulerRefaire = game.annulerRefaire;
		this.combo = new ArrayList<Case>();
		this.display = game.display;
		this.enCombo = false;
		this.finish = game.finish;
		this.joueurBlanc = null;
		this.joueurNoir = null;
		this.joueurCourant = null;
		this.matricePlateau = copyMatrice(game.matricePlateau);
		this.nbColonnes = game.nbColonnes;
		this.nbLignes = game.nbLignes;
		this.nombrePionBlanc = game.nombrePionBlanc;
		this.nombrePionNoir = game.nombrePionNoir;
		this.paused = game.paused;
		this.stopped = game.stopped;
		this.winner = game.winner;
	}

	public static Case[][] copyMatrice(Case[][] courant)
	{
		Case[][] tableau = new Case[courant.length][courant[0].length];
		for (int i = 0; i < courant.length; i++)
			for (int j = 0; j < courant[0].length; j++)
				tableau[i][j] = courant[i][j].clone();

		return chainage(courant.length, courant[0].length, tableau);
	}

	/**
	 * Initialise le plateau du jeu
	 * 
	 * @param nbLignes
	 *            (5)
	 * @param nbColonne
	 *            (9 ou 5)
	 */
	public void initialisation(int nbLignes, int nbColonne)
	{
		Case[][] tableau = new Case[nbLignes][nbColonne];

		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonne; j++)
				tableau[i][j] = new Case(new Coordonnee(i, j));

		tableau = chainage(nbLignes, nbColonne, tableau);

		for (int i = 0; i < Math.floor((double) nbLignes / 2.0); i++)
			for (int j = 0; j < nbColonne; j++)
				tableau[i][j].pion = Pion.Noir;

		for (int i = (int) Math.floor((double) nbLignes / 2.0) + 1; i < nbLignes; i++)
			for (int j = 0; j < nbColonne; j++)
				tableau[i][j].pion = Pion.Blanc;

		for (int j = 0; j < nbColonne / 2; j++)
			if (j % 2 == 0)
				tableau[(int) Math.floor((double) nbLignes / 2.0)][j].pion = Pion.Blanc;
			else
				tableau[(int) Math.floor((double) nbLignes / 2.0)][j].pion = Pion.Noir;

		tableau[nbLignes / 2][nbColonne / 2].pion = null;

		for (int j = nbColonne / 2 + 1; j < nbColonne; j++)
			if (j % 2 == 0)
				tableau[(int) Math.floor((double) nbLignes / 2.0)][j].pion = Pion.Noir;
			else
				tableau[(int) Math.floor((double) nbLignes / 2.0)][j].pion = Pion.Blanc;

		this.matricePlateau = tableau;
	}

	/**
	 * Joue une partie jusqu'a ce qu'un joueur ai gagné ou que la partie a été arretée
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void jouer(String nameJoueur) throws InterruptedException
	{

		System.err.println(nameJoueur + " rentre ne section critique");
		while (!stopped && !nameJoueur.equals(joueurCourant.name))
		{
			wait();
			while (!stopped && paused)
				Thread.sleep(50);
		}

		System.err.println(joueurCourant);
		while (paused)
			Thread.sleep(50);
		if (!finish && !stopped)
		{
			System.err.println(nameJoueur + " debloqu�");

			ArrayList<Case> pionsPossibles = this.lesPionsQuiPeuventManger();
			boolean doitManger = true;
			if (pionsPossibles.size() == 0)
			{
				System.err.println("Aucun pion ne peut manger");
				pionsPossibles = this.lesPionsJouables();
				doitManger = false;
			}
			// TODO : FAIRE FONCTION D'ELIMINATION DOUBLON DE LA LISTE
			// pionPossibles

			afficherList(pionsPossibles, "PIONS POSSIBLES");
			display.afficherPionsPossibles(pionsPossibles);

			Case[] tmp = new Case[pionsPossibles.size()];
			Coup c = this.joueurCourant.play(pionsPossibles.toArray(tmp));

			while (!stopped && !paused && !this.coupValide(c, pionsPossibles, doitManger))
			{
				c = this.joueurCourant.play(pionsPossibles.toArray(tmp));
			}

			/* Apres que le joueur ai joue on test si le jeu n'a pas ete arrete ou mis en pause */
			while (!stopped && paused)
				Thread.sleep(50);
			/* S'il a ete arrete alors il faut debloquer tout le monde pour terminer la methode play pour que les threads joueurs se termient */
			if (stopped)
			{
				notifyAll();
				return;
			}
			boolean rejouer = faireCoup(c);
			enCombo = rejouer;
			combo = new ArrayList<Case>();
			combo.add(matricePlateau[c.depart.ligne][c.depart.colonne]);

			pionCombo = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
			while (rejouer && !finirSonTour)
			{
				ArrayList<Case> l = this.coupsPourPriseParUnPion(coupsPossiblesPourUnPion(pionCombo), pionCombo);

				l.removeAll(combo);
				if (l.size() == 1 && l.contains(pionCombo))
					l.remove(pionCombo);
				if (l.size() <= 0)
				{
					System.out.println("Plus de possibilites");
					break;
				}
				display.afficherPionDuCombo(pionCombo);
				display.afficherCheminParcouruParleCombo(combo);
				Case t[] = new Case[1];
				t[0] = pionCombo;
				Coup c2 = this.joueurCourant.play(t);
				while (!finirSonTour && !joueurCourant.isStopped() && !comboValide(c2, pionCombo, combo))
					c2 = this.joueurCourant.play(t);
				if (finirSonTour)
					break;
				/* Apres que le joueur ai joue on test si le jeu n'a pas ete arrete ou mis en pause */
				while (!stopped && paused)
					Thread.sleep(50);
				/* S'il a ete arrete alors il faut debloquer tout le monde pour terminer la methode play pour que les threads joueurs se termient */
				if (stopped)
				{
					notifyAll();
					return;
				}
				pionCombo = matricePlateau[c2.arrivee.ligne][c2.arrivee.colonne];
				System.out.println("PION JOUE " + pionCombo);
				combo.add(matricePlateau[c2.depart.ligne][c2.depart.colonne]);
				rejouer = faireCoup(c2);
				System.out.println("PEUT REJOUE ? " + rejouer);
				enCombo = rejouer;
			}

			enCombo = false;
			pionCombo = null;
			finirSonTour = false;
			if (!paused && !stopped)
				annulerRefaire.addItem(new Game(this));

			joueurCourant = (joueurCourant == joueurBlanc) ? joueurNoir : joueurBlanc;
			finish = testVictoire();
			this.display.afficherJeu();
			System.err.println(nameJoueur + " a fini");

			/* Si un joueur a gagner alors il faut areter tous les threads joueurs */
			if (finish)
				finir();
		}
		/* On reveil le thread du joueur suivant pour qu'il joue */
		notifyAll();
	}

	/**
	 * Demarre la partie Les joueurs doivent avoir ete crees
	 * 
	 * @throws InterruptedException
	 */
	public void commencer() throws InterruptedException
	{
		System.err.println("Debut de la partie");
		joueurBlanc.setStopped(false);
		joueurNoir.setStopped(false);
		joueurBlanc.start();
		joueurNoir.start();

		ArrayList<Case> pionsPossibles = this.lesPionsQuiPeuventManger();
		if (pionsPossibles.size() == 0)
		{
			System.err.println("Aucun pion ne peut manger");
			pionsPossibles = this.lesPionsJouables();
		}
		display.afficherPionsPossibles(pionsPossibles);

		joueurBlanc.join();
		joueurNoir.join();
		System.err.println("Partie terminee");
		if (finish)
		{
			System.out.println("victoire");
			winner = joueurCourant;
			display.afficherVictoire(winner);
		}

	}

	/**
	 * Teste si on peut effectuer un combo
	 * 
	 * @param c
	 *            Le coup joué
	 * @param pionJoue
	 * @return Vrai -> si on peut faire le combo, Faux sinon
	 */
	private boolean comboValide(Coup c, Case pionJoue, ArrayList<Case> listCombo)
	{
		boolean res = true;
		if (c == null || c.arrivee == null || c.depart == null)
			return false;
		Case arrivee = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
		Case depart = matricePlateau[c.depart.ligne][c.depart.colonne];
		Direction d = determinerDirection(c.depart, c.arrivee);
		ArrayList<Case> coupsPossibles = coupsPossiblesPourUnPion(depart);
		coupsPossibles.removeAll(listCombo);
		res = res && depart.equals(pionJoue) && !combo.contains(arrivee) && coupsPossibles.contains(arrivee) /* && (coupsPourPriseParUnPion(coupsPossibles, depart).size() != 0) */; // MARCHE PAS CAR IL FAUT TESTER LA DIRECTION
		res = res && (determinerPionsACapturerRaprochement(d, arrivee).size() > 0 || determinerPionsACapturerEloignement(d, depart).size() > 0);
		return res;
	}

	/**
	 * Teste si un coup est valide
	 * 
	 * @param c
	 *            Le coup a verifier
	 * @param pionsPossibles
	 * @param doitManger
	 * @return True -> si coup est valide, False sinon
	 */
	private boolean coupValide(Coup c, ArrayList<Case> pionsPossibles, boolean doitManger)
	{
		if (c == null || c.arrivee == null || c.depart == null)
			return false;

		ArrayList<Coordonnee> l = new ArrayList<Coordonnee>();
		for (int i = 0; i < pionsPossibles.size(); i++)
			l.add(pionsPossibles.get(i).position);
		Direction d = determinerDirection(c.depart, c.arrivee);
		Case arrivee = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
		Case depart = matricePlateau[c.depart.ligne][c.depart.colonne];
		boolean res = (c != null && l.contains(c.depart) && this.matricePlateau[c.arrivee.ligne][c.arrivee.colonne].estVide() && c.depart != c.arrivee);
		if (!doitManger)
			return res;
		else
			res = res && (determinerPionsACapturerRaprochement(d, arrivee).size() > 0 || determinerPionsACapturerEloignement(d, depart).size() > 0);
		return res;
	}

	/**
	 * Evalue le coup joué par le joueur
	 * 
	 * @param c
	 *            Le coup joué par le joueur
	 * @return True -> le joueur peut rejouer, False -> le joueur ne peut pas rejouer
	 */
	private boolean faireCoup(Coup c)
	{
		if (!paused && !stopped && c != null && c.depart != null && c.arrivee != null)
		{
			Direction d = determinerDirection(c.depart, c.arrivee);

			ArrayList<Case> rapprochement = determinerPionsACapturerRaprochement(d, matricePlateau[c.arrivee.ligne][c.arrivee.colonne]);
			ArrayList<Case> eloignement = determinerPionsACapturerEloignement(d, matricePlateau[c.depart.ligne][c.depart.colonne]);

			if (rapprochement.size() == 0 && eloignement.size() == 0)
			{
				this.display.afficherJeu();
				matricePlateau[c.arrivee.ligne][c.arrivee.colonne].pion = matricePlateau[c.depart.ligne][c.depart.colonne].pion;
				matricePlateau[c.depart.ligne][c.depart.colonne].pion = null;
				return false;
			} else if (rapprochement.size() != 0 && eloignement.size() != 0)
			{
				display.afficherMultiDirections(eloignement, rapprochement);
				Case choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);
				while (!rapprochement.contains(choix) && !eloignement.contains(choix))
					choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);

				if (rapprochement.contains(choix))
					capturer(rapprochement);
				else if (eloignement.contains(choix))
					capturer(eloignement);

			} else if (rapprochement.size() != 0 && eloignement.size() == 0)
			{
				capturer(rapprochement);
			} else if (eloignement.size() != 0 && rapprochement.size() == 0)
			{
				capturer(eloignement);
			}
			matricePlateau[c.arrivee.ligne][c.arrivee.colonne].pion = matricePlateau[c.depart.ligne][c.depart.colonne].pion;
			matricePlateau[c.depart.ligne][c.depart.colonne].pion = null;
			this.display.afficherJeu();
			return true;
		}
		return false;
	}

	/**
	 * Methode permettant de retirer reelement les pions de la liste passee en parametres du plateau du jeu
	 * 
	 * @param l
	 *            La liste de case a liberer
	 */
	private void capturer(ArrayList<Case> l)
	{
		Iterator<Case> it = l.iterator();
		while (it.hasNext())
		{
			it.next().pion = null;
			if (joueurCourant == joueurBlanc)
				nombrePionNoir--;
			else
				nombrePionBlanc--;
		}
	}

	/**
	 * Determine les pions qui seront capturer si le coup est reelement joue
	 * 
	 * @param d
	 *            La direction dans la quelle capturer
	 * @param depart
	 *            La case de debut de la capture (cette case doit etre vide on commence la capture la case d'apres)
	 * @return Une liste de cases qui correspond aux pions supprimes
	 */
	private ArrayList<Case> determinerPionsACapturerRaprochement(Direction d, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (d != null && depart != null)
		{
			Case courante = depart;
			Pion p = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

			while (courante != null)
			{
				courante = courante.getCaseAt(d);
				if (courante != null && !courante.estVide() && courante.pion != p)
					res.add(courante);
				else
					break;
			}
		}
		return res;
	}

	/**
	 * Determine les pions qui seront capturer si le coup est reelement joue
	 * 
	 * @param d
	 *            La direction dans la quelle capturer
	 * @param depart
	 *            La case de debut de la capture (cette case doit etre vide on commence la capture sur la case opposee)
	 * @return Une liste de cases qui correspond aux pions supprimes
	 */
	private ArrayList<Case> determinerPionsACapturerEloignement(Direction d, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (d != null && depart != null)
		{
			Case courante = depart;
			Pion p = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

			if (courante.getCaseAt(d).estVide())
			{
				while (courante != null)
				{
					courante = courante.getCaseAt(Direction.oppose(d));
					if (courante != null && !courante.estVide() && courante.pion != p)
						res.add(courante);
					else
						break;
				}
			}
		}
		return res;
	}

	/**
	 * Fonction determinant si un des joueur a capturer tous les pions de l'autre
	 * 
	 * @return True si victoire, False sinon
	 */
	private boolean testVictoire()
	{
		if (nombrePionBlanc == 0)
		{
			winner = joueurNoir;
			return true;
		} else if (nombrePionNoir == 0)
		{
			winner = joueurBlanc;
			return true;
		} else
			return false;
	}

	/**
	 * Met en pause le jeu La methode reprendre() doit etre appelée ensuite
	 */
	public void pause()
	{
		this.paused = true;
	}

	/**
	 * Reprend le jeu qui etait en pause
	 */
	public void reprendre()
	{
		this.joueurBlanc.setStopped(false);
		this.joueurNoir.setStopped(false);
		this.paused = false;
	}

	/**
	 * Donne l'etat de la partie
	 * 
	 * @return True -> le jeu est en pause, False -> le jeu joue actuellement
	 */
	public boolean isPaused()
	{
		return this.paused;
	}

	/**
	 * Donne le gagnant du jeu s'il y en a un (vaut null sinon)
	 * 
	 * @return Un joueur qui est le gagnant
	 */
	public Player getWinner()
	{
		return winner;
	}

	/**
	 * Donne les cases possibles (etant vides) pour un pion se trouvant sur la case c
	 * 
	 * @param c
	 *            La case de depart du pion
	 * @return Une liste de cases accessibles pour ce pion
	 */
	public ArrayList<Case> coupsPossiblesPourUnPion(Case c)
	{

		ArrayList<Case> res = new ArrayList<Case>();
		if (c != null)
		{
			Iterator<Case> it = c.voisins().iterator();
			Case cour;
			while (it.hasNext())
			{
				cour = it.next();
				if (cour.estVide())
					res.add(cour);
			}
		}
		return res;
	}

	/**
	 * Fonction determinant quelle direction le coup est joué
	 * 
	 * @param depart
	 *            Position de départ
	 * @param arrivee
	 *            Position d'arrive
	 * @return La direction du coup
	 */
	public static Direction determinerDirection(Coordonnee depart, Coordonnee arrivee)
	{
		int deplacementColonne = arrivee.colonne - depart.colonne;
		int deplacementLigne = arrivee.ligne - depart.ligne;
		switch (deplacementColonne)
		{
			case -1:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.NordOuest;
					case 0:
						return Direction.Ouest;
					case 1:
						return Direction.SudOuest;
				}
				break;
			case 0:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.Nord;
					case 1:
						return Direction.Sud;
				}
				break;
			case 1:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.NordEst;
					case 0:
						return Direction.Est;
					case 1:
						return Direction.SudEst;
				}
				break;
		}
		// System.err.println("GROS SOUCIS ! IMPOSSIBLE DE DETERMINEE LA DIRECTION DU COUP : Depart : " + depart + ", arrivee : " + arrivee);
		return null;
	}

	/**
	 * Fonction renvoyant les pions du joueur courant qui peuvent se deplacer (en mangeant ou nom)
	 * 
	 * @return Une liste de pions
	 */
	public ArrayList<Case> lesPionsJouables()
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Pion courant = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;
		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonnes; j++)
			{
				if (matricePlateau[i][j].pion == courant)
				{
					for (Case case1 : matricePlateau[i][j].voisins())
					{
						if (case1.estVide())
						{
							res.add(matricePlateau[i][j]);
							break;
						}
					}
				}
			}
		return res;
	}

	/**
	 * Fonction donnant les pions du joueur courant qui peuvent manger ce coup ci
	 * 
	 * @return Une liste de pions
	 */
	public ArrayList<Case> lesPionsQuiPeuventManger()
	{
		ArrayList<Case> temp = this.lesPionsJouables();
		ArrayList<Case> res = new ArrayList<Case>();
		ArrayList<Case> coupsPossibles;
		Iterator<Case> it = temp.iterator();
		while (it.hasNext())
		{
			Case tmp = it.next();
			coupsPossibles = coupsPossiblesPourUnPion(tmp);
			if (coupsPourPriseParUnPion(coupsPossibles, tmp).size() > 0)
				res.add(tmp);
		}
		for (int i = 0; i < res.size(); i++)
			System.out.print(res.get(i));

		return res;
	}

	/**
	 * Donne les cases que si on y va dessus on mange
	 * 
	 * @param coupsPossibles
	 *            Tous les coups possibles de deplacement pour le pion sur case c
	 * @param c
	 *            La case sur laquelle ce trouve le pion qui va manger
	 * @return
	 */
	public ArrayList<Case> coupsPourPriseParUnPion(ArrayList<Case> coupsPossibles, Case c)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (c != null)
		{
			Pion ennemi = (joueurCourant == joueurBlanc) ? Pion.Noir : Pion.Blanc;
			for (Direction d : Direction.values())
			{
				/* Aspiration - Eloignement */
				/*
				 * Pour chaque direction, on test si la case est vide, qu'elle est un coup possibles, et que la case opposée soit un pion ennemi
				 */
				if (c.getCaseAt(d) != null && coupsPossibles.contains(c.getCaseAt(d)) && c.getCaseAt(Direction.oppose(d)) != null && c.getCaseAt(Direction.oppose(d)).pion == ennemi)
					res.add(c.getCaseAt(d));

				/* Percussion - Rapprochement */
				/*
				 * Pour chaque direction, on vérifie que la case visée soit vide, qu'elle soit un coup possible et que la case suivante dans la même direction soit un pion ennemi
				 */
				if (c.getCaseAt(d) != null && coupsPossibles.contains(c.getCaseAt(d)) && c.getCaseAt(d).getCaseAt(d) != null && c.getCaseAt(d).getCaseAt(d).pion == ennemi)
					res.add(c.getCaseAt(d));
			}

			// afficherList(res, "MACHIN");
		}
		return res;

	}

	/**
	 * Teste si une case est jouable
	 * 
	 * @param p
	 *            La coordonnee de la case
	 * @return Vrai si la case est vide, Faux sinon
	 */
	/*
	 * public boolean estJouable(Coordonnee p) { Pion courant = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir; boolean res = false; if (matricePlateau[p.ligne][p.colonne].pion == courant) { for (Case case1 : matricePlateau[p.ligne][p.colonne].voisins()) { res = res || case1.estVide(); } }
	 * return res; }
	 */

	/**
	 * Teste si la case a la coordonee p est voisin de la case a la coordonnee q
	 * 
	 * @param p
	 *            Une case
	 * @param q
	 *            Une case
	 * @return Vrai si p appartien aux voisins de q
	 */
	/*
	 * public boolean estVoisin(Coordonnee p, Coordonnee q) { Case c1 = matricePlateau[p.ligne][p.colonne]; Case c2 = matricePlateau[q.ligne][q.colonne]; return c1.voisins().contains(c2); }
	 */

	public void afficherList(ArrayList<Case> l, String str)
	{
		System.out.println("------------Affichage" + str + "-------------");
		Iterator<Case> it = l.iterator();
		while (it.hasNext())
			System.out.println(it.next());
		System.out.println("------------ Fin Affichage-------------");
	}

	/**
	 * Arrete le jeu, tue les joueurs
	 */
	public void finir()
	{
		this.joueurBlanc.setStopped(true);
		this.joueurNoir.setStopped(true);
		this.joueurBlanc.stop();
		this.joueurNoir.stop();
		this.stopped = true;
	}

	private static Case[][] chainage(int nbLignes, int nbColonne, Case[][] tableau)
	{
		for (int i = 0; i < nbLignes; i++)
		{
			for (int j = 0; j < nbColonne - 1; j++)
				tableau[i][j].est = tableau[i][j + 1];
			for (int j = 1; j < nbColonne; j++)
				tableau[i][j].ouest = tableau[i][j - 1];
		}
		for (int j = 0; j < nbColonne; j++)
		{
			for (int i = 0; i < nbLignes - 1; i++)
				tableau[i][j].sud = tableau[i + 1][j];
			for (int i = 1; i < nbLignes; i++)
				tableau[i][j].nord = tableau[i - 1][j];
		}

		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonne; j++)
			{
				if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))
				{
					if (i > 0 && i < nbLignes - 1 && j > 0 && j < nbColonne - 1)
					{
						tableau[i][j].nordEst = tableau[i - 1][j + 1];
						tableau[i][j].sudEst = tableau[i + 1][j + 1];
						tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (i == 0)
					{
						if (j >= 0 && j != nbColonne - 1)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
						if (j != 0 && j < nbColonne)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
					}
					if (i == nbLignes - 1)
					{
						if (j >= 0 && j != nbColonne - 1)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (j != 0 && j < nbColonne)
							tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (j == 0)
					{
						if (i != 0 && i <= nbLignes)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (i >= 0 && i != nbLignes - 1)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
					}
					if (j == 8)
					{
						if (i >= 0 && i != nbLignes - 1)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						if (i != 0 && i <= nbLignes)
							tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
				}
			}
		return tableau;

	}

	/**
	 * Permet au joueur courant de finir son tour (uniquement possible durant un combo this.enCombo==True)
	 */
	public void finirSonTour()
	{
		if (this.joueurCourant instanceof HumanPlayer)
		{
			this.finirSonTour = true;
			((HumanPlayer) this.joueurCourant).setCoup(null, null);
		}
	}
}

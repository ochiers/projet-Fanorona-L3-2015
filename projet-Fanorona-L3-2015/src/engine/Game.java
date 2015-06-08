package engine;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe representant une partie. Pour lancer le jeu il faut faire appel a la
 * methode commencer()
 * 
 * @author soulierc
 *
 */
public class Game implements Serializable {

	private static final long			serialVersionUID	= -1634914624217639082L;

	/**
	 * Indique que e jeu est arreter, le passage a true a pour effet de terminer
	 * la partie
	 */
	public boolean						stopped;

	/**
	 * Indique que la partie s'est terminee normalement avec un vainqueur, la
	 * partie est arretée apres cela
	 */
	public boolean						finish;

	/**
	 * Indique si la partie est en pause
	 */
	private boolean						paused;

	/**
	 * Indique si un combo est en cours, le pion correspondant est dans
	 * l'attribu this.pionCombo
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
	 * Le joueur qui a gagner, est renseigné uniquement quand la partie c'est
	 * terminee normalement (this.finish == true)
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
	public transient EngineServices		leMoteur;

	/**
	 * Liste des coups du combo courant, sert à respecter la regle qui dit qu'on
	 * ne peut pas revenir sur une case deja jouee
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
	 * Permet de savoir si le joueur veut terminer son tour (uniquement possible
	 * durant un combo enCombo==True)
	 */
	private boolean						finirSonTour;

	/**
	 * Indique qui est le premier joueur, True -> joueur1, False -> joueur2
	 */
	public boolean						premierJoueur;

	/**
	 * Cree une nouvelle partie avec un module d'affichage, deux joueurs blanc
	 * et noirs et un plateu de largeur*hauteur
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
	 *            Taille du plateau size.height = nombre de lignes size.width =
	 *            nombre de colonnes
	 */
	public Game(EngineServices leMoteur, int joueurQuiCommence, Player p1, Player p2, Dimension size)
	{
		this.stopped = false;
		this.finish = false;
		this.enCombo = false;
		this.paused = true;
		this.joueurBlanc = p1;
		this.joueurNoir = p2;
		this.joueurCourant = (joueurQuiCommence == 0) ? p1 : p2;
		this.premierJoueur = joueurQuiCommence == 0;
		this.combo = new ArrayList<Case>();
		this.leMoteur = leMoteur;
		this.nbColonnes = size.width;
		this.nbLignes = size.height;
		this.nombrePionBlanc = ((nbLignes * nbColonnes) - 1) / 2;
		this.nombrePionNoir = this.nombrePionBlanc;
		this.annulerRefaire = leMoteur.getUndoRedo();
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
		this.leMoteur = game.leMoteur;
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
		this.premierJoueur = game.premierJoueur;
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
	 * Joue une partie jusqu'a ce qu'un joueur ai gagné ou que la partie a été
	 * arretée
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void jouer(long idJoueur) throws InterruptedException
	{

		//System.err.println(idJoueur + " rentre en section critique, nom du joueur courant : " + joueurCourant.getIdJoueur());

		while (!stopped && idJoueur != joueurCourant.getIdJoueur())
		{
			//System.err.print(idJoueur + " coincé");
			wait();
			//System.out.print(nameJoueur + " décoincé");
			while (!stopped && paused)
				Thread.sleep(50);
		}

		//System.err.println(joueurCourant.getClass().getCanonicalName() + " " + joueurCourant);
		while (paused)
			Thread.sleep(50);
		if (!finish && !stopped)
		{
			System.err.println(idJoueur + " debloqué");

			ArrayList<Case> pionsPossibles = this.lesPionsQuiPeuventManger();
			boolean doitManger = true;
			if (pionsPossibles.size() == 0)
			{
				pionsPossibles = this.lesPionsJouables();
				doitManger = false;
			}

			leMoteur.getCurrentDisplay().afficherPionsPossibles(pionsPossibles);

			combo = new ArrayList<Case>();

			Case[] tmp = new Case[pionsPossibles.size()];
			Case[][] copiePlateauPourJoueur = copyMatrice(matricePlateau);
			Coup c = this.joueurCourant.play(copiePlateauPourJoueur, pionsPossibles.toArray(tmp));

			while (!stopped && !paused && !this.coupValide(c, pionsPossibles, doitManger))
			{
				c = this.joueurCourant.play(copiePlateauPourJoueur, pionsPossibles.toArray(tmp));
			}

			/*
			 * Apres que le joueur ai joue on test si le jeu n'a pas ete arrete
			 * ou mis en pause
			 */
			while (!stopped && paused)
				Thread.sleep(50);
			/*
			 * S'il a ete arrete alors il faut debloquer tout le monde pour
			 * terminer la methode play pour que les threads joueurs se termient
			 */
			if (stopped)
			{
				notifyAll();
				return;
			}
			this.leMoteur.envoyerCoupSurReseau(c);
			boolean peutRejouer = faireCoup(c);
			enCombo = peutRejouer;
			combo.add(matricePlateau[c.depart.ligne][c.depart.colonne]);

			pionCombo = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
			while (peutRejouer && !finirSonTour)
			{
				ArrayList<Case> l = this.coupsPourPriseParUnPion(coupsPossiblesPourUnPion(pionCombo), pionCombo);

				l.removeAll(combo);
				if (l.size() == 1 && l.contains(pionCombo))
					l.remove(pionCombo);
				if (l.size() <= 0)
					break;
				leMoteur.getCurrentDisplay().afficherPionDuCombo(pionCombo);
				leMoteur.getCurrentDisplay().afficherCheminParcouruParleCombo(combo);
				Case t[] = new Case[1];
				t[0] = pionCombo;
				copiePlateauPourJoueur = copyMatrice(matricePlateau);
				Coup coupCombo = this.joueurCourant.play(copiePlateauPourJoueur, t);
				while (!finirSonTour && !joueurCourant.isStopped() && !comboValide(coupCombo, pionCombo, combo))
					coupCombo = this.joueurCourant.play(copiePlateauPourJoueur, t);
				if (finirSonTour)
					break;
				/*
				 * Apres que le joueur ai joue on test si le jeu n'a pas ete
				 * arrete ou mis en pause
				 */
				while (!stopped && paused)
					Thread.sleep(50);
				/*
				 * S'il a ete arrete alors il faut debloquer tout le monde pour
				 * terminer la methode play pour que les threads joueurs se
				 * termient
				 */
				if (stopped)
				{
					notifyAll();
					return;
				}
				pionCombo = matricePlateau[coupCombo.arrivee.ligne][coupCombo.arrivee.colonne];
				combo.add(matricePlateau[coupCombo.depart.ligne][coupCombo.depart.colonne]);
				this.leMoteur.envoyerCoupSurReseau(coupCombo);
				peutRejouer = faireCoup(coupCombo);
				enCombo = peutRejouer;
			}
			combo.clear();
			enCombo = false;
			pionCombo = null;
			finirSonTour = false;
			if (!paused && !stopped)
				annulerRefaire.addItem(new Game(this));

			finish = testVictoire();
			joueurCourant = (joueurCourant == joueurBlanc) ? joueurNoir : joueurBlanc;
			leMoteur.getCurrentDisplay().afficherJeu();
			//System.err.println(nameJoueur + " a fini, jeu gagné ? : " + finish);

			/*
			 * Si un joueur a gagner alors il faut areter tous les threads
			 * joueurs
			 */
			if (finish)
				finir(false);
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
		joueurBlanc.setStopped(false);
		joueurNoir.setStopped(false);
		joueurBlanc.start();
		joueurNoir.start();

		ArrayList<Case> pionsPossibles = this.lesPionsQuiPeuventManger();
		if (pionsPossibles.size() == 0)
		{
			pionsPossibles = this.lesPionsJouables();
		}
		System.out.println(leMoteur);
		System.out.println(leMoteur.getCurrentDisplay());
		leMoteur.getCurrentDisplay().afficherPionsPossibles(pionsPossibles);

		joueurBlanc.join();
		joueurNoir.join();
		if (finish)
			leMoteur.getCurrentDisplay().afficherVictoire(winner);
	}

	/**
	 * Teste si on peut effectuer un combo
	 * 
	 * @param coupJoue
	 *            Le coup joué
	 * @param pionJoue
	 *            Pion avec lequel on joue
	 * @param listCombo
	 *            Liste contenant les anciennes positions du pion
	 * @return Vrai -> si on peut faire le combo, Faux sinon
	 */
	private boolean comboValide(Coup coupJoue, Case pionJoue, ArrayList<Case> listCombo)
	{
		boolean res = true;
		if (coupJoue == null || coupJoue.arrivee == null || coupJoue.depart == null)
			return false;
		Case arrivee = matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne];
		Case depart = matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne];
		Direction d = determinerDirection(coupJoue.depart, coupJoue.arrivee);
		ArrayList<Case> coupsPossibles = coupsPossiblesPourUnPion(depart);
		coupsPossibles.removeAll(listCombo);
		res = res && depart.equals(pionJoue) && !combo.contains(arrivee) && coupsPossibles.contains(arrivee);
		res = res && (determinerPionsACapturerRaprochement(d, arrivee).size() > 0 || determinerPionsACapturerEloignement(d, depart).size() > 0);
		return res;
	}

	/**
	 * Teste si un coup est valide
	 * 
	 * @param coupJoue
	 *            Le coup a verifier
	 * @param pionsPossibles
	 * @param doitManger
	 * @return True -> si coup est valide, False sinon
	 */
	private boolean coupValide(Coup coupJoue, ArrayList<Case> pionsPossibles, boolean doitManger)
	{
		if (coupJoue == null || coupJoue.arrivee == null || coupJoue.depart == null)
			return false;

		ArrayList<Coordonnee> l = new ArrayList<Coordonnee>();
		Iterator<Case> it = pionsPossibles.iterator();
		while (it.hasNext())
			l.add(it.next().position);

		Direction d = determinerDirection(coupJoue.depart, coupJoue.arrivee);
		Case arrivee = matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne];
		Case depart = matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne];
		boolean res = (coupJoue != null && l.contains(coupJoue.depart) && this.matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne].estVide() && coupJoue.depart != coupJoue.arrivee && d != null && depart.getCaseAt(d) == arrivee);
		if (doitManger)
			res = res && (determinerPionsACapturerRaprochement(d, arrivee).size() > 0 || determinerPionsACapturerEloignement(d, depart).size() > 0);
		return res;
	}

	/**
	 * Evalue le coup joué par le joueur
	 * 
	 * @param coupJoue
	 *            Le coup joué par le joueur
	 * @return True -> le joueur peut rejouer, False -> le joueur ne peut pas
	 *         rejouer
	 */
	private boolean faireCoup(Coup coupJoue)
	{
		leMoteur.getCurrentDisplay().afficherCoupJoue(coupJoue);
		if (!paused && !stopped && coupJoue != null && coupJoue.depart != null && coupJoue.arrivee != null)
		{
			Direction directionJoue = determinerDirection(coupJoue.depart, coupJoue.arrivee);

			ArrayList<Case> rapprochement = determinerPionsACapturerRaprochement(directionJoue, matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne]);
			ArrayList<Case> eloignement = determinerPionsACapturerEloignement(directionJoue, matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne]);

			if (rapprochement.size() == 0 && eloignement.size() == 0)
			{
				leMoteur.getCurrentDisplay().afficherJeu();
				matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne].pion = matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne].pion;
				matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne].pion = null;
				return false;
			} else if (rapprochement.size() != 0 && eloignement.size() != 0)
			{
				leMoteur.getCurrentDisplay().afficherMultiDirections(eloignement, rapprochement);
				Case choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);
				while (!rapprochement.contains(choix) && !eloignement.contains(choix))
					choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);

				leMoteur.envoyerChoixCaseSurReseau(choix.position);

				if (rapprochement.contains(choix))
				{
					leMoteur.getCurrentDisplay().afficherPionsCaptures(rapprochement);
					capturer(rapprochement);
				} else if (eloignement.contains(choix))
				{
					leMoteur.getCurrentDisplay().afficherPionsCaptures(eloignement);
					capturer(eloignement);
				}
			} else if (rapprochement.size() != 0 && eloignement.size() == 0)
			{
				capturer(rapprochement);
				leMoteur.getCurrentDisplay().afficherPionsCaptures(rapprochement);
			} else if (eloignement.size() != 0 && rapprochement.size() == 0)
			{
				capturer(eloignement);
				leMoteur.getCurrentDisplay().afficherPionsCaptures(eloignement);
			}
			matricePlateau[coupJoue.arrivee.ligne][coupJoue.arrivee.colonne].pion = matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne].pion;
			matricePlateau[coupJoue.depart.ligne][coupJoue.depart.colonne].pion = null;
			leMoteur.getCurrentDisplay().afficherJeu();
			return true;
		}
		return false;
	}

	/**
	 * Methode permettant de retirer reelement les pions de la liste passee en
	 * parametres du plateau du jeu
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
	 * @param directionCapture
	 *            La direction dans la quelle capturer
	 * @param depart
	 *            La case de debut de la capture (cette case doit etre vide on
	 *            commence la capture la case d'apres)
	 * @return Une liste de cases qui correspond aux pions supprimes
	 */
	private ArrayList<Case> determinerPionsACapturerRaprochement(Direction directionCapture, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (directionCapture != null && depart != null)
		{
			Case courante = depart;
			Pion pionJoueurCourant = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

			while (courante != null)
			{
				courante = courante.getCaseAt(directionCapture);
				if (courante != null && !courante.estVide() && courante.pion != pionJoueurCourant)
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
	 * @param directionJoue
	 *            La direction dans la quelle capturer
	 * @param depart
	 *            La case de debut de la capture (cette case doit etre vide on
	 *            commence la capture sur la case opposee)
	 * @return Une liste de cases qui correspond aux pions supprimes
	 */
	private ArrayList<Case> determinerPionsACapturerEloignement(Direction directionJoue, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (directionJoue != null && depart != null)
		{
			Case courante = depart;
			Pion pionJoueurCourant = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

			if (courante.getCaseAt(directionJoue) != null && courante.getCaseAt(directionJoue).estVide())
			{
				while (courante != null)
				{
					courante = courante.getCaseAt(Direction.oppose(directionJoue));
					if (courante != null && !courante.estVide() && courante.pion != pionJoueurCourant)
						res.add(courante);
					else
						break;
				}
			}
		}
		return res;
	}

	/**
	 * Fonction determinant si un des joueur a capturer tous les pions de
	 * l'autre
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
	 * Donne les cases possibles (etant vides) pour un pion se trouvant sur la
	 * case c
	 * 
	 * @param caseDuPion
	 *            La case de depart du pion
	 * @return Une liste de cases accessibles pour ce pion
	 */
	public ArrayList<Case> coupsPossiblesPourUnPion(Case caseDuPion)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (caseDuPion != null)
		{
			Iterator<Case> it = caseDuPion.voisins().iterator();
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
		return null;
	}

	/**
	 * Fonction renvoyant les pions du joueur courant qui peuvent se deplacer
	 * (en capturant ou nom)
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
	 * Fonction donnant les pions du joueur courant qui peuvent capturer ce coup
	 * ci
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
		return res;
	}

	/**
	 * Donne les cases que si on y va dessus on capture
	 * 
	 * @param coupsPossibles
	 *            Tous les coups possibles de deplacement pour le pion sur case
	 *            c
	 * @param caseDuPion
	 *            La case sur laquelle ce trouve le pion qui va capturer
	 * @return
	 */
	public ArrayList<Case> coupsPourPriseParUnPion(ArrayList<Case> coupsPossibles, Case caseDuPion)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if (caseDuPion != null)
		{
			Pion ennemi = (joueurCourant == joueurBlanc) ? Pion.Noir : Pion.Blanc;
			for (Direction d : Direction.values())
			{
				/* Aspiration - Eloignement */
				/*
				 * Pour chaque direction, on test si la case est vide, qu'elle
				 * est un coup possibles, et que la case opposée soit un pion
				 * ennemi
				 */
				if (caseDuPion.getCaseAt(d) != null && coupsPossibles.contains(caseDuPion.getCaseAt(d)) && caseDuPion.getCaseAt(Direction.oppose(d)) != null && caseDuPion.getCaseAt(Direction.oppose(d)).pion == ennemi)
					res.add(caseDuPion.getCaseAt(d));

				/* Percussion - Rapprochement */
				/*
				 * Pour chaque direction, on vérifie que la case visée soit
				 * vide, qu'elle soit un coup possible et que la case suivante
				 * dans la même direction soit un pion ennemi
				 */
				if (caseDuPion.getCaseAt(d) != null && coupsPossibles.contains(caseDuPion.getCaseAt(d)) && caseDuPion.getCaseAt(d).getCaseAt(d) != null && caseDuPion.getCaseAt(d).getCaseAt(d).pion == ennemi)
					res.add(caseDuPion.getCaseAt(d));
			}
		}
		return res;

	}


	/**
	 * Arrete le jeu, tue les joueurs
	 * 
	 * @param b
	 */
	@SuppressWarnings("deprecation")
	public void finir(boolean b)
	{
		this.joueurBlanc.setStopped(true);
		this.joueurNoir.setStopped(true);
		if (b)
		{
			this.joueurBlanc.stop();
			this.joueurNoir.stop();
		}
		this.stopped = true;
	}

	/**
	 * Chaine une matrice de cases
	 * @param nbLignes Nombre de lignes de la matrice
	 * @param nbColonne Nombre de colonnes de la matrice
	 * @param matrice La matrice a chainee
	 * @return La matrice passee en parametre chainee
	 */
	private static Case[][] chainage(int nbLignes, int nbColonne, Case[][] matrice)
	{
		for (int i = 0; i < nbLignes; i++)
		{
			for (int j = 0; j < nbColonne - 1; j++)
				matrice[i][j].est = matrice[i][j + 1];
			for (int j = 1; j < nbColonne; j++)
				matrice[i][j].ouest = matrice[i][j - 1];
		}
		for (int j = 0; j < nbColonne; j++)
		{
			for (int i = 0; i < nbLignes - 1; i++)
				matrice[i][j].sud = matrice[i + 1][j];
			for (int i = 1; i < nbLignes; i++)
				matrice[i][j].nord = matrice[i - 1][j];
		}

		for (int i = 0; i < nbLignes; i++)
			for (int j = 0; j < nbColonne; j++)
			{
				if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))
				{
					if (i > 0 && i < nbLignes - 1 && j > 0 && j < nbColonne - 1)
					{
						matrice[i][j].nordEst = matrice[i - 1][j + 1];
						matrice[i][j].sudEst = matrice[i + 1][j + 1];
						matrice[i][j].sudOuest = matrice[i + 1][j - 1];
						matrice[i][j].nordOuest = matrice[i - 1][j - 1];
					}
					if (i == 0)
					{
						if (j >= 0 && j != nbColonne - 1)
							matrice[i][j].sudEst = matrice[i + 1][j + 1];
						if (j != 0 && j < nbColonne)
							matrice[i][j].sudOuest = matrice[i + 1][j - 1];
					}
					if (i == nbLignes - 1)
					{
						if (j >= 0 && j != nbColonne - 1)
							matrice[i][j].nordEst = matrice[i - 1][j + 1];
						if (j != 0 && j < nbColonne)
							matrice[i][j].nordOuest = matrice[i - 1][j - 1];
					}
					if (j == 0)
					{
						if (i != 0 && i <= nbLignes)
							matrice[i][j].nordEst = matrice[i - 1][j + 1];
						if (i >= 0 && i != nbLignes - 1)
							matrice[i][j].sudEst = matrice[i + 1][j + 1];
					}
					if (j == 8)
					{
						if (i >= 0 && i != nbLignes - 1)
							matrice[i][j].sudOuest = matrice[i + 1][j - 1];
						if (i != 0 && i <= nbLignes)
							matrice[i][j].nordOuest = matrice[i - 1][j - 1];
					}
				}
			}
		return matrice;

	}

	/**
	 * Permet au joueur courant de finir son tour (uniquement possible durant un
	 * combo this.enCombo==True)
	 */
	public void finirSonTour()
	{
		if(this.enCombo){
			if (Tools.getTypeOfPlayer(joueurCourant) == PlayerType.Humain)
			{
				this.finirSonTour = true;
					((HumanPlayer) this.joueurCourant).setCoup(null, null);
				
			}else if(Tools.getTypeOfPlayer(joueurCourant) == PlayerType.Reseau) {
				this.finirSonTour = true;
				this.leMoteur.getNetworkManager().coupsRecu.add(new Coup(null,null));
			}
		}
	}
}

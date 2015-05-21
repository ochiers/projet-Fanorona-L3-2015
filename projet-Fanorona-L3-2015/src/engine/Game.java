package engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import IHM.Affichage;

/**
 * Classe representant une partie. Pour lancer le jeu il faut faire appel a la
 * methode jouer()
 * 
 * @author soulierc
 *
 */
public class Game {

	/**
	 * Indique que e jeu est arreter, le passage a true a pour effet de terminer
	 * la partie
	 */
	public boolean			stopped;
	/**
	 * Indique que la partie s'est terminee normalement avec un vainqueur, la
	 * partie est arretée apres cela
	 */
	public boolean			finish;
	/**
	 * Indique si la partie est en pause
	 */
	private boolean			paused;
	/**
	 * Le joueur qui est en train de jouer
	 */
	public Player			joueurCourant;
	/**
	 * Le joueur qui les pions blancs
	 */
	public Player			joueurBlanc;
	/**
	 * Le joueur qui a les pions noirs
	 */
	public Player			joueurNoir;
	/**
	 * Le joueur qui a gagner, est renseigné uniquement quand la partie c'est
	 * terminee normalement (this.finish == true)
	 */
	private Player			winner;
	/**
	 * Premiere case du plateau, permet d'acceder aux autres car elles sont
	 * chainee
	 */
	public Case				plateau;
	/**
	 * Tableau de case representant tous le plateau
	 */
	public Case[][]			matricePlateau;
	/**
	 * Nombre de tour effectués
	 */
	public int				numberTurn;
	/**
	 * Nombre de pions blancs restants sur le plateau
	 */
	public int				nombrePionBlanc;
	/**
	 * Nombre de pions noirs restants sur le plateau
	 */
	public int				nombrePionNoir;
	/**
	 * Hauteur du plateau
	 */
	public int				nbLignes;
	/**
	 * Largeur du plateau
	 */
	public int				nbColonnes;
	/**
	 * Le module d'affichage
	 */
	public Affichage		display;

	/**
	 * Liste des coups du combo courant, sert à respecter la regle qui dit qu'on
	 * ne peut pas revenir sur une case deja jouee
	 */
	public ArrayList<Case>	combo;

	/**
	 * Cree une nouvelle partie avec un module d'affichage, deux joueurs blanc
	 * et noirs et un plateu de largeur*hauteur
	 * 
	 * @param affichage
	 *            L'affichage du jeu
	 * @param joueurQuiCommence
	 *            0 -> Blanc qui commence, 1 -> Noir qui commence
	 * @param p1
	 *            Joueur Blanc
	 * @param p2
	 *            Joueur Noir
	 * @param nbLignes
	 *            Hauteur du plateau (nombre de cases)(5)
	 * @param nbColonnes
	 *            Largeur du plateau (nombre de cases)(9 ou 5)
	 */
	public Game(Affichage affichage, int joueurQuiCommence, Player p1, Player p2, int nbLignes, int nbColonnes)
	{
		this.stopped = false;
		this.finish = false;
		this.paused = true;
		this.joueurBlanc = p1;
		this.joueurNoir = p2;
		if (joueurQuiCommence == 0)
			this.joueurCourant = p1;
		else
			this.joueurCourant = p2;

		this.combo = new ArrayList<Case>();
		this.numberTurn = 0;
		this.display = affichage;
		this.nombrePionBlanc = ((nbLignes * nbColonnes) - 1) / 2;
		this.nombrePionNoir = this.nombrePionBlanc;
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		initialisation(nbLignes, nbColonnes);
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

		this.plateau = tableau[0][0];
		this.matricePlateau = tableau;
	}

	/**
	 * Joue une partie jusqu'a ce qu'un joueur ai gagné ou que la partie a été
	 * arretée
	 * 
	 * @throws InterruptedException
	 */
	public void jouer() throws InterruptedException
	{
		while (!finish && !stopped)
		{
			while (paused)
				Thread.sleep(50);

			if (stopped)
				return;
			ArrayList<Case> pionsPossibles = this.lesPionsQuiPeuventManger();
			if (pionsPossibles.size() == 0)
			{
				System.err.println("Aucun pion ne peut manger");
				pionsPossibles = this.lesPionsJouables();
			}
			// TODO : FAIRE FONCTION D'ELIMINATION DOUBLON DE LA LISTE
			// piosPossibles

			display.afficherPionsPossibles(pionsPossibles);
			Case[] tmp = new Case[pionsPossibles.size()];
			Coup c = this.joueurCourant.play(pionsPossibles.toArray(tmp));
			while (!stopped && !paused && !this.coupValide(c, pionsPossibles))
			{
				System.err.println("Coup impossible depart : " + c.depart + ", arrivee : " + c.arrivee);
				c = this.joueurCourant.play(pionsPossibles.toArray(tmp));
			}

			while (paused)
				Thread.sleep(50);
			System.out.println("Coup valide");
			if (!stopped && !paused)
			{

				// System.out.println("Maj matrice ..... ");
				boolean rejouer = faireCoup(c);
				// System.out.println(".... Fini. Peut rejouer ? : " + rejouer);
				combo = new ArrayList<Case>();
				combo.add(matricePlateau[c.depart.ligne][c.depart.colonne]);
				// System.out.println("Premier coup " +c);
				Case pionJoue = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
				while (!joueurCourant.isStopped() && !stopped && !paused && rejouer)
				{

					System.out.println("\t\t\t" + matricePlateau[2][4].pion);
					System.out.println("\t\t\t" + pionJoue.pion);
					System.out.println("\t\t\t" + matricePlateau[c.depart.ligne][c.depart.colonne]);
					ArrayList<Case> l = this.coupsPourPriseParUnPion(coupsPossiblesPourUnPion(pionJoue), pionJoue);
					afficherList(l, "Case");
					afficherList(combo, "Combo");
					l.removeAll(combo);
					if (l.size() == 1 && l.contains(pionJoue))
						l.remove(pionJoue);
					Case tmp2[] = new Case[l.size()];
					if (l.size() <= 0)
					{
						System.out.println("Plus de possibilites");
						break;
					}
					display.afficherPionDuCombo(matricePlateau[c.arrivee.ligne][c.arrivee.colonne]);
					Coup c2 = this.joueurCourant.play(l.toArray(tmp2));
					while (!joueurCourant.isStopped() && !comboValide(c2))
						c2 = this.joueurCourant.play(l.toArray(tmp2));

					pionJoue = matricePlateau[c2.arrivee.ligne][c2.arrivee.colonne];
					// System.out.println("Nouveau point du combo " + c2);
					while (paused)
						Thread.sleep(50);

					combo.add(matricePlateau[c2.depart.ligne][c2.depart.colonne]);
					rejouer = faireCoup(c2);
				}
			}
			joueurCourant = (joueurCourant == joueurBlanc) ? joueurNoir : joueurBlanc;
			finish = testVictoire();
			this.display.afficherJeu();

		}

		if (finish && !stopped)
		{
			winner = joueurCourant;
		}

	}

	/**
	 * Teste si on peut effectuer un combo
	 * 
	 * @param c
	 *            Le coup joué
	 * @return Vrai -> si on peut faire le combo, Faux sinon
	 */
	private boolean comboValide(Coup c)
	{
		boolean res = true;
		Case arrivee = matricePlateau[c.arrivee.ligne][c.arrivee.colonne];
		Case depart = matricePlateau[c.depart.ligne][c.depart.colonne];
		res = res && !combo.contains(arrivee) && coupsPossiblesPourUnPion(depart).contains(arrivee);
		System.out.println("Combo valide ? :" + res);
		return res;
	}

	/**
	 * Teste si un coup est valide
	 * 
	 * @param c
	 *            Le coup a verifier
	 * @param pionsPossibles
	 * @return True -> si coup est valide, False sinon
	 */
	private boolean coupValide(Coup c, ArrayList<Case> pionsPossibles)
	{
		ArrayList<Coordonnee> l = new ArrayList<Coordonnee>();
		for (int i = 0; i < pionsPossibles.size(); i++)
			l.add(pionsPossibles.get(i).position);

		return (c != null && l.contains(c.depart) && this.matricePlateau[c.arrivee.ligne][c.arrivee.colonne].estVide() && c.depart != c.arrivee);
	}

	/**
	 * Evalue le coup joué par le joueur
	 * 
	 * @param c
	 *            Le coup joué par le joueur
	 * @return True -> le joueur peut rejouer, False -> le joueur ne peut pas
	 *         rejouer
	 */
	private boolean faireCoup(Coup c)
	{
		// System.out.println("Determination du raprochemeent ...");
		Direction d = determinerDirection(c.depart, c.arrivee);

		ArrayList<Case> rapprochement = determinerPionsACapturerRaprochement(d, matricePlateau[c.arrivee.ligne][c.arrivee.colonne]);
		// System.out.println("..." + rapprochement.size() +
		// "...Fini. Determination de l'eloignement ...");
		ArrayList<Case> eloignement = determinerPionsACapturerEloignement(d, matricePlateau[c.depart.ligne][c.depart.colonne]);
		// System.out.println("..." + eloignement.size() + "... Fini");
		if (rapprochement.size() == 0 && eloignement.size() == 0)
		{
			this.display.afficherJeu();
			matricePlateau[c.arrivee.ligne][c.arrivee.colonne].pion = matricePlateau[c.depart.ligne][c.depart.colonne].pion;
			matricePlateau[c.depart.ligne][c.depart.colonne].pion = null;
			return false;
		} else if (rapprochement.size() != 0 && eloignement.size() != 0)
		{
			System.out.println("Deux prises possibles (" + rapprochement.size() + ", " + eloignement.size() + " , faite votre choix ....");
			display.afficherMultiDirections(eloignement, rapprochement);
			Case choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);
			while (!rapprochement.contains(choix) && !eloignement.contains(choix))
				choix = joueurCourant.choisirDirectionAManger(rapprochement, eloignement);

			if (rapprochement.contains(choix))
				capturer(rapprochement);
			else if (eloignement.contains(choix))
				capturer(eloignement);

			System.out.println(".... Choix fait.");
		} else if (rapprochement.size() != 0 && eloignement.size() == 0)
		{
			// System.out.println("Capture avec rapprochement ....");
			capturer(rapprochement);
			// System.out.println("....Fini");
		} else if (eloignement.size() != 0 && rapprochement.size() == 0)
		{
			// System.out.println("Capture avec eloignement ....");
			capturer(eloignement);
			// System.out.println("....Fini");
		}
		matricePlateau[c.arrivee.ligne][c.arrivee.colonne].pion = matricePlateau[c.depart.ligne][c.depart.colonne].pion;
		matricePlateau[c.depart.ligne][c.depart.colonne].pion = null;
		this.display.afficherJeu();
		return true;
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
	 * @param d
	 *            La direction dans la quelle capturer
	 * @param depart
	 *            La case de debut de la capture (cette case doit etre vide on
	 *            commence la capture la case d'apres)
	 * @return Une liste de case a liberer de leur pion
	 */
	private ArrayList<Case> determinerPionsACapturerRaprochement(Direction d, Case depart)
	{
		// System.out.println("\n Direction :" + d.name() + ", " + depart +
		// "\n");

		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;
		Pion p = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

		while (courante != null)
		{
			courante = courante.getCaseAt(d);
			if (courante != null && !courante.estVide() & courante.pion != p)
				res.add(courante);
			else
				break;
		}

		return res;
	}

	private ArrayList<Case> determinerPionsACapturerEloignement(Direction d, Case depart)
	{
		System.out.println("\n Direction :" + d.name() + ", " + depart + "\n");

		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;
		Pion p = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

		if (courante.getCaseAt(d).estVide())
		{
			while (courante != null)
			{

				courante = courante.getCaseAt(Direction.oppose(d));

				if (courante != null && !courante.estVide() & courante.pion != p)
					res.add(courante);
				else
					break;
			}
		}
		return res;
	}

	/**
	 * FOnction determinant si un des joueur a capturer tous les pions de
	 * l'autre
	 * 
	 * @return
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
		this.joueurBlanc.setStopped(true);
		this.joueurNoir.setStopped(true);
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
	 * @param c
	 *            La case de depart du pion
	 * @return Une liste de cases accessibles pour ce pion
	 */
	public ArrayList<Case> coupsPossiblesPourUnPion(Case c)
	{

		ArrayList<Case> res = new ArrayList<Case>();
		Iterator<Case> it = c.voisins().iterator();
		Case cour;
		while (it.hasNext())
		{
			cour = it.next();
			if (cour.estVide())
				res.add(cour);
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
		System.err.println("GROS SOUCIS ! IMPOSSIBLE DE DETERMINEE LA DIRECTION DU COUP : Depart : " + depart + ", arrivee : " + arrivee);
		return null;
	}

	/**
	 * Fonction renvoyant les pions du joueur courant qui peuvent se deplacer
	 * (en mangeant ou nom)
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
	 * Fonction donnant les pions du joueur courant qui peuvent manger ce coup
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
			res.addAll(coupsPourPriseParUnPion(coupsPossibles, tmp));
		}
		for (int i = 0; i < res.size(); i++)
			System.out.print(res.get(i));

		return res;
	}

	/**
	 * Donne les cases mangeables par un le pion sur la case c
	 * 
	 * @param coupsPossibles
	 *            Tous les coups possibles de deplacement pour le pion sur case
	 *            c
	 * @param c
	 *            La case sur laquelle ce trouve le pion qui va manger
	 * @return
	 */
	public ArrayList<Case> coupsPourPriseParUnPion(ArrayList<Case> coupsPossibles, Case c)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Pion ennemi = (joueurCourant == joueurBlanc) ? Pion.Noir : Pion.Blanc;
		for (Direction d : Direction.values())
		{
			/*
			 * POur chaque dir, on test si la case est d'une couleur différente
			 * et que la case dans la direction opposé fait partie des coups
			 * possibles
			 */
			if (c.getCaseAt(d) != null && c.getCaseAt(d).pion == ennemi && coupsPossibles.contains(c.getCaseAt(Direction.oppose(d))))
				res.add(c);
		}

		/* Percussion - Rapprochement */
		for (Direction d : Direction.values())
		{
			/*
			 * POur chaque dir, on test si la case est d'une couleur différente
			 * et que la case dans la direction opposé fait partie des coups
			 * possibles
			 */
			if (c.getCaseAt(d) != null && c.getCaseAt(d).estVide() && c.getCaseAt(d).getCaseAt(d) != null && c.getCaseAt(d).getCaseAt(d).pion == ennemi)
				res.add(c);
		}
		afficherList(res, "MACHIN");
		return res;

	}

	/**
	 * Teste si une case est jouable
	 * 
	 * @param p
	 *            La coordonnee de la case
	 * @return Vrai si la case est vide, Faux sinon
	 */
	public boolean estJouable(Coordonnee p)
	{

		Pion courant = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;
		boolean res = false;
		if (matricePlateau[p.ligne][p.colonne].pion == courant)
		{
			for (Case case1 : matricePlateau[p.ligne][p.colonne].voisins())
			{
				res = res || case1.estVide();
			}
		}
		return res;

	}

	/**
	 * Teste si la case a la coordonee p est voisin de la case a la coordonnee q
	 * 
	 * @param p
	 *            Une case
	 * @param q
	 *            Une case
	 * @return Vrai si p appartien aux voisins de q
	 */
	public boolean estVoisin(Coordonnee p, Coordonnee q)
	{
		Case c1 = matricePlateau[p.ligne][p.colonne];
		Case c2 = matricePlateau[q.ligne][q.colonne];

		return c1.voisins().contains(c2);

	}

	public void afficherList(ArrayList<Case> l, String str)
	{
		System.out.println("------------Affichage" + str + "-------------");
		Iterator<Case> it = l.iterator();
		while (it.hasNext())
			System.out.println(it.next());
		System.out.println("------------ Fin Affichage-------------");
	}
}

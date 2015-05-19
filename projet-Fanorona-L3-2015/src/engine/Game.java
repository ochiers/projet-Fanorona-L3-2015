package engine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import IHM.Affichage;

public class Game {

	public boolean		stopped;
	public boolean		finish;
	private boolean		paused;
	public Player		joueurCourant;
	public Player		joueurBlanc;
	public Player		joueurNoir;
	private Player		winner;
	public Case			plateau;
	public Case[][]		matricePlateau;
	public int			numberTurn;
	public int			nombrePionBlanc;
	public int			nombrePionNoir;
	public int			hauteur;
	public int			largeur;
	public Affichage	display;

	/**
	 * Crée une nouvelle partie avec un module d'affichage et deux joueurs,
	 * c'est p1 qui joue en premier
	 * 
	 * @param affichage
	 *            Le module qui sert a afficher le jeu
	 * @param p1
	 *            Joueur qui commencer en premier
	 * @param p2
	 *            Joueur qui joue en deuxieme
	 */
	public Game(Affichage affichage, int joueurQuiCommence, Player p1,
			Player p2, int hauteur, int largeur)
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

		this.numberTurn = 0;
		this.display = affichage;
		this.nombrePionBlanc = ((hauteur * largeur) - 1) / 2;
		this.nombrePionNoir = this.nombrePionBlanc;
		this.hauteur = hauteur;
		this.largeur = largeur;
		initialisation(hauteur, largeur);
	}

	/**
	 * Initialise le plateau du jeu
	 * 
	 * @param hauteur
	 * @param largeur
	 */
	public void initialisation(int hauteur, int largeur)
	{
		Case[][] tableau = new Case[hauteur][largeur];

		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				tableau[i][j] = new Case(new Point(i, j));

		for (int i = 0; i < hauteur; i++)
		{
			for (int j = 0; j < largeur - 1; j++)
				tableau[i][j].est = tableau[i][j + 1];
			for (int j = 1; j < largeur; j++)
				tableau[i][j].ouest = tableau[i][j - 1];
		}
		for (int j = 0; j < largeur; j++)
		{
			for (int i = 0; i < hauteur - 1; i++)
				tableau[i][j].sud = tableau[i + 1][j];
			for (int i = 1; i <= hauteur; i++)
				tableau[i][j].nord = tableau[i - 1][j];
		}

		for (int i = 0; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
			{
				if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))
				{
					if (i > 0 && i < hauteur - 1 && j > 0 && j < largeur - 1)
					{
						tableau[i][j].nordEst = tableau[i - 1][j + 1];
						tableau[i][j].sudEst = tableau[i + 1][j + 1];
						tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (i == 0)
					{
						if (j >= 0 && j != largeur - 1)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
						if (j != 0 && j < largeur)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
					}
					if (i == hauteur - 1)
					{
						if (j >= 0 && j != largeur - 1)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (j != 0 && j < largeur)
							tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (j == 0)
					{
						if (i != 0 && i <= hauteur)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (i >= 0 && i != hauteur)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
					}
					if (j == 8)
					{
						if (i >= 0 && i != hauteur - 1)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						if (i != 0 && i <= hauteur)
							tableau[i][j].nordEst = tableau[i + 1][j + 1];
					}
				}
			}

		for (int i = 0; i < Math.floor((double) hauteur / 2.0); i++)
			for (int j = 0; j < largeur; j++)
				tableau[i][j].pion = Pion.Noir;

		for (int i = (int) Math.floor((double) hauteur / 2.0) + 1; i < hauteur; i++)
			for (int j = 0; j < largeur; j++)
				tableau[i][j].pion = Pion.Blanc;

		for (int j = 0; j < largeur; j++)
			if (j % 2 == 0)
				tableau[(int) Math.floor((double) hauteur / 2.0)][j].pion = Pion.Blanc;
			else
				tableau[(int) Math.floor((double) hauteur / 2.0)][j].pion = Pion.Noir;

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
			Coup c = null;
			while (!stopped && !paused && !this.coupValide(c))
				c = this.joueurCourant.play();

			if (stopped || paused)
				continue;
			else
			{
				while (!joueurCourant.isStopped() && !stopped && !paused
						&& faireCoup(c))
					c = this.joueurCourant.play();

			}

			finish = testVictoire();

		}

		if (finish && !stopped)
			winner = joueurCourant;

	}

	/**
	 * Teste si un coup est valide
	 * 
	 * @param c
	 *            Le coup a verifier
	 * @return True -> si coup est valide, False sinon
	 */
	private boolean coupValide(Coup c)
	{

		return (c != null && c.arrivee.x >= 0 && c.arrivee.x < largeur
				&& c.arrivee.y >= 0 && c.arrivee.y < hauteur && c.depart.x >= 0
				&& c.depart.x < largeur && c.depart.y >= 0
				&& c.depart.y < hauteur
				&& this.matricePlateau[c.arrivee.y][c.arrivee.x].estVide() && c.depart != c.arrivee);
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
		ArrayList<Case> rapprochement = determinerPionsACapturer(
				determinerDirection(c.depart, c.arrivee),
				matricePlateau[c.arrivee.y][c.arrivee.x]);
		ArrayList<Case> eloignement = determinerPionsACapturer(
				determinerDirection(c.depart, c.arrivee),
				matricePlateau[c.depart.y][c.depart.x]);
		if (rapprochement.size() == 0 && eloignement.size() == 0)
		{
			return false;
		} else if (rapprochement.size() != 0 && rapprochement.size() != 0)
		{
			capturer(determinerPionsACapturer(
					joueurCourant.choisirDirectionAManger(),
					matricePlateau[c.arrivee.y][c.arrivee.x])); // risque de
																// gros soucis
																// ici
		} else if (rapprochement.size() != 0 && eloignement.size() == 0)
		{
			capturer(rapprochement);
		} else if (eloignement.size() != 0 && rapprochement.size() == 0)
		{
			capturer(eloignement);
		}
		matricePlateau[c.arrivee.y][c.arrivee.x].pion = matricePlateau[c.depart.y][c.depart.x].pion;
		matricePlateau[c.depart.y][c.depart.x].pion = null;
		return true;
	}

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

	private ArrayList<Case> determinerPionsACapturer(Direction d, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;
		Pion p = (joueurCourant == joueurBlanc) ? Pion.Blanc : Pion.Noir;

		while (courante != null)
		{

			switch (d)
			{
				case Nord:
					courante = courante.nord;
					break;
				case NordEst:
					courante = courante.nordEst;
					break;
				case Est:
					courante = courante.est;
					break;
				case SudEst:
					courante = courante.sudEst;
					break;
				case Sud:
					courante = courante.sud;
					break;
				case SudOuest:
					courante = courante.sudOuest;
					break;
				case Ouest:
					courante = courante.ouest;
					break;
				case NordOuest:
					courante = courante.nordOuest;
					break;
				default:
					break;
			}
			if (courante != null && !courante.estVide() & courante.pion != p)
				res.add(courante);
			else
				break;
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
	 * Donne le gagnant du jeu;
	 * 
	 * @return Un joueur qui est le gagnant
	 */
	public Player getWinner()
	{
		return winner;
	}

	/**
	 * Donne les cases possibles pour un pion se trouvant sur la case c
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

	public static Direction determinerDirection(Point depart, Point arrivee)
	{
		int deplacementX = arrivee.x - depart.x;
		int deplacementY = arrivee.y - depart.y;
		switch (deplacementX)
		{
			case -1:
				switch (deplacementY)
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
				switch (deplacementY)
				{
					case -1:
						return Direction.Nord;
					case 1:
						return Direction.Sud;
				}
				break;
			case 1:
				switch (deplacementY)
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

}

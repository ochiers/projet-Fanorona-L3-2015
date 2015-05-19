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
	public boolean		stopped;
	/**
	 * Indique que la partie s'est terminee normalement avec un vainqueur, la
	 * partie est arretée apres cela
	 */
	public boolean		finish;
	/**
	 * Indique si la partie est en pause
	 */
	private boolean		paused;
	/**
	 * Le joueur qui est en train de jouer
	 */
	public Player		joueurCourant;
	/**
	 * Le joueur qui les pions blancs
	 */
	public Player		joueurBlanc;
	/**
	 * Le joueur qui a les pions noirs
	 */
	public Player		joueurNoir;
	/**
	 * Le joueur qui a gagner, est renseigné uniquement quand la partie c'est
	 * terminee normalement (this.finish == true)
	 */
	private Player		winner;
	/**
	 * Premiere case du plateau, permet d'acceder aux autres car elles sont
	 * chainee
	 */
	public Case			plateau;
	/**
	 * Tableau de case representant tous le plateau
	 */
	public Case[][]		matricePlateau;
	/**
	 * Nombre de tour effectués
	 */
	public int			numberTurn;
	/**
	 * Nombre de pions blancs restants sur le plateau
	 */
	public int			nombrePionBlanc;
	/**
	 * Nombre de pions noirs restants sur le plateau
	 */
	public int			nombrePionNoir;
	/**
	 * Hauteur du plateau
	 */
	public int			hauteur;
	/**
	 * Largeur du plateau
	 */
	public int			largeur;
	/**
	 * Le module d'affichage
	 */
	public Affichage	display;

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
	 * @param hauteur
	 *            Hauteur du plateau (nombre de cases)
	 * @param largeur
	 *            Largeur du plateau (nombre de cases)
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
			for (int i = 1; i < hauteur; i++)
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
						if (i >= 0 && i != hauteur - 1)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
					}
					if (j == 8)
					{
						if (i >= 0 && i != hauteur - 1)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						if (i != 0 && i <= hauteur)
							tableau[i][j].nordOuest = tableau[i - 1][j - 1];
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
		
		tableau[hauteur/2][largeur/2].pion = null;
		
		
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
			this.display.afficherJeu();

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

	/**
	 * Fonction determinant quelle direction le coup est joué
	 * 
	 * @param depart
	 *            Position de départ
	 * @param arrivee
	 *            Position d'arrive
	 * @return La direction du coup
	 */
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

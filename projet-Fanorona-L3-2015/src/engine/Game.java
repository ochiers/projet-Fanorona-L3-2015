package engine;

import java.awt.Point;

import IHM.Affichage;

public class Game {

	public boolean		stopped;
	public boolean		finish;
	private boolean		paused;
	public Player		joueurCourant;
	public Player		J1;
	public Player		J2;
	private Player		winner;
	private Case		plateau;
	public int			numberTurn;
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
	public Game(Affichage affichage, Player p1, Player p2)
	{
		this.stopped = false;
		this.finish = false;
		this.paused = true;
		this.J1 = p1;
		this.J2 = p2;
		this.joueurCourant = p1;
		this.numberTurn = 0;
		this.display = affichage;
	}

	public void initialisation()
	{
		Case[][] tableau = new Case[5][9];

		for (int i = 0; i <= 4; i++)
			for (int j = 0; j <= 8; j++)
				tableau[i][j] = new Case(new Point(i, j));

		for (int i = 0; i <= 4; i++)
		{
			for (int j = 0; j < 8; j++)
				tableau[i][j].est = tableau[i][j + 1];
			for (int j = 1; j <= 8; j++)
				tableau[i][j].ouest = tableau[i][j - 1];
		}
		for (int j = 0; j <= 8; j++)
		{
			for (int i = 0; i < 4; i++)
				tableau[i][j].sud = tableau[i + 1][j];
			for (int i = 1; i <= 4; i++)
				tableau[i][j].nord = tableau[i - 1][j];
		}

		for (int i = 0; i <= 4; i++)
			for (int j = 0; j <= 8; j++)
			{
				if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1))
				{
					if (i > 0 && i < 4 && j > 0 && j < 8)
					{
						tableau[i][j].nordEst = tableau[i - 1][j + 1];
						tableau[i][j].sudEst = tableau[i + 1][j + 1];
						tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (i == 0)
					{
						if (j >= 0 && j != 8)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
						if (j != 0 && j <= 8)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
					}
					if (i == 4)
					{
						if (j >= 0 && j != 8)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (j != 0 && j <= 8)
							tableau[i][j].nordOuest = tableau[i - 1][j - 1];
					}
					if (j == 0)
					{
						if (i != 0 && i <= 4)
							tableau[i][j].nordEst = tableau[i - 1][j + 1];
						if (i >= 0 && i != 4)
							tableau[i][j].sudEst = tableau[i + 1][j + 1];
					}
					if (j == 8)
					{
						if (i >= 0 && i != 4)
							tableau[i][j].sudOuest = tableau[i + 1][j - 1];
						if (i != 0 && i <= 4)
							tableau[i][j].nordEst = tableau[i + 1][j + 1];
					}
				}
			}

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

			Coup c = this.joueurCourant.play();
			if (stopped || paused)
				continue;
			else
			{
				while (faireCoup(c))
					c = this.joueurCourant.play();

			}

			finish = testVictoire();

		}

		if (finish && !stopped)
			winner = joueurCourant;

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

		return finish;
	}

	private boolean testVictoire()
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Met en pause le jeu La methode reprendre() doit etre appelée ensuite
	 */
	public void pause()
	{
		this.J1.setStopped(true);
		this.J2.setStopped(true);
		this.paused = true;
	}

	/**
	 * Reprend le jeu qui etait en pause
	 */
	public void reprendre()
	{
		this.J1.setStopped(false);
		this.J2.setStopped(false);
		this.paused = true;
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

	public Player getWinner()
	{
		return winner;
	}

}

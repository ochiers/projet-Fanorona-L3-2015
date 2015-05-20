package engine;

import java.io.File;
import java.util.Scanner;

import AI.*;
import IHM.Affichage;

public class Engine {

	public boolean			gameInProgress;
	public Game				partieCourante;
	public Affichage		affichage;
	private UndoRedo<Game>	undoRedo;

	public Engine()
	{
		this.gameInProgress = false;
		this.undoRedo = new UndoRedo<Game>();
	}

	public void setAffichage(Affichage f)
	{
		this.affichage = f;
	}

	public void begin()
	{
		try
		{
			while (true) // On suppose que c'est l'IHM qui tue le thread
			{
				while (!gameInProgress)
					Thread.sleep(50);

				partieCourante.reprendre();
				partieCourante.jouer();
				if (!partieCourante.stopped && partieCourante.finish)
				{
					System.out.println("victoire");
					affichage.afficherVictoire(partieCourante.getWinner());
					gameInProgress = false;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void nouvellePartie(Player p1, Player p2,int premierJoueur, int hauteur, int largeur)
	{

		if (this.gameInProgress)
			stopper();
		this.partieCourante = new Game(this.affichage,premierJoueur, p1, p2, hauteur, largeur);
		this.gameInProgress = true;
	}

	public void stopper()
	{
		if (partieCourante != null)
		{
			this.partieCourante.pause();
			this.partieCourante.stopped = true;
			if (this.partieCourante.joueurCourant instanceof HumanPlayer)
			{
				 ((HumanPlayer) this.partieCourante.joueurCourant).setStopped(true);
			}
		}
	}

	/**
	 * Revient un demi-coup plus tot
	 */
	public void annuler()
	{

		if (gameInProgress)
		{

		} else
		{
			partieCourante = undoRedo.undo();
			partieCourante.pause();
			partieCourante.finish = false;
			partieCourante.stopped = false;
			partieCourante.joueurCourant = (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? partieCourante.joueurNoir
					: partieCourante.joueurBlanc;
			partieCourante.reprendre();
			try
			{
				partieCourante.jouer();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			gameInProgress = true;
		}
	}

	/**
	 * Refait le demi-coup annulé
	 */
	public void refaire()
	{

		if (gameInProgress)
		{
			partieCourante.pause(); // On arrete la partie courante qui se
									// deroule dans le thread principal
			partieCourante.stopped = true; //

			/*
			 * On récupere le coup d'avant (partie précédente) et parametre
			 * correctement
			 */
			partieCourante = undoRedo.redo();
			partieCourante.pause();
			partieCourante.finish = false;
			partieCourante.stopped = false;
			partieCourante.joueurCourant = (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? partieCourante.joueurNoir
					: partieCourante.joueurBlanc;
			partieCourante.reprendre();
			gameInProgress = true;
		} else
		{
			partieCourante = undoRedo.redo();
			partieCourante.pause();
			partieCourante.finish = false;
			partieCourante.stopped = false;
			partieCourante.joueurCourant = (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? partieCourante.joueurNoir
					: partieCourante.joueurBlanc;
			partieCourante.reprendre();
			try
			{
				partieCourante.jouer();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			gameInProgress = true;
		}
	}

	public boolean peutAnnuler()
	{
		return undoRedo.canUndo();
	}

	public boolean peutRefaire()
	{
		return undoRedo.canRedo();
	}

	/**
	 * Sauvegarde la partie courante dans son état courant dans le fichier situé
	 * dans path
	 * 
	 * @param path
	 *            Chemin du fichier de sauvegarde
	 */
	public void sauvegarderPartie(String path)
	{
		/*
		 * File fichier = new File(path); try { FileWriter w = new
		 * FileWriter(fichier); String str = partieCourante.J1.toString() + "#"
		 * + partieCourante.J1.getClass().getSimpleName() + "\n"; str +=
		 * partieCourante.J2.toString() + "#" +
		 * partieCourante.J2.getClass().getSimpleName() + "\n";
		 * 
		 * if (partieCourante.joueurCourant == partieCourante.J1) str += 1 +
		 * "\n"; else str += 2 + "\n";
		 * 
		 * str += partieCourante.numberTurn + "\n"; str +=
		 * partieCourante.map.largeur + "\n" + partieCourante.map.hauteur; for
		 * (int i = 0; i < partieCourante.map.hauteur; i++) { str += "\n"; for
		 * (int j = 0; j < partieCourante.map.largeur; j++) str +=
		 * partieCourante.map.grille[j][i] + " "; } w.write(str); w.close(); }
		 * catch (IOException e) { e.printStackTrace(); }
		 */
	}

	/**
	 * Charge une nouvelle partie stochée dans path, la nouvelle partie sera en
	 * pause
	 * 
	 * @param path
	 *            Le chemin vers le fichier qui contient la partie à charger
	 */
	public void chargerPartie(String path)
	{

		File fichier = new File(path);
		try
		{
			Scanner s = new Scanner(fichier);
			String j1 = s.nextLine();
			String j2 = s.nextLine();
			int jcour = Integer.parseInt(s.nextLine());
			int nbturn = Integer.parseInt(s.nextLine());
			int largeur = Integer.parseInt(s.nextLine());
			int hauteur = Integer.parseInt(s.nextLine());
			int map[][] = new int[largeur][hauteur];
			for (int i = 0; i < hauteur; i++)
			{
				String[] str = s.nextLine().split(" ");
				System.out.println(str[0]);
				System.out.println(str[1]);
				for (int j = 0; j < largeur; j++)
				{
					map[j][i] = Integer.parseInt(str[j]);
				}
			}
			Player p1 = parsePlayer(j1);
			Player p2 = parsePlayer(j2);
			if (this.gameInProgress)
				stopper();
			
			Game g = new Game(affichage,0, p1, p2, hauteur, largeur);
			g.pause();
			this.partieCourante = g;
			this.gameInProgress = true;
			affichage.afficherJeu();
			this.partieCourante.reprendre();

		} catch (Exception e)
		{
			System.err.println("Fichier corrompu");
			e.printStackTrace();
		}

	}

	private Player parsePlayer(String str)
	{
		Player res = null;
		String[] a = str.split("#");
		if (a[2].equals("EasyAI"))
			res = new EasyAI(this, true, a[1]);
		else if (a[2].equals("MediumAI"))
			res = new MediumAI(this, true, a[1]);
		else if (a[2].equals("HardAI"))
			res = new HardAI(this, true, a[1]);
		else if (a[2].equals("HumanPlayer"))
			res = new HumanPlayer(this, false, a[1]);
		else if (a[2].equals("HumanPlayerConsole"))
			res = new HumanPlayerConsole(this, false, a[1]);

		return res;

	}
}

package engine;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import AI.*;
import IHM.Affichage;

public class Engine {

	public boolean			gameInProgress;
	public Game				partieCourante;
	public Affichage		affichage;
	private UndoRedo<Game>	undoRedo;
	public boolean premierJeu;
	
	public Engine()
	{
		this.gameInProgress = false;
		this.undoRedo = new UndoRedo<Game>();
	}

	public void setDisplay(Affichage f)
	{
		this.affichage = f;
		this.premierJeu = true;
	}

	public void begin()
	{
		try
		{
			while (true) // On suppose que c'est l'IHM qui tue le thread
							// principal
			{
				while (!gameInProgress)
				{
					System.out.print("Attente d'une partie");
					Thread.sleep(50);
				}
				partieCourante.pause();
				if(premierJeu)
					partieCourante.reprendre();
				premierJeu = false;
				partieCourante.commencer();
				if (partieCourante.finish)
					gameInProgress = false;

			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void changerPartieCourante(Game g, Pion jCourant)
	{
		if (partieCourante != null)
		{
			gameInProgress = false;
			partieCourante.finir(); // On arrete la partie courante qui se
									// deroule
									// dans le thread principal
			
			Player pB = partieCourante.joueurBlanc;
			Player pN = partieCourante.joueurNoir;
			partieCourante = g;
			
			/*
			 * On récupere le coup d'avant (partie précédente) et parametre correctement
			 */
			
			switch (pB.getNiveau())
			{
				case "Humain":
					partieCourante.joueurBlanc = new HumanPlayer(pB);
					break;
				case "IA Facile":
					partieCourante.joueurBlanc = new EasyAI(this, true, pB.name);
					break;
				case "IA Moyenne":
					partieCourante.joueurBlanc = new MediumAI(this, true, pB.name);
					break;
				case "IA Difficile":
					partieCourante.joueurBlanc = new HardAI(this, true, pB.name);
					break;
			}
			switch (pN.getNiveau())
			{
				case "Humain":
					partieCourante.joueurNoir = new HumanPlayer(pN);
					break;
				case "IA Facile":
					partieCourante.joueurNoir = new EasyAI(this, true, pN.name);
					break;
				case "IA Moyenne":
					partieCourante.joueurNoir = new MediumAI(this, true, pN.name);
					break;
				case "IA Difficile":
					partieCourante.joueurNoir = new HardAI(this, true, pN.name);
					break;
			}
			partieCourante.joueurCourant = (jCourant == Pion.Blanc) ? partieCourante.joueurBlanc : partieCourante.joueurNoir;
			partieCourante.finish = false;
			partieCourante.stopped = false;
			partieCourante.pause();
			try
			{
				Thread.sleep(500);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			gameInProgress = true;
			affichage.afficherJeu();
		}
		else
		{
			partieCourante = g;
		}
	}

	public void nouvellePartie(Player p1, Player p2, int premierJoueur, Dimension size)
	{

		Game g = new Game(this.affichage, this.undoRedo, premierJoueur, p1, p2, size);
		changerPartieCourante(g, (premierJoueur == 0)?Pion.Blanc:Pion.Noir);
		
		this.undoRedo.vider();
		this.undoRedo.addItem(new Game(partieCourante));
		this.gameInProgress = true;
	}

	public void stopper()
	{
		if (partieCourante != null)
		{
			this.partieCourante.finir();
		}
	}

	/**
	 * Revient un demi-coup plus tot
	 */
	public void annuler()
	{
		if (undoRedo.canUndo())
		{
			System.err.println("Annuler");
			changerPartieCourante(this.undoRedo.undo(), (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Noir : Pion.Blanc);
		}
	}

	/**
	 * Refait le demi-coup annul�
	 */
	public void refaire()
	{
		if (undoRedo.canRedo())
		{
			System.err.println("Refaire");
			changerPartieCourante(this.undoRedo.redo(), (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Noir : Pion.Blanc);
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
	 * Sauvegarde la partie courante dans son état courant dans le fichier situé dans path
	 * 
	 * @param path
	 *            Chemin du fichier de sauvegarde
	 */
	public void sauvegarderPartie(String path)
	{
		File f = new File(path);
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			
			out.writeObject(partieCourante);
			System.out.println("Partie sauvegardee");
			this.affichage.sauvegardeReussie(true);
			out.close();
			
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			this.affichage.sauvegardeReussie(false);
			e.printStackTrace();
		}
		
	}

	/**
	 * Charge une nouvelle partie stochée dans path, la nouvelle partie sera en pause
	 * 
	 * @param path
	 *            Le chemin vers le fichier qui contient la partie à charger
	 */
	public void chargerPartie(String path)
	{
		File fichier =  new File(path) ;

		 // ouverture d'un flux sur un fichier
		ObjectInputStream ois = null;
		try
		{
			ois = new ObjectInputStream(new FileInputStream(fichier));
			Game g = (Game)ois.readObject() ;
			g.display = this.affichage;
			g.combo = new ArrayList<Case>();
			Pion Jcourant = (g.joueurBlanc == g.joueurCourant)?Pion.Blanc:Pion.Noir;
			changerPartieCourante(g, Jcourant);
		} catch (Exception e)
		{
			this.affichage.chargementReussi(false);
			e.printStackTrace();
		}
	}
}

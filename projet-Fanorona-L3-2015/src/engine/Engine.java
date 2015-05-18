package engine;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import AI.*;
import IHM.Affichage;

public class Engine {

	public boolean gameInProgress;
	public Game partieCourante;
	public Affichage affichage;

	public Engine() {
		this.gameInProgress = false;
	}

	public void setAffichage(Affichage f) {
		this.affichage = f;
	}

	public void begin() {

		while (true) // On suppose que c'est l'IHM qui tue le thread
		{
			while (!gameInProgress)
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			partieCourante.jouer();
			if (!partieCourante.stopped && partieCourante.finish) {
				System.out.println("victoire");
				affichage.afficherVictoire();
				gameInProgress = false;
			}
		}
	}

	public void nouvellePartie(Player p1, Player p2, int largeur, int hauteur) {

		if (this.gameInProgress)
			stopper();
		this.partieCourante = new Game(this.affichage, largeur, hauteur, p1, p2);
		this.gameInProgress = true;
	}

	public void stopper() {
		if (partieCourante != null) {
			this.partieCourante.stopped = true;
			if (this.partieCourante.joueurCourant instanceof HumanPlayer) {
				((HumanPlayer) this.partieCourante.joueurCourant).setCaseJouee(new Point(0, 0));
			}
		}
	}

	public void sauvegarderPartie(String path) {

		File fichier = new File(path);
		try {
			FileWriter w = new FileWriter(fichier);
			String str = partieCourante.J1.toString() + "#" + partieCourante.J1.getClass().getSimpleName() + "\n";
			str += partieCourante.J2.toString() + "#" + partieCourante.J2.getClass().getSimpleName() + "\n";

			if (partieCourante.joueurCourant == partieCourante.J1)
				str += 1 + "\n";
			else
				str += 2 + "\n";

			str += partieCourante.numberTurn + "\n";
			str += partieCourante.map.largeur + "\n" + partieCourante.map.hauteur;
			for (int i = 0; i < partieCourante.map.hauteur; i++) {
				str += "\n";
				for (int j = 0; j < partieCourante.map.largeur; j++)
					str += partieCourante.map.grille[j][i] + " ";
			}
			w.write(str);
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void chargerPartie(String path) {

		File fichier = new File(path);
		try {
			Scanner s = new Scanner(fichier);
			String j1 = s.nextLine();
			String j2 = s.nextLine();
			int jcour = Integer.parseInt(s.nextLine());
			int nbturn = Integer.parseInt(s.nextLine());
			int largeur = Integer.parseInt(s.nextLine());
			int hauteur = Integer.parseInt(s.nextLine());
			int map[][] = new int[largeur][hauteur];
			for (int i = 0; i < hauteur; i++) {
				String[] str = s.nextLine().split(" ");
				System.out.println(str[0]);
				System.out.println(str[1]);
				for (int j = 0; j < largeur; j++) {
					map[j][i] = Integer.parseInt(str[j]);
				}
			}
			Player p1 = parsePlayer(j1);
			Player p2 = parsePlayer(j2);
			if (this.gameInProgress)
				stopper();
			Game g = new Game(affichage, largeur, hauteur, p1, p2);
			g.joueurCourant = (jcour == 1) ? p1 : p2;
			g.map.grille = map;
			g.numberTurn = nbturn;
			this.partieCourante = g;
			affichage.afficherJeu();

		} catch (Exception e) {
			System.err.println("Fichier corrompu");
			e.printStackTrace();
		}

	}

	private Player parsePlayer(String str) {
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

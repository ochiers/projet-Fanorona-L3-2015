package Main;

import java.awt.Dimension;

import engine.*;
import AI.*;

public class JeuFanoronaIAstats {
	public static void main(String argv[]) throws InterruptedException {

		Machin[] listeParties = new Machin[100];
		for (int i = 0; i < 2; i++) {
			Machin m = new Machin();
			listeParties[i] = m;
			m.start();
		}
		for (int i = 0; i < 2; i++) {
			listeParties[i].join();
		}
		Thread.sleep(5000);
		System.out.flush();
		System.out.println("Nb victoires J1 : " + Machin.nbWInsJoueur1);
		System.out.println("Nb victoires J2 : " + Machin.nbWInsJoueur2);
	}
}

class Machin extends Thread {
	public static int nbWInsJoueur1;
	public static int nbWInsJoueur2;

	public void run() {
		EngineServices e = new Engine();
		Player p1 = new EasyAI(e, true, "Chuck Norris");
		Player p2 = new EasyAI(e, true, "Guillaume de Sauza");
		AffichageVide f = new AffichageVide();
		e.setDisplay(f);
		System.err.close();
		e.nouvellePartie(p1, p2, 0, new Dimension(9, 5));
		e.begin();
		Player vainqueur = e.getWinner();
		if (vainqueur == p1)
			nbWInsJoueur1++;
		else
			nbWInsJoueur2++;
	}
}

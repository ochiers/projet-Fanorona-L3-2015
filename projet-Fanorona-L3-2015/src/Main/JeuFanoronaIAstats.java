package Main;

import java.awt.Dimension;

import javax.print.attribute.standard.Finishings;

import engine.*;
import AI.*;

public class JeuFanoronaIAstats {
	
	public static final int nbTest = 100;
	public static final int[] finished = new int[nbTest];
	
	public static void main(String argv[]) throws InterruptedException {

		Test[] listeParties = new Test[nbTest];
		init();
		System.err.close();
		for (int i = 0; i < nbTest; i++) {
			Test m = new Test(i);
			listeParties[i] = m;
			m.start();
			m.join();
			System.out.println(i + " a fini");
			System.out.println("Nb victoires J1 : " + nbVictoiresJ1());
			System.out.println("Nb victoires J2 : " + nbVictoiresJ2());
		}

		System.out.flush();
		Thread.sleep(2000);
		System.out.println("Nb victoires J1 : " + nbVictoiresJ1());
		System.out.println("Nb victoires J2 : " + nbVictoiresJ2());
	}
	
	public static void init()
	{
		for(int i = 0 ; i<finished.length; i++)
			finished[i] = -1;
	}
	
	public static int nbVictoiresJ1(){
		int res = 0;
		for(int i = 0; i<nbTest;i++)
			if(finished[i] == 1)
				res++;
		return res;
	}
	
	public static int nbVictoiresJ2(){
		int res = 0;
		for(int i = 0; i<nbTest;i++)
			if(finished[i] == 2)
				res++;
		return res;
	}
}

class Test extends Thread {
	public static int nbWInsJoueur1;
	public static int nbWInsJoueur2;

	final int pos;
	public Test(int i)
	{
		pos = i;
	}

	public void run() {
		EngineServices e = new Engine();
		Player p1 = new HardAI(e, true, "Chuck Norris");
		Player p2 = new HardAI(e, true, "Guillaume de Sauza");
		AffichageVide f = new AffichageVide();
		e.setDisplay(f);
		//System.err.close();
		e.nouvellePartie(p1, p2, 1, new Dimension(9, 5));
		e.playOnlyOnce();
		Player vainqueur = e.getWinner();
		if (vainqueur == p1){
			JeuFanoronaIAstats.finished[pos] = 1;
		}
		else if (vainqueur == p2){
			JeuFanoronaIAstats.finished[pos] = 2;
			}
		else
			System.out.println("probleme");
			
	}
}

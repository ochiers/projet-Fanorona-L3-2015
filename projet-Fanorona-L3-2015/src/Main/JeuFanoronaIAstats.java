package Main;

import java.awt.Dimension;

import engine.*;
import AI.*;

public class JeuFanoronaIAstats {
	public static void main(String argv[]) throws InterruptedException{

		EngineServices e=new Engine();
		Player p1 = new EasyAI(e,true,"Chuck Norris");
		Player p2 = new EasyAI(e,true,"Guillaume de Sauza");
		AffichageVide f = new AffichageVide();
		e.setDisplay(f);
		System.err.close();
		int nbWInsJoueur1 = 0;
		int nbWInsJoueur2 = 0;
		for(int i = 0; i < 100; i++){
			System.out.println("test");
			if(i%2 == 0)
				e.nouvellePartie(p1, p2,0, new Dimension(9,5));
			else e.nouvellePartie(p2, p1,0, new Dimension(9,5));
			if(i == 0){
				e.begin();
			}
			Player vainqueur = e.getWinner();
			System.out.println(vainqueur);
			if(vainqueur == p1)
				nbWInsJoueur1++;
			else nbWInsJoueur2++;
		}
		System.out.println("Nb victoires J1 : " + nbWInsJoueur1);
		System.out.println("Nb victoires J2 : " + nbWInsJoueur2);
	}
}

package Main;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{
		Engine e=new Engine();
		
		Player p1=new HumanPlayer(e,false,"player1");
		//Player p1=new EasyAI(e,true,"player1");
		//Player p2=new EasyAI(e,true,"player2");
		Player p2=new HumanPlayer(e,false,"player1");

		Fenetre f=new Fenetre(e);
		e.setAffichage(f);
		//System.err.close();
		e.nouvellePartie(p1, p2,0, 5,9);
		// ajouté pour test ia
//		ArrayList<Case> pionsPossibles = e.partieCourante.lesPionsQuiPeuventManger();
//		Case[] tmp = new Case[pionsPossibles.size()];
//		Coup c = e.partieCourante.joueurCourant.play(pionsPossibles.toArray(tmp));
//		System.out.println("--------------");
//		System.out.println("Depart (" + c.depart.ligne + "," + c.depart.colonne + ") Arrivée (" + c.arrivee.ligne + ","  + c.arrivee.colonne + ")");
//		System.out.println("--------------");
		//fin ajout
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

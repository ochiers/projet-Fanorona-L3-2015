package Main;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{
		Engine e=new Engine();
		
		Player p1=new HumanPlayer(e,false,"Jeremy");
		//Player p1=new EasyAI(e,true,"player1");
		Player p2=new HardAI(e,true,"IA stupide");

		Fenetre f=new Fenetre(e);
		e.setAffichage(f);
		//System.err.close();
		e.nouvellePartie(p1, p2,0, 5,9);
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

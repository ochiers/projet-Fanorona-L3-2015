package Main;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{
		Engine e=new Engine();
		
		//Player p1=new HumanPlayer(e,false,"player1");
		//Player p2=new HumanPlayer(e,false,"player2");

		Fenetre f=new Fenetre(e);
		//e.setAffichage(f);
		
		//e.nouvellePartie(p1, p2, 4, 6);
		SwingUtilities.invokeLater(f);
		//Thread.sleep(200);
	//	e.begin();
	}
}
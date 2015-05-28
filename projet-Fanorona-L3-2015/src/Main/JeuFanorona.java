package Main;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{
		Engine e=new Engine();
		
		Player p1=new HumanPlayer(e,false,"Jean");
		Player p2=new HumanPlayer(e,false,"Pierre");

		Fenetre f=new Fenetre(e);
		e.setAffichage(f);
		
		e.nouvellePartie(p1, p2,0, 5,9);
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

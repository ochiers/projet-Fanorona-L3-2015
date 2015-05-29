package Main;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{

		EngineServices e=new Engine();

		Player p1=new HumanPlayer(e,false,"Jeremy");
		//Player p1=new EasyAI(e,true,"player1");
		Player p2=new MediumAI(e,true,"player2");
		Fenetre f= new Fenetre(e);
		e.setDisplay(f);
		//System.err.close();
		e.nouvellePartie(p1, p2,0, new Dimension(9,5));
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

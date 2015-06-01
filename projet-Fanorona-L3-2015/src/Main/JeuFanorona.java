package Main;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import network.NetworkPlayer;
import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException{

		EngineServices e=new Engine();
		Player p1=new HumanPlayer(e,false,"clem");
		//Player p1=new EasyAI(e,true,"player1");
		//Player p2=new NetworkPlayer(e,false,"seb");
		Player p2=new EasyAI(e,true,"player2");
		Fenetre f= new Fenetre(e);
		e.setDisplay(f);
		//e.hebergerPartie(12345);
		// System.err.close();
		e.nouvellePartie(p1, p2,0, new Dimension(9,5));
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

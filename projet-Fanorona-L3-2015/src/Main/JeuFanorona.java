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
		Player p1=new HumanPlayer(e,false,"seb");
		Player p2=new EasyAI(e,true,"player1");
		//Player p2=new NetworkPlayer(e,false,"clem");
		Fenetre f= new Fenetre(e);
		e.setDisplay(f);
		e.rejoindrePartie(12345, "152.77.82.223");
		// System.err.close();
		e.nouvellePartie(p1, p2,0, new Dimension(9,5));
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

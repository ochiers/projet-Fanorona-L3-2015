package Main;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import engine.*;
import IHM.*;
import AI.*;

public class JeuFanoronaIA {
	public static void main(String argv[]) throws InterruptedException{

		EngineServices e=new Engine();
		Player p1 = new HardAI(e,true,"Chuck Norris");
		Player p2 = new HumanPlayer(e,false,"Guillaume de Sauza");
		Fenetre f = new Fenetre(e);
		e.setDisplay(f);
		// System.err.close();
		e.nouvellePartie(p1, p2,0, new Dimension(9,5));
		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}
}

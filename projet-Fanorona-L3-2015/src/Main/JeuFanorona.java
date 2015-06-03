package Main;

import java.awt.Dimension;

import javax.swing.SwingUtilities;

import network.NetworkPlayer;
import engine.*;
import IHM.*;
import AI.*;

public class JeuFanorona {
	public static void main(String argv[]) throws InterruptedException
	{

		EngineServices e=new Engine();
		Player p1=null;
		Player p2=null;
		Fenetre f= new Fenetre(e);
		e.setDisplay(f);

		
		if (argv.length > 0)
		{
			if (argv[0].equals("--help") || argv[0].equals("-h"))
				usage();
			else if (argv[0].equals("--net"))
			{
				if (argv.length >= 3 && argv[1].toLowerCase().equals("server"))
				{
					e.hebergerPartie(Integer.parseInt(argv[2]));
					p1 = new HumanPlayer(e, false, "Joueur");
					p2 = new NetworkPlayer(e, false, "Player at " + e.getNetworkManager().socketEnvoiPrincipal.getInetAddress());
				} else if (argv.length >= 4 && argv[1].toLowerCase().equals("client"))
					if (Tools.isValidIP(argv[3]) || argv[3].toLowerCase().equals("localhost"))
					{
						p1 = new NetworkPlayer(e, false, "Player at " + argv[3]);
						p2 = new HumanPlayer(e, false, "Joueur");
						e.rejoindrePartie(Integer.parseInt(argv[2]), argv[3]);
					} else
						System.err.println("L'adresse ip fournie est invalide");
				else
					usage();
			}
			for (int i =0; i<argv.length; i++)
				if(argv[i].equals("-/v")){
					System.out.close();
					System.err.close();					
				}
		}
		if (p1 == null && p2 == null)
		{
			p1 = new HumanPlayer(e, false, "Clem");
			p2 = new EasyAI(e, true, "Solveur");
		}
		e.nouvellePartie(p1, p2, 0, new Dimension(9, 5));

		SwingUtilities.invokeLater(f);
		Thread.sleep(200);
		e.begin();
	}

	private static void usage()
	{
		System.out.println("\n--- Usage ---");
		System.out.println("\t -h or --help : Print this help");
		System.out.println("\t --net <server || client> <port> [ip] : host a game on the given port or establish a connection to the given ip and port");
		System.out.println("\t -v Allow debug in console, effective by default. -/v to desactivate");
		System.out.println("--- ----- ---  \n");
	}
}

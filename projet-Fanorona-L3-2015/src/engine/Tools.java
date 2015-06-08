package engine;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Random;

import network.NetworkPlayer;
import AI.*;

public class Tools {

	public static final Random rand = new Random();
	
	/**
	 * Cree un nouveau joueur
	 * 
	 * @param e
	 *            Le moteur associé
	 * @param p
	 *            Le type de joueur
	 * @param nom
	 *            Le nom du joueur
	 * @return
	 */
	public static Player createPlayer(EngineServices e, PlayerType p, String nom)
	{
		if (e == null || p == null || nom == null)
			throw new RuntimeException("IMPOSSIBLE DE CREE LE JOUEUR");

		Player res;
		switch (p)
		{
			case Humain:
				res = new HumanPlayer(e, false, nom);
				break;
			case IAFacile:
				res = new EasyAI(e, true, nom);
				break;
			case IAMoyenne:
				res = new MediumAI(e, true, nom);
				break;
			case IADifficile:
				res = new HardAI(e, true, nom);
				break;
			case Reseau:
				res = new NetworkPlayer(e, true, nom);
				break;
			default:
				throw new RuntimeException("IMPOSSIBLE DE CREE LE JOUEUR");
		}
		return res;
	}

	/**
	 * Donne le type d'un joueur
	 * 
	 * @param p
	 *            Le joueur
	 * @return Le type de p (appartien a PlayerType)
	 */
	public static PlayerType getTypeOfPlayer(Player p)
	{

		if (p instanceof HumanPlayer)
			return PlayerType.Humain;
		if (p instanceof EasyAI)
			return PlayerType.IAFacile;
		if (p instanceof MediumAI)
			return PlayerType.IAMoyenne;
		if (p instanceof HardAI)
			return PlayerType.IADifficile;
		if (p instanceof NetworkPlayer)
			return PlayerType.Reseau;

		throw new RuntimeException();
	}

	public static PlayerType getTypeOfPlayer(int p)
	{

		if (p == 0)
			return PlayerType.Humain;
		if (p == 1)
			return PlayerType.IAFacile;
		if (p == 2)
			return PlayerType.IAMoyenne;
		if (p == 3)
			return PlayerType.IADifficile;
		if (p == 4)
			return PlayerType.Reseau;

		throw new RuntimeException();
	}

	/**
	 * Donne la configuration d'une partie donnée
	 * 
	 * @param g
	 * @return
	 */
	public static Configuration getTypePartie(Game g)
	{
		if ((getTypeOfPlayer(g.joueurBlanc) == PlayerType.Humain && getTypeOfPlayer(g.joueurNoir) == PlayerType.Humain) || (getTypeOfPlayer(g.joueurBlanc) == PlayerType.Reseau || getTypeOfPlayer(g.joueurNoir) == PlayerType.Reseau))
			return Configuration.HumainVSHumain;
		if (g.joueurBlanc.aiPlayer && g.joueurNoir.aiPlayer)
			return Configuration.IAvsIA;
		if (g.joueurBlanc.aiPlayer || g.joueurNoir.aiPlayer)
			return Configuration.HumainVSIA;

		throw new RuntimeException();
	}

	public static Configuration getTypePartie(int g)
	{
		if (g == 0)
			return Configuration.HumainVSHumain;
		if (g == 1)
			return Configuration.HumainVSIA;
		if (g == 2)
			return Configuration.IAvsIA;

		throw new RuntimeException();
	}

	public static boolean isValidIP(String ip)
	{
		return ip.matches("^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");
	}

	/**
	 * Permet d'obtenir l'ip de la machine
	 * 
	 * @return Une chaine sous forme xxx.xxx.xxx.xxx
	 */
	public static String getIp()
	{
		String ipOrdi = "";
		Enumeration<NetworkInterface> e;

		try
		{
			e = NetworkInterface.getNetworkInterfaces();

			while (e.hasMoreElements())
			{
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration<InetAddress> ee = n.getInetAddresses();
				while (ee.hasMoreElements())
				{
					String i = ((InetAddress) ee.nextElement()).toString().replace("/", "");
					if (!i.toString().contains("127.0.0.1") && Tools.isValidIP(i))
						ipOrdi = i;
				}
			}
		} catch (SocketException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return ipOrdi;
	}

	public static void changerDeJoueur(EngineServices moteur, Configuration conf, PlayerType p1, PlayerType p2, String nomJ1, String nomJ2)
	{

		Player j1 = null;
		Player j2 = null;
		System.out.println("COnf : " + conf + ", p1 : "+ p1 + ", p2 : " + p2 );
		switch (conf)
		{
			case HumainVSHumain:
				if (p1 == PlayerType.Reseau)
				{
					j1 = new NetworkPlayer(moteur, false, nomJ1);
					j2 = new HumanPlayer(moteur, false, nomJ2);
				} else if (p2 == PlayerType.Reseau)
				{
					j2 = new NetworkPlayer(moteur, false, nomJ2);
					j1 = new HumanPlayer(moteur, false, nomJ1);
				} else
				{
					j1 = new HumanPlayer(moteur, false, nomJ1);
					j2 = new HumanPlayer(moteur, false, nomJ2);
				}
				break;
			case HumainVSIA:
				System.out.println(Configuration.HumainVSIA +" " + p1 +" " + p2);
				if (p1 == PlayerType.Reseau)
				{
					j1 = new NetworkPlayer(moteur, false, nomJ1);
					j2 = Tools.createPlayer(moteur, p2, nomJ2);
				} else if (p2 == PlayerType.Reseau)
				{
					j2 = new NetworkPlayer(moteur, false, nomJ2);
					j1 = Tools.createPlayer(moteur, p2, nomJ1);
				} else if (p1 == PlayerType.Humain)
				{
					j1 = new HumanPlayer(moteur, false, nomJ1);
					j2 = Tools.createPlayer(moteur, p2, nomJ2);
				}
				else if (p2 == PlayerType.Humain){
					j2 = new HumanPlayer(moteur, false, nomJ2);
					j1 = Tools.createPlayer(moteur, p2, nomJ1);
				}
				break;
			case IAvsIA:
				j1 = Tools.createPlayer(moteur, p1, nomJ1);
				j2 = Tools.createPlayer(moteur, p2, nomJ2);
				break;
			default:
				throw new RuntimeException();

		}
		moteur.changerLesJoueurs(j1, j2);
	}
}

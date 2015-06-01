package network;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import engine.Coordonnee;
import engine.Coup;
import engine.EngineServices;
import engine.Player;
import engine.Tools;

public class NetworkManager extends Thread {

	public String			ip;
	public int				port;
	public ServerSocket		socketServeurPrincipal;
	public Socket			socketEnvoiPrincipal;
	public InputStream		reception;
	public OutputStream		envoi;
	public EngineServices	leMoteur;
	public Coup				coupRecu;
	public Coordonnee		coordonneeRecu;
	private boolean			enReception;

	public NetworkManager(EngineServices e, int port, String ip)
	{
		this.leMoteur = e;
		this.ip = ip;
		this.port = port;
	}

	// TODO 1. Etablir la connexion
	// TODO 2. Envoyer la configuration
	// TODO 3. Envoyer un coup valide au moteur distant
	// TODO 4. Envoyer le coup au joueur (moteur -> joueur)

	// TODO Système de requêtes : Envoie d'une annulation/refaire

	// Comment déterminer celui qui commence ?
	// Le moteur analyse en local les coups tenter par le joueur humain puis
	// l'envoie sur le r�seau. Seul des coups valides sont envoyés.
	// Comment annuler et refaire en réseau ? Requête ?

	/*
	 * Serveur Principal : Seb L'autre ordi : Clem Seb ouvre un socket d'écoute Clem s'y connecte et envoie un numéro de port Seb ouvre un socket d'envoi Seb envoie la configuration dont celui qui commence
	 */

	/**
	 * L'ordinateur qui execute cette fonction est le serveur principal.
	 * 
	 */
	public void hebergerPartie()
	{	
		System.out.println("Partie en réseau ---------------------------------- ");
		try
		{
			socketServeurPrincipal = new ServerSocket(port);

			System.out.println("Serveur en ecoute sur le port : " + socketServeurPrincipal.getLocalPort());

			socketEnvoiPrincipal = socketServeurPrincipal.accept();
			reception = socketEnvoiPrincipal.getInputStream();
			envoi = socketEnvoiPrincipal.getOutputStream();

		} catch (Exception e)
		{
			System.err.println(e);
		}
	}

	/**
	 * Client
	 */
	public void rejoindrePartie()
	{
		System.out.println("Partie en réseau à rejoindre---------------------------------- ");
		try
		{
			InetAddress addr = InetAddress.getByName(ip);
			socketEnvoiPrincipal = new Socket(addr, port);

			this.envoi = socketEnvoiPrincipal.getOutputStream();
			this.reception = socketEnvoiPrincipal.getInputStream();

		} catch (Exception e)
		{
			System.err.println(e);
		}
		System.out.println("Partie en réseau rejointe ---------------------------------- ");
	}

	/**
	 * Envoie de la configuration de la machine principale vers la deuxi�me.
	 * 
	 * @throws IOException
	 */
	public void sendRequete(int req) throws IOException
	{
		System.out.println("Envoie de requete ---------------------------------- ");
		this.envoi.write(req);
		System.out.println("Requete envoie ---------------------------------- ");
	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2 ordinateurs.
	 * 
	 * @throws IOException
	 */
	public boolean receiveRequete() throws IOException
	{
		System.out.println("Reception requete ************************************************************************* ");
		int req = this.reception.read();
		switch (req)
		{
			case RequestType.EnvoiCoup:
				coupRecu = receiveCoup();
				break;
			case RequestType.Annuler:
				leMoteur.annuler();
				break;
			case RequestType.FinDuTour:
				leMoteur.finirSonTour();
				break;
			case RequestType.NouvellePartie:
			case RequestType.Quitter:
				terminerPartieReseau();
				leMoteur.stopper();
				return false;
			case RequestType.Recommencer:
				Player p1 = Tools.createPlayer(leMoteur, Tools.getTypeOfPlayer((leMoteur.getJoueurBlanc())), leMoteur.getJoueurBlanc().name);
				Player p2 = Tools.createPlayer(leMoteur, Tools.getTypeOfPlayer((leMoteur.getJoueurNoir())), leMoteur.getJoueurNoir().name);
				leMoteur.nouvellePartie(p1, p2, leMoteur.getCurrentGame().premierJoueur ? 0 : 1, new Dimension(9, 5));
				break;
			case RequestType.Refaire:
				leMoteur.refaire();
				break;
			case RequestType.EnvoiCase :
				coordonneeRecu = receiveCoordonnee();
		}
		System.out.println("Reception terminee ************************************************************************* ");
		return true;
		
	}

	public void terminerPartieReseau() throws IOException
	{
		this.reception.close();
		this.envoi.close();
		this.socketEnvoiPrincipal.close();
		this.socketServeurPrincipal.close();
	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	public void sendCoup(Coup c)
	{
		System.out.println("Envoi coup ************************************************************************* ");
		try
		{
			this.sendRequete(RequestType.EnvoiCoup);
			this.envoi.write(c.depart.colonne);
			attenteNotif();
			this.envoi.write(c.depart.ligne);
			attenteNotif();
			this.envoi.write(c.arrivee.colonne);
			attenteNotif();
			this.envoi.write(c.arrivee.ligne);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Coup recu ************************************************************************* ");
	}

	/**
	 * Réception du coup envoyé sur le réseau.
	 */
	public synchronized Coup receiveCoup()
	{
		System.out.println("Reception coup ************************************************************************* ");
		Coup c = null;
		enReception = true;
		try
		{
			int col1, lig1, col2, lig2;
			col1 = col2 = lig1 = lig2 = -1;

			while (col1 == -1)
				col1 = this.reception.read();
			System.out.print('a');
			System.out.println(this.envoi);
			Thread.sleep(500);
			this.envoi.write(100);
			System.out.print("a'");
			while (lig1 == -1)
				lig1 = this.reception.read();
			System.out.print('b');
			this.envoi.write(100);
			while (col2 == -1)
				col2 = this.reception.read();
			System.out.print('c');
			this.envoi.write(100);
			while (lig2 == -1)
				lig2 = this.reception.read();
			System.out.print('d');
			this.envoi.write(100);
			c = new Coup(new Coordonnee(lig1, col1), new Coordonnee(lig2, col2));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		enReception = false;
		System.out.println('e');
		notifyAll();
		System.out.println("Coup recu ************************************************************************* ");
		return c;

	}

	public synchronized Coordonnee receiveCoordonnee()
	{
		
		Coordonnee c = null;
		enReception = true;
		try
		{
			int col1, lig1;
			col1 = lig1 = -1;

			while (col1 == -1)
				col1 = this.reception.read();
			this.envoi.write(852);
			while (lig1 == -1)
				lig1 = this.reception.read();
			this.envoi.write(852);
			
			c = new Coordonnee(lig1,col1);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		enReception = false;
		notifyAll();
		return c;

	}
	
	public void attenteNotif() throws InterruptedException, IOException
	{

		while (this.reception.read() == -1)
		{
			Thread.sleep(50);
		}

	}

//	public static void main(String args[]) throws IOException
//	{
//
//		NetworkManager net = new NetworkManager(null, 12345, args[0]);
//		if (args[1].equals("client"))
//		{
//			net.rejoindrePartie();
//			net.sendCoup(new Coup(new Coordonnee(5, 9), new Coordonnee(4, 8)));
//			net.socketEnvoiPrincipal.close();
//		} else
//		{
//			net.hebergerPartie();
//			net.receiveCoup();
//			net.socketServeurPrincipal.close();
//		}
//
//	}

	public void run()
	{

		try
		{
			while (receiveRequete())
			{
				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public synchronized Coup getCoupRecu()
	{
		while (enReception)
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		Coup res = null;
		if (coupRecu != null)
			res = coupRecu;
		coupRecu = null;
		return res;
	}

	public synchronized Coordonnee getCoordonnee()
	{
		while (enReception)
			try
			{
				wait();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		Coordonnee res = null;
		if (coordonneeRecu != null)
			res = coordonneeRecu;
		coordonneeRecu = null;
		return res;
	}
}

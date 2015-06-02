package network;

import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import engine.Case;
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
	private Coup			coupAEnvoyer;
	private Coordonnee		coordonneeAEnvoyer;

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
	}

	public void terminerPartieReseau() throws IOException
	{
		this.reception.close();
		this.envoi.close();
		this.socketEnvoiPrincipal.close();
		this.socketServeurPrincipal.close();
	}

	/**
	 * Envoie de la configuration de la machine principale vers la deuxi�me.
	 * 
	 * @throws IOException
	 */
	public void sendRequete(int req) throws IOException
	{
		this.envoi.write(req);
	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2 ordinateurs.
	 * 
	 * @throws IOException
	 */
	public boolean receiveRequete() throws IOException
	{
		if (this.reception.available() > 0)
		{
			System.out.flush();
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
				case RequestType.EnvoiCase:
					coordonneeRecu = receiveCoordonnee();
			}
		}
		return true;

	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	public void sendObject(Object c, int req)
	{
		try
		{
			this.sendRequete(req);
			ObjectOutputStream out = new ObjectOutputStream(envoi);
			out.writeObject(c);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Réception du coup envoyé sur le réseau.
	 */
	public Coup receiveCoup()
	{
		Coup c = null;
		try
		{
			ObjectInputStream in = new ObjectInputStream(reception);
			c = (Coup) in.readObject();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		System.out.println("Coup recu ******************************** : " + c);
		return c;

	}


	public Coordonnee receiveCoordonnee()
	{

		Coordonnee c = null;
		try
		{
			ObjectInputStream in = new ObjectInputStream(reception);
			c = (Coordonnee) in.readObject();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;

	}

	private void envoyerCoordonnee()
	{
		if (coordonneeAEnvoyer != null)
			sendObject(coordonneeAEnvoyer, RequestType.EnvoiCase);
		coordonneeAEnvoyer = null;
	}

	private void envoyerCoup()
	{
		if (coupAEnvoyer != null)
			sendObject(coupAEnvoyer, RequestType.EnvoiCoup);
		coupAEnvoyer = null;
	}

	public Coup getCoupRecu()
	{

		Coup res = null;
		if (coupRecu != null)
			res = coupRecu;
		coupRecu = null;
		return res;
	}

	public Coordonnee getCoordonnee()
	{
		Coordonnee res = null;
		if (coordonneeRecu != null)
			res = coordonneeRecu;
		coordonneeRecu = null;
		return res;
	}

	public void setCoupAEnvoyer(Coup aEnvoyer)
	{
		this.coupAEnvoyer = aEnvoyer;
	}

	public void setCoordoneeAEnvoyer(Coordonnee aEnvoyer)
	{
		this.coordonneeAEnvoyer = aEnvoyer;
	}

	public void attenteNotif() throws InterruptedException, IOException
	{
	
		System.out.print("J'attend");
		int recu = -1;
		while (recu == -1)
		{
			recu = this.reception.read();
		}
	}

	public void run()
	{
		try
		{
			while (receiveRequete())
			{
				try
				{
					envoyerCoordonnee();
					envoyerCoup();
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

}

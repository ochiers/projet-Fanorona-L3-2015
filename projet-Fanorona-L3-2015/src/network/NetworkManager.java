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
import java.util.Stack;

import engine.Case;
import engine.Coordonnee;
import engine.Coup;
import engine.EngineServices;
import engine.Player;
import engine.Tools;

/**
 * Mini moteur rataché au moteur pricipal, sert d'interface avec le reseau
 * 
 * @author soulierc
 *
 */
public class NetworkManager extends Thread {

	/**
	 * L'ip de l'ordinateur distant si on s'est connecté
	 */
	public String				ip;
	/**
	 * Le port de la connection actuelle
	 */
	public int					port;
	/**
	 * Le socket serveur acceptant les connections.
	 */
	public ServerSocket			socketServeurPrincipal;
	/**
	 * Le socket de la connection permettant les echanges
	 */
	public Socket				socketEnvoiPrincipal;
	/**
	 * Le flux d'entree
	 */
	public InputStream			reception;
	/**
	 * Le flux de sortie
	 */
	public OutputStream			envoi;
	/**
	 * Le moteur du jeu
	 */
	public EngineServices		leMoteur;
	/**
	 * Variable-buffer qui contien le coup qui vient d'etre recu depuis le
	 * reseau
	 */
	public Stack<Coup>			coupsRecu;
	/**
	 * Variable-buffer qui contien la coordonne qui vient d'etre recu depuis le
	 * reseau
	 */
	public Stack<Coordonnee>	coordonneesRecu;

	/**
	 * Le coup qui doit etre envoyé sur le reseau
	 */
	private Coup				coupAEnvoyer;
	/**
	 * La coordonnee qui doit etre envoyee sur le reseau
	 */
	private Coordonnee			coordonneeAEnvoyer;

	public Multicast			serveurMulticast;

	public NetworkManager(EngineServices e, int port, String ip)
	{
		this.leMoteur = e;
		this.ip = ip;
		this.port = port;
		this.coupsRecu = new Stack<Coup>();
		this.coordonneesRecu = new Stack<Coordonnee>();
	}

	/**
	 * L'ordinateur qui execute cette fonction est le serveur principal.
	 * 
	 * @throws IOException
	 * 
	 */
	public void hebergerPartie() throws IOException
	{

		socketServeurPrincipal = new ServerSocket(port);
		this.serveurMulticast = new Multicast("224.3.3.3", 12344, port, true);
		System.out.println("Serveur en ecoute sur " + Tools.getIp() + ":" + socketServeurPrincipal.getLocalPort());

		socketEnvoiPrincipal = socketServeurPrincipal.accept();
		reception = socketEnvoiPrincipal.getInputStream();
		envoi = socketEnvoiPrincipal.getOutputStream();
		
	}

	/**
	 * Client
	 * 
	 * @throws IOException
	 */
	public void rejoindrePartie() throws IOException
	{
		InetAddress addr = InetAddress.getByName(ip);
		socketEnvoiPrincipal = new Socket(addr, port);

		this.envoi = socketEnvoiPrincipal.getOutputStream();
		this.reception = socketEnvoiPrincipal.getInputStream();

	}

	public void terminerPartieReseau() throws IOException
	{
		if (this.reception != null)
			this.reception.close();
		if (this.envoi != null)
			this.envoi.close();
		if (socketEnvoiPrincipal != null)
			this.socketEnvoiPrincipal.close();
		if (socketServeurPrincipal != null)
			this.socketServeurPrincipal.close();
	}

	/**
	 * Envoie de la configuration de la machine principale vers la deuxi�me.
	 * 
	 * @throws IOException
	 */
	public void sendRequete(int req)
	{
		try
		{
			this.envoi.write(req);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2
	 * ordinateurs.
	 * 
	 * @throws IOException
	 */
	private boolean receiveRequete() throws IOException
	{
		if (this.reception.available() > 0)
		{
			int req = this.reception.read();
			switch (req)
			{
				case RequestType.EnvoiCoup:
					coupsRecu.push(receiveCoup());
					break;
				case RequestType.Annuler:
					leMoteur.annuler(false);
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
					leMoteur.refaire(false);
					break;
				case RequestType.EnvoiCase:
					coordonneesRecu.add(receiveCoordonnee());
			}
		}
		return true;

	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	private void sendObject(Object c, int req)
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
	private Coup receiveCoup()
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

	private Coordonnee receiveCoordonnee()
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
		if (!coupsRecu.empty())
			res = coupsRecu.pop();

		return res;
	}

	public Coordonnee getCoordonnee()
	{
		Coordonnee res = null;
		if (!coordonneesRecu.empty())
			res = coordonneesRecu.pop();
		return res;
	}

	/**
	 * Donne le coup a envoyer sur le reseau
	 * 
	 * @param aEnvoyer
	 *            Un coup
	 */
	public void setCoupAEnvoyer(Coup aEnvoyer)
	{
		this.coupAEnvoyer = aEnvoyer;
	}

	/**
	 * Donne la coordonnee a envoyer sur le reseau
	 * 
	 * @param aEnvoyer
	 *            Une coordonnee
	 */
	public void setCoordoneeAEnvoyer(Coordonnee aEnvoyer)
	{
		this.coordonneeAEnvoyer = aEnvoyer;
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

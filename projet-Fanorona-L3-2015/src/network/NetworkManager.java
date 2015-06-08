package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

import engine.Coordonnee;
import engine.Coup;
import engine.EngineServices;
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
	 * Variable-buffer qui contien le coup qui vient d'etre recu depuis le reseau
	 */
	public Stack<Coup>			coupsRecu;
	/**
	 * Variable-buffer qui contien la coordonne qui vient d'etre recu depuis le reseau
	 */
	public Stack<Coordonnee>	coordonneesRecues;

	public int					confirmationRecue;

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
		this.coordonneesRecues = new Stack<Coordonnee>();
		this.confirmationRecue = -1;
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
		this.serveurMulticast = new Multicast(leMoteur,"224.3.3.3", 12344, port, true);
		System.out.println("Serveur en ecoute sur " + Tools.getIp() + ":" + socketServeurPrincipal.getLocalPort());

		socketEnvoiPrincipal = socketServeurPrincipal.accept();
		reception = socketEnvoiPrincipal.getInputStream();
		envoi = socketEnvoiPrincipal.getOutputStream();
		this.serveurMulticast.terminer();
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
			if(envoi != null)
				this.envoi.write(req);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2 ordinateurs.
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
					leMoteur.finirSonTour(false);
					break;
				case RequestType.NouvellePartie:
				case RequestType.Quitter:
					terminerPartieReseau();
					leMoteur.stopper();
					leMoteur.getCurrentDisplay().afficherMessage("Le joueur s'est déconnecté");
					return false;
				case RequestType.Recommencer:
					leMoteur.recommencer(false);
					break;
				case RequestType.Refaire:
					leMoteur.refaire(false);
					break;
				case RequestType.EnvoiCase:
					coordonneesRecues.add(receiveCoordonnee());
					break;
				case RequestType.DemanderConfirmationAnnuler:
					if (leMoteur.getCurrentDisplay().demanderConfirmation("Le joueur veut annuler un coup,\n autoriser ?"))
						sendRequete(RequestType.ReponseOUI);
					else
						sendRequete(RequestType.ReponseNON);
					break;
				case RequestType.DemanderConfirmationRefaire:
					if (leMoteur.getCurrentDisplay().demanderConfirmation("Le joueur veut refaire le coup,\n autoriser ?"))
						sendRequete(RequestType.ReponseOUI);
					else
						sendRequete(RequestType.ReponseNON);
					break;
				case RequestType.ReponseOUI:
					confirmationRecue = 1;
					break;
				case RequestType.ReponseNON:
					confirmationRecue = 0;
					break;
				case RequestType.DemanderConfirmationRecommencer : 
					if (leMoteur.getCurrentDisplay().demanderConfirmation("Le joueur veut recommencer la partie,\n autoriser ?"))
						sendRequete(RequestType.ReponseOUI);
					else
						sendRequete(RequestType.ReponseNON);
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

	/**
	 * Donne le dernier coup recu
	 * @return
	 */
	public Coup getCoupRecu()
	{
		Coup res = null;
		if (!coupsRecu.empty())
			res = coupsRecu.pop();

		return res;
	}

	/**
	 * Donne la derniere coordonnee recue
	 * @return
	 */
	public Coordonnee getCoordonnee()
	{
		Coordonnee res = null;
		if (!coordonneesRecues.empty())
			res = coordonneesRecues.pop();
		return res;
	}

	/**
	 * Donne la confiramtion recue
	 * @return La confirmation recu (0 ou 1) ou -1 si on a rien recu
	 */
	public int getConfirmation()
	{
		int res = -1;
		if (confirmationRecue != -1)
			res = confirmationRecue;
		confirmationRecue = -1;
		return res;
	}

	/**
	 * Demande une confirmation au joueur distant
	 * @param req La reqeste de confirmation
	 */
	public void demanderConfirmation(int req)
	{
		this.sendRequete(req);
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

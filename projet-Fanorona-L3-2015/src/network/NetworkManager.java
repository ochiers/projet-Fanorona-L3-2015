package network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import engine.Coordonnee;
import engine.Coup;

public class NetworkManager {

	public String		ip;
	public int			port;
	public ServerSocket	socketServeurPrincipal;
	public Socket		socketEnvoiPrincipal;
	public InputStream	reception;
	public OutputStream	envoi;	

	public NetworkManager(int port, String ip)
	{

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

	/**
	 * Envoie de la configuration de la machine principale vers la deuxi�me.
	 */
	public void sendConfig()
	{

	}

	/**
	 * Méthode permettant de recevoir la configuration partagée entre les 2 ordinateurs.
	 */
	public void receiveConfig()
	{

	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	public void sendCoup(Coup c)
	{
		try{
			this.envoi.write(c.depart.colonne);
			attenteNotif();
			this.envoi.write(c.depart.ligne);
			attenteNotif();
			this.envoi.write(c.arrivee.colonne);
			attenteNotif();
			this.envoi.write(c.arrivee.ligne);
		}
		catch (Exception e){}
	}

	/**
	 * Réception du coup envoyé sur le réseau.
	 */
	public Coup receiveCoup()
	{
		Coup c = null;
		try{
			int col1,lig1,col2,lig2;
			col1 = col2 = lig1 = lig2 = -1;
			while(col1 == -1){
				col1 = this.reception.read();
				System.out.println("Premier envoi");
			}
			this.envoi.write(852);
			while(lig1 == -1)
				lig1 = this.reception.read();
			this.envoi.write(852);
			while(col2 == -1)
				col2 = this.reception.read();
			this.envoi.write(852);
			while(lig2 == -1)
				lig2 = this.reception.read();
			this.envoi.write(852);
			c = new Coup(new Coordonnee(lig1, col1), new Coordonnee(lig2, col2));
		}
		catch (Exception e){}
		System.out.println(c);
		return c;

	}
	
	public void attenteNotif() throws InterruptedException, IOException{
		
		while(this.reception.read() != -1)
			Thread.sleep(50);
		
	}
	
	public static void main(String args[]) throws IOException{
		
		NetworkManager net = new NetworkManager(12345, args[0]);
		if(args[1].equals("client")){
			net.rejoindrePartie();
			net.sendCoup(new Coup(new Coordonnee(5,9),new Coordonnee(4,8)));
			net.socketEnvoiPrincipal.close();
		}
		else{
			net.hebergerPartie();
			net.receiveCoup();
			net.socketServeurPrincipal.close();
		}
		
	}
}

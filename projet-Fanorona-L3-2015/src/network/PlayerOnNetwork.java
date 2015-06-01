package network;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PlayerOnNetwork {

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
	 * Serveur Principal : Seb
	 * L'autre ordi : Clem
	 * 
	 * Seb ouvre un socket d'écoute
	 * Clem s'y connecte et envoie un numéro de port
	 * Seb ouvre un socket d'envoi
	 * Seb envoie la configuration dont celui qui commence
	 * 
	 */
	
	
	
	
	
	/**
	 * L'ordinateur qui execute cette fonction est le serveur principal.
	 * 
	 */
	public int hebergerPartie()
	{
		ServerSocket socketServeurPrincipal = null;
		Socket socketEnvoiPrincipal;
		try
		{
			socketServeurPrincipal = new ServerSocket(0);
			boolean finished = false;

			System.out.println("Serveur en ecoute sur le port : " + socketServeurPrincipal.getLocalPort());
			while (!finished)
			{
				socketEnvoiPrincipal = socketServeurPrincipal.accept();
				InputStream in = socketEnvoiPrincipal.getInputStream();
				byte[] buffer = new byte[1024];
				int number;
				while ((number = in.read(buffer)) != -1)
				{
					System.out.write(buffer, 0, number);
				}
				finished = true;
			}
		} catch (Exception e)
		{
			System.err.println(e);
		}
		return socketServeurPrincipal.getLocalPort();
	}

	/**
	 * Client
	 */
	public void rejoindrePartie(String ip, int port)
	{
		ServerSocket socketReception;
		Socket socketEnvoi;
		try {
            InetAddress addr = InetAddress.getByName(ip);
            socketEnvoi = new Socket(addr, port);
            PrintStream print = new PrintStream(socketEnvoi.getOutputStream());

            print.println("Salut, je viens de me connecter");
            print.println("Bye bye");
            print.close();
        } catch (Exception e) {
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
	 * Méthode permettant de recevoir la configuration partagée entre les 2
	 * ordinateurs.
	 */
	public void receiveConfig()
	{

	}

	/**
	 * Envoie du coup valide du joueur humain.
	 */
	public void sendCoup()
	{

	}

	/**
	 * Réception du coup envoyé sur le réseau.
	 */
	public void receiveCoup()
	{

	}
}

package network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

import engine.EngineServices;
import engine.Tools;

class Recepteur extends Thread {
	public final Multicast	multiCast;
	public InetAddress		groupeIP;
	public String			nom;
	public MulticastSocket	socketReception;
	private boolean			isHost;
	public int				portGame;
	public boolean			stopped;

	Recepteur(Multicast m, InetAddress groupeIP, int port, String nom, boolean isHost, int portGame) throws Exception
	{
		this.multiCast = m;
		this.groupeIP = groupeIP;
		this.portGame = portGame;
		this.nom = nom;
		this.isHost = isHost;
		socketReception = new MulticastSocket(port);
		socketReception.joinGroup(groupeIP);
		start();
	}

	public void run()
	{
		DatagramPacket message;
		byte[] contenuMessage;
		String texte;

		while (!stopped)
		{
			contenuMessage = new byte[1024];
			message = new DatagramPacket(contenuMessage, contenuMessage.length);
			try
			{
				socketReception.receive(message);
				texte = (new DataInputStream(new ByteArrayInputStream(contenuMessage))).readUTF();
				if (!texte.startsWith(nom))
					continue;
				if (texte.contains("QuiEstLa?") && isHost)
				{
					System.out.println("Requete recue, j'emet ...");
					multiCast.emeteur.aEmettre = nom + "Je suis #" + Tools.getIp() + ":" + portGame;
				} else if (texte.contains("#")){
					multiCast.dicoveredHosts.add(multiCast.moteur.getJoueurCourant().name +" at " +texte.split("#")[1]);
				}
			} catch (Exception exc)
			{
				exc.printStackTrace();
			}
		}
	}
}

class Emetteur extends Thread {
	public final Multicast	multiCast;
	public InetAddress		groupeIP;
	public int				port;
	public MulticastSocket	socketEmission;
	public String			nom;
	public String			aEmettre;
	public boolean			stopped;

	Emetteur(Multicast m, InetAddress groupeIP, int port, String nom) throws Exception
	{
		this.multiCast = m;
		this.groupeIP = groupeIP;
		this.port = port;
		this.nom = nom;
		socketEmission = new MulticastSocket();
		socketEmission.setTimeToLive(15); // pour un site
		start();
	}

	public void run()
	{
		try
		{
			while (!stopped)
			{
				if (aEmettre != null)
				{
					emettre(aEmettre);
					aEmettre = null;
				}
				Thread.sleep(200);
			}
		} catch (Exception exc)
		{
			System.err.println(exc);
		}
	}

	void emettre(String texte) throws Exception
	{
		byte[] contenuMessage;
		DatagramPacket message;
		ByteArrayOutputStream sortie = new ByteArrayOutputStream();
		(new DataOutputStream(sortie)).writeUTF(texte);
		contenuMessage = sortie.toByteArray();
		message = new DatagramPacket(contenuMessage, contenuMessage.length, groupeIP, port);
		socketEmission.send(message);
	}
}

public class Multicast {

	public final String			ip;
	public final int			portMultiCast;
	public final int			portGame;
	public static final String	identifiant	= "Fanorona_Multicast";
	public Emetteur				emeteur;
	public Recepteur			recepteur;
	public ArrayList<String>	dicoveredHosts;
	public EngineServices moteur;
	public Multicast(EngineServices m, String ipMulticast, int portMulticast, int portGame, boolean isHost)
	{
		if (!Tools.isValidIP(ipMulticast))
			throw new RuntimeException();
		this.moteur = m;
		this.ip = ipMulticast;
		this.portMultiCast = portMulticast;
		this.portGame = portGame;
		this.dicoveredHosts = new ArrayList<String>();
		InetAddress groupeIP;
		try
		{
			groupeIP = InetAddress.getByName(ip);
			recepteur = new Recepteur(this, groupeIP, portMultiCast, identifiant, isHost, portGame);
			emeteur = new Emetteur(this, groupeIP, portMultiCast, identifiant);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public ArrayList<String> trouverDesParties(int timeOut)
	{
		System.out.println("Recherche de parties ...");
		try
		{
			int i = 0;
			while (i < 5 && this.dicoveredHosts.size() == 0)
			{
				emeteur.aEmettre = identifiant + "QuiEstLa?";
				i++;
			}
			Thread.sleep(timeOut / 5);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Liste obtenue" + this.dicoveredHosts);
		return this.dicoveredHosts;
	}

	@SuppressWarnings("deprecation")
	public void terminer()
	{
		if (emeteur != null)
		{
			emeteur.socketEmission.close();
			emeteur.stopped = true;
			//emeteur.stop();
		}
		if (recepteur != null)
		{
			recepteur.socketReception.close();
			recepteur.stopped = true;
			//recepteur.stop();
		}
	}

	/*public static void main(String[] arg)
	{

		if (arg[0].equals("host"))
		{
			Multicast m = new Multicast("224.2.2.3", 8965, 12345, true);
		} else
		{
			Multicast m2 = new Multicast("224.2.2.3", 8965, 12345, false);
			ArrayList<String> l = m2.trouverDesParties(4000);
			System.out.println(l.size());
			for (int i = 0; i < l.size(); i++)
				System.out.println(l.get(i));
		}
	}*/
}

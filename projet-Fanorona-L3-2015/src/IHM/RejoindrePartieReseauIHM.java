package IHM;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AI.HumanPlayer;
import network.NetworkPlayer;
import engine.Engine;
import engine.EngineServices;
import engine.Player;
import engine.Tools;

public class RejoindrePartieReseauIHM extends JFrame {

	public static JFrame		frame;
	public static Engine		leMoteur;

	private static String		titleFrame	= "Rejoindre une partie en réseau";
	private static int			width		= 280;
	private static int			height		= 200;

	public static JButton		bt_rejoindre;
	public static JButton		bt_annuler;

	public static JTextField	txt_saisieIp;
	public static JTextField	txt_saisiePort;

	public RejoindrePartieReseauIHM(Engine moteur)
	{
		this.leMoteur = moteur;
	}

	public static void init()
	{
		frame = new JFrame(titleFrame);
		frame.setSize(width, height);

		JPanel pan = new JPanel();
		pan.setLayout(null);

		JLabel explicationIP = new JLabel("Adresse IP visée : ");
		explicationIP.setBounds(20, 20, 150, 40);

		JLabel explicationPort = new JLabel("Port visé : ");
		explicationPort.setBounds(75, 70, 100, 40);

		txt_saisieIp = new JTextField("192.168.0.2", 15);
		txt_saisieIp.setBounds(150, 20, 100, 40);

		txt_saisiePort = new JTextField("12345", 5);
		txt_saisiePort.setBounds(150, 70, 100, 40);

		bt_rejoindre = new JButton("Rejoindre");
		bt_rejoindre.setBounds(20, 120, 125, 40);

		bt_annuler = new JButton("Annuler");
		bt_annuler.setBounds(150, 120, 100, 40);

		pan.add(explicationIP);
		pan.add(txt_saisieIp);

		pan.add(explicationPort);
		pan.add(txt_saisiePort);

		pan.add(bt_rejoindre);
		pan.add(bt_annuler);

		frame.add(pan);
		frame.setLocation(width, height);
		frame.setVisible(true);
	}

	public static void main(String argv[])
	{
		init();
	}

}

class rejoindreListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Engine moteur = RejoindrePartieReseauIHM.leMoteur;
		String ip = RejoindrePartieReseauIHM.txt_saisieIp.getText();
		if (Tools.isValidIP(ip))
		{
			Player p1 = new NetworkPlayer(moteur, false, "Player at " + ip);
			Player p2 = new HumanPlayer(moteur, false, "Joueur");
			moteur.rejoindrePartie(Integer.parseInt(RejoindrePartieReseauIHM.txt_saisiePort.getText()), ip);
			moteur.nouvellePartie(p1, p2, 0, new Dimension(9, 5));
		}
	}
}

class annulerListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e)
	{
		RejoindrePartieReseauIHM.frame.setVisible(false);
	}

}

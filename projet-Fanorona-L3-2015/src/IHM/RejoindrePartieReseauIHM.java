package IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import AI.HumanPlayer;
import network.NetworkPlayer;
import engine.EngineServices;
import engine.Player;
import engine.Tools;

public class RejoindrePartieReseauIHM extends JFrame {

	private static final long	serialVersionUID	= 1L;
	
	private EngineServices		leMoteur;
	public JFrame		frame;

	private String		titleFrame	= "Rejoindre une partie en réseau";
	private int			width		= 280;
	private int			height		= 200;

	public JButton		bt_rejoindre;
	public JButton		bt_annuler;

	public JTextField	txt_saisieIp;
	public JTextField	txt_saisiePort;

	public RejoindrePartieReseauIHM(EngineServices moteur){
		this.setLeMoteur(moteur);
		init();
	}

	public void init(){
		this.setTitle(titleFrame);
		this.setSize(width, height);

		JPanel pan = new JPanel();
		pan.setLayout(null);

		JLabel explicationIP = new JLabel("Adresse IP visee : ");
		explicationIP.setBounds(20, 20, 150, 40);

		JLabel explicationPort = new JLabel("Port vise : ");
		explicationPort.setBounds(75, 70, 100, 40);

		txt_saisieIp = new JTextField("192.168.0.2", 15);
		txt_saisieIp.setBounds(150, 20, 100, 40);

		txt_saisiePort = new JTextField("12345", 5);
		txt_saisiePort.setBounds(150, 70, 100, 40);

		bt_rejoindre = new JButton("Rejoindre");
		bt_rejoindre.setBounds(20, 120, 125, 40);

		bt_annuler = new JButton("Annuler");
		bt_annuler.setBounds(150, 120, 100, 40);
		
		bt_rejoindre.addActionListener(new rejoindreListener(this));
		bt_annuler.addActionListener(new annulerListener(this));
		
		pan.add(explicationIP);
		pan.add(txt_saisieIp);

		pan.add(explicationPort);
		pan.add(txt_saisiePort);

		pan.add(bt_rejoindre);
		pan.add(bt_annuler);

		this.add(pan);
		this.setLocation(width, height);
		this.setVisible(true);
	}


	public EngineServices getLeMoteur(){
		return leMoteur;
	}

	public void setLeMoteur(EngineServices leMoteur){
		this.leMoteur = leMoteur;
	}

}

class rejoindreListener implements ActionListener {

	RejoindrePartieReseauIHM r;
	
	public rejoindreListener(RejoindrePartieReseauIHM r)
	{
		this.r =r;
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		String ip = r.txt_saisieIp.getText();
		if (Tools.isValidIP(ip)){
			Player p1 = new NetworkPlayer(r.getLeMoteur(), false, "Player at " + ip);
			Player p2 = new HumanPlayer(r.getLeMoteur(), false, "Joueur");
			try
			{
				r.getLeMoteur().rejoindrePartie(Integer.parseInt(r.txt_saisiePort.getText()), ip);
				r.getLeMoteur().nouvellePartie(p1, p2, 0, new Dimension(9, 5));
				
				System.out.println("OK");
			} catch (NumberFormatException | IOException e1)
			{
				JOptionPane.showMessageDialog((Component) e.getSource(), "Impossible de se connecter a " + ip + "sur le port" +  r.txt_saisiePort, "Connection impossible", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}
}

class annulerListener implements ActionListener {

	RejoindrePartieReseauIHM r;
	
	public annulerListener(RejoindrePartieReseauIHM rejoindrePartieReseauIHM)
	{
		this.r = rejoindrePartieReseauIHM;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		r.setVisible(false);
	}

}

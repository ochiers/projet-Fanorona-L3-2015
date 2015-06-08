package IHM;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import network.*;
import engine.EngineServices;
import engine.HumanPlayer;
import engine.Player;
import engine.Tools;

public class RejoindrePartieReseauIHM extends JFrame {

	private static final long	serialVersionUID	= 1L;

	private EngineServices		leMoteur;
	public JFrame				frame;

	private String				titleFrame			= "Rejoindre une partie en réseau";
	private int					width				= 480;
	private int					height				= 200;

	public JButton				bt_rejoindre;
	public JButton				bt_annuler;

	public JTextField			txt_saisieIp;
	public JTextField			txt_saisiePort;

	public JList<String>		list_detection;

	public RejoindrePartieReseauIHM(EngineServices moteur)
	{
		this.setLeMoteur(moteur);
		init();
	}

	public void init()
	{
		this.setTitle(titleFrame);
		this.setSize(width, height);

		JPanel pan = new JPanel();
		pan.setLayout(null);

		JLabel explicationIP = new JLabel("Adresse IP visee : ");
		explicationIP.setBounds(20, 20, 150, 40);

		JLabel explicationPort = new JLabel("Port vise : ");
		explicationPort.setBounds(75, 70, 100, 40);

		JLabel explicationDetection = new JLabel("Détection de partie : ");
		explicationDetection.setBounds(290, 5, 190, 20);

		txt_saisieIp = new JTextField("xxx.xxx.xxx.xxx", 15);
		txt_saisieIp.setBounds(150, 20, 100, 40);

		txt_saisiePort = new JTextField("12345", 5);
		txt_saisiePort.setBounds(150, 70, 100, 40);

		Multicast m = new Multicast(leMoteur,"224.3.3.3", 12344, 12345, false);
		ArrayList<String> l = m.trouverDesParties(2000);
		Iterator<String> it_l = l.iterator();
		DefaultListModel<String> modelData = new DefaultListModel<String>();
		while (it_l.hasNext())
			modelData.addElement(it_l.next());
		list_detection = new JList<String>(modelData);
		list_detection.setBounds(290, 30, 180, 130);
		list_detection.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list_detection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list_detection.addListSelectionListener(new listChangeListener(this));
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

		pan.add(explicationDetection);
		pan.add(list_detection);

		pan.add(bt_rejoindre);
		pan.add(bt_annuler);

		this.add(pan);
		this.setLocation(width, height);
		this.setVisible(true);
	}

	public EngineServices getLeMoteur()
	{
		return leMoteur;
	}

	public void setLeMoteur(EngineServices leMoteur)
	{
		this.leMoteur = leMoteur;
	}

}

class rejoindreListener implements ActionListener {

	RejoindrePartieReseauIHM	r;

	public rejoindreListener(RejoindrePartieReseauIHM r)
	{
		this.r = r;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String ipSaisie = r.txt_saisieIp.getText();
		String selection = r.list_detection.getSelectedValue();
		if (Tools.isValidIP(ipSaisie) || selection != null)
		{
			String ip = ipSaisie;
			int port = Integer.parseInt(r.txt_saisiePort.getText());
			if (selection != null)
			{
				String[] selectionParse = selection.split(":");
				ip = selectionParse[0].replaceAll("[^0-9\\.]", "");
				if(!Tools.isValidIP(ip))
					System.err.println("L'ip n'est pas bonne, " + ip);
				port = Integer.parseInt(selectionParse[1]);
			}
			Player p1 = new NetworkPlayer(r.getLeMoteur(), false, "Player at " + ip);
			Player p2 = new HumanPlayer(r.getLeMoteur(), false, "Joueur");
			try
			{
				r.getLeMoteur().rejoindrePartie(port, ip);
				r.getLeMoteur().nouvellePartie(p1, p2, 0, new Dimension(9, 5));

				r.getLeMoteur().getCurrentDisplay().afficherJeu();
				System.out.println("OK");
			} catch (NumberFormatException | IOException e1)
			{
				JOptionPane.showMessageDialog((Component) e.getSource(), "Impossible de se connecter a " + ip + "sur le port" + r.txt_saisiePort.getText(), "Connection impossible", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			r.setVisible(false);
		} else
		{
			System.err.println("Erreur : L'ip n'est pas bonne");
		}
	}
}

class annulerListener implements ActionListener {

	RejoindrePartieReseauIHM	r;

	public annulerListener(RejoindrePartieReseauIHM rejoindrePartieReseauIHM)
	{
		this.r = rejoindrePartieReseauIHM;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		r.setVisible(false);
	}

}

class listChangeListener implements ListSelectionListener{

	public RejoindrePartieReseauIHM r;
	
	public listChangeListener(RejoindrePartieReseauIHM r)
	{
		this.r=r;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e)
	{
		String str[] = r.list_detection.getSelectedValue().split(":");
		r.txt_saisieIp.setText(str[0].replaceAll("[^0-9\\.]", ""));
		r.txt_saisiePort.setText(str[1]);
	}
	
}

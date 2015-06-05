package IHM;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import engine.Configuration;
import engine.Player;
import engine.PlayerType;
import engine.Tools;

public class Parametres {
	JRadioButton	r1b1;
	JRadioButton	r1b2;
	JRadioButton	r1b3;
	JRadioButton	r2b1;
	JRadioButton	r2b2;
	JRadioButton	r2b3;
	JRadioButton	r3b1;
	JRadioButton	r3b2;
	JRadioButton	r3b3;

	/*
	 * int saveMode; int savelvlPC1; int savelvlPC2;
	 */

	Configuration	saveMode;
	PlayerType		savelvlPC1;
	PlayerType		savelvlPC2;
	boolean			saveCommencer;

	JButton			accepter;
	JButton			annuler;
	JComboBox		box1;
	JComboBox		box2;
	JComboBox		box3;
	JComboBox		box4;

	public Fenetre	fenetre;

	public Parametres(Fenetre f)
	{
		fenetre = f;
	}

	public void majParam()
	{
		// fenetre.frame2.setSize(300, 300);
		JPanel panel = new JPanel(new GridLayout(0, 2));
		JLabel labelbox1 = new JLabel("Mode de jeu");
		JLabel labelbox2 = new JLabel("Difficulte Ordi 1");
		JLabel labelbox3 = new JLabel("Difficulte Ordi 2");
		JLabel labelbox4 = new JLabel("Qui commence ?");

		String[] tab1 = { "Joueur vs Joueur", "Joueur vs Ordi", "Ordi vs Ordi" };
		String[] tab2 = { "Facile", "Moyen", "Difficile" };
		String[] tab3 = { "Facile", "Moyen", "Difficile" };
		String[] tab4 = { "Joueur 1", "Joueur 2" };

		box1 = new JComboBox(tab1);
		box2 = new JComboBox(tab2);
		box3 = new JComboBox(tab3);
		box4 = new JComboBox(tab4);

		box1.addActionListener(new ItemAction_box1());
		box2.addActionListener(new ItemAction_box2());
		box3.addActionListener(new ItemAction_box3());
		box4.addActionListener(new ItemAction_box4());

		// Selection des Boutons
		fenetre.commencer = fenetre.engine.getPremierJoueur();
		fenetre.mode = engine.Tools.getTypePartie(fenetre.engine.getCurrentGame());
		if (fenetre.engine.getCurrentGame().joueurBlanc.aiPlayer || fenetre.engine.getCurrentGame().joueurNoir.aiPlayer)
		{
			if (fenetre.engine.getCurrentGame().joueurBlanc.aiPlayer && fenetre.engine.getCurrentGame().joueurNoir.aiPlayer)
			{
				fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getCurrentGame().joueurBlanc);
				fenetre.lvlPC2 = engine.Tools.getTypeOfPlayer(fenetre.engine.getCurrentGame().joueurNoir);
			} else
			{
				if (fenetre.engine.getCurrentGame().joueurBlanc.aiPlayer)
				{
					fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getCurrentGame().joueurBlanc);
					fenetre.lvlPC2 = fenetre.defaut;
				} else
				{
					fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getCurrentGame().joueurNoir);
					fenetre.lvlPC2 = fenetre.defaut;
				}
			}
		} else
		{
			fenetre.lvlPC1 = fenetre.defaut;
			fenetre.lvlPC2 = fenetre.defaut;
		}
		saveCommencer = fenetre.commencer;
		saveMode = fenetre.mode;
		savelvlPC1 = fenetre.lvlPC1;
		savelvlPC2 = fenetre.lvlPC2;

		JButton accepter = new JButton("Accepter");
		JButton annuler = new JButton("Annuler");

		accepter.addActionListener(new ItemAction_accepter());
		annuler.addActionListener(new ItemAction_annuler());

		panel.add(labelbox1);
		panel.add(box1);
		panel.add(labelbox2);
		panel.add(box2);
		panel.add(labelbox3);
		panel.add(box3);
		panel.add(labelbox4);
		panel.add(box4);
		panel.add(accepter);
		panel.add(annuler);

		fenetre.frame2.add(panel);
		fenetre.frame2.pack();
		fenetre.frame2.setResizable(false);
		fenetre.frame2.setVisible(false);
	}

	class ItemAction_box1 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("ActionListener : action sur " + box1.getSelectedItem());
			System.out.println("numero de l'item: " + box1.getSelectedIndex());
			saveMode = engine.Tools.getTypePartie(box1.getSelectedIndex());
			fenetre.afficherJeu();

		}

	}

	class ItemAction_box2 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("ActionListener : action sur " + box2.getSelectedItem());
			System.out.println("numero de l'item: " + box2.getSelectedIndex());
			savelvlPC1 = engine.Tools.getTypeOfPlayer(box2.getSelectedIndex() + 1);
			fenetre.afficherJeu();

		}

	}

	class ItemAction_box3 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("ActionListener : action sur " + box3.getSelectedItem());
			System.out.println("numero de l'item: " + box3.getSelectedIndex());
			savelvlPC2 = engine.Tools.getTypeOfPlayer(box3.getSelectedIndex() + 1);
			fenetre.afficherJeu();

		}

	}

	class ItemAction_box4 implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			saveCommencer = (box4.getSelectedIndex() == 0);
			fenetre.afficherJeu();
		}

	}

	class ItemAction_accepter implements ActionListener {
		// TODO relancer partie en automatique
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Bouton Accepter");
			fenetre.mode = saveMode;
			fenetre.lvlPC1 = savelvlPC1;
			fenetre.lvlPC2 = savelvlPC2;
			fenetre.commencer = saveCommencer;
			Player humain1 = Tools.createPlayer(fenetre.engine, PlayerType.Humain, fenetre.nameJ1);
			Player pc1 = Tools.createPlayer(fenetre.engine, fenetre.lvlPC1, "Ordi");
			

			if (fenetre.mode == Configuration.HumainVSHumain)
			{
				Player humain2 = Tools.createPlayer(fenetre.engine, PlayerType.Humain, fenetre.nameJ2);
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurBlanc, humain1);
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurNoir, humain2);
			} else if (fenetre.mode == Configuration.HumainVSIA)
			{
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurBlanc, humain1);
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurNoir, pc1);
			} else if (fenetre.mode == Configuration.IAvsIA)
			{
				Player pc2 = Tools.createPlayer(fenetre.engine, fenetre.lvlPC2, "Ordi");
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurBlanc, pc1);
				fenetre.engine.changerLeJoueur(fenetre.engine.getCurrentGame().joueurNoir, pc2);
			} else if (fenetre.mode == Configuration.ANYvsReseau)
			{
				Player networkPlayer = Tools.createPlayer(fenetre.engine, PlayerType.Reseau, "Ordi");
			}
			fenetre.frame2.setVisible(false);
			fenetre.afficherJeu();
		}
	}

	class ItemAction_annuler implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("Bouton Annuler");
			box1.setSelectedIndex(fenetre.mode.ordinal());
			box2.setSelectedIndex(fenetre.lvlPC1.ordinal() - 1);
			box3.setSelectedIndex(fenetre.lvlPC2.ordinal() - 1);
			box4.setSelectedIndex((fenetre.commencer ? 0 : 1));
			fenetre.frame2.setVisible(false);
			fenetre.afficherJeu();
		}
	}
}

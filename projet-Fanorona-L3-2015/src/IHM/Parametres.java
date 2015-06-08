package IHM;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import engine.Configuration;
import engine.PlayerType;
import engine.Tools;

public class Parametres {
	JRadioButton	r1b1, r1b2, r1b3;
	JRadioButton	r2b1, r2b2, r2b3;
	JRadioButton	r3b1, r3b2, r3b3;

	Configuration	saveMode;
	PlayerType		savelvlPC1;
	PlayerType		savelvlPC2;
	boolean			saveCommencer;

	JButton			accepter;
	JButton			annuler;
	JComboBox<String>		box1;
	JComboBox<String>		box2;
	JComboBox<String>		box3;
	JComboBox<String>		box4;

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

		box1 = new JComboBox<String>(tab1);
		box2 = new JComboBox<String>(tab2);
		box3 = new JComboBox<String>(tab3);
		box4 = new JComboBox<String>(tab4);

		box1.addActionListener(new ItemAction_box1());
		box2.addActionListener(new ItemAction_box2());
		box3.addActionListener(new ItemAction_box3());
		box4.addActionListener(new ItemAction_box4());

		// Selection des Boutons
		fenetre.commencer = fenetre.engine.getPremierJoueur();
		fenetre.mode = engine.Tools.getTypePartie(fenetre.engine.getCurrentGame());
		if (fenetre.engine.getJoueurBlanc().aiPlayer || fenetre.engine.getJoueurNoir().aiPlayer)
		{
			if (fenetre.engine.getJoueurBlanc().aiPlayer && fenetre.engine.getJoueurNoir().aiPlayer)
			{
				fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getJoueurBlanc());
				fenetre.lvlPC2 = engine.Tools.getTypeOfPlayer(fenetre.engine.getJoueurNoir());
			} else
			{
				if (fenetre.engine.getJoueurBlanc().aiPlayer)
				{
					fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getJoueurBlanc());
					fenetre.lvlPC2 = fenetre.defaut;
				} else
				{
					fenetre.lvlPC1 = engine.Tools.getTypeOfPlayer(fenetre.engine.getJoueurNoir());
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
			if (box1.getSelectedIndex()==0){
				box2.setEnabled(false);
				box3.setEnabled(false);
			}
			if (box1.getSelectedIndex()==1){
				box2.setEnabled(true);
				box3.setEnabled(false);
			}
			if (box1.getSelectedIndex()==2){
				box2.setEnabled(true);
				box3.setEnabled(true);
			}
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
			PlayerType tmp1 = savelvlPC1;
			PlayerType tmp2 = savelvlPC2;
			
			if(Tools.getTypeOfPlayer(fenetre.engine.getJoueurBlanc()) == PlayerType.Reseau)
				tmp1 = PlayerType.Reseau;
			if(Tools.getTypeOfPlayer(fenetre.engine.getJoueurNoir()) == PlayerType.Reseau)
				tmp2 = PlayerType.Reseau;
			
			Tools.changerDeJoueur(fenetre.engine, fenetre.mode, tmp1, tmp2, fenetre.nameJ1, fenetre.nameJ2);
			
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

package IHM;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class PreferencesOnglets extends JPanel {
	int			width	= 699;
	int			height	= 549;

	int			pBw		= 10;
	int			pBh		= 30;

	ChoixNoms	panel2;

	Fenetre		fenetre;
	// ImagePanel iconSave;
	Image		save;

	public PreferencesOnglets(Fenetre f)
	{
		super(new BorderLayout());

		fenetre = f;

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setPreferredSize(new Dimension(width, height));
		ImageIcon icon = new ImageIcon("src/images/iconFano.jpg");

		ChoixFond panel1 = new ChoixFond(fenetre);
		tabbedPane.addTab(" Choix Fond Ecran ", icon, panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		panel2 = new ChoixNoms(fenetre);
		// ajout fond
		// ImagePanel imagefond = new ImagePanel(new
		// ImageIcon("src/images/players.jpg").getImage(), width, height);
		// panel2.add(imagefond);
		tabbedPane.addTab(" Choix Noms Joueurs ", icon, panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		ChoixCouleur panel3 = new ChoixCouleur();
		tabbedPane.addTab(" Choix Couleurs Pions ", icon, panel3);
		tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

		// creation panel boutons
		JPanel panelBouton = new JPanel(new GridLayout(1, 3));
		// mettre 3 boutons
		JButton valider = new JButton(" VALIDER ");
		valider.addActionListener(new ItemAction_valider());
		JButton annuler = new JButton(" ANNULER ");
		annuler.addActionListener(new ItemAction_annuler());
		JButton reset = new JButton(" REMETTRE PAR DEFAUT ");
		reset.addActionListener(new ItemAction_reset());
		panelBouton.add(valider);
		panelBouton.add(annuler);
		panelBouton.add(reset);
		panelBouton.setPreferredSize(new Dimension(pBw, pBh));

		// Ajout
		add(tabbedPane, BorderLayout.PAGE_START);
		add(panelBouton, BorderLayout.PAGE_END);

		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	public void majPref()
	{
		// Ajouts
		fenetre.frame3.add(this, BorderLayout.CENTER);
		fenetre.frame3.pack();
		fenetre.frame3.setVisible(false);
	}

	class ItemAction_valider implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.nameJ1 = panel2.name1.getText();
			fenetre.nameJ2 = panel2.name2.getText();
			fenetre.frame3.setVisible(false);
			fenetre.frame.repaint();
			System.out.println("valider");
		}
	}

	// Ne Marche pas
	class ItemAction_annuler implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			// System.out.println("Image panel: "+fenetre.panelAccueil.getImage().toString()+"    "+iconSave.getImage().toString());
			fenetre.panelAccueil = new ImagePanel(save, fenetre.fw, fenetre.fh);
			// panelAccueil et iconSave sont deja egale ...
			fenetre.frame.repaint();
			fenetre.frame3.setVisible(false);
		}
	}

	class ItemAction_reset implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			fenetre.panelAccueil = new ImagePanel(new ImageIcon("src/images/iconimageDefault.jpg").getImage(), fenetre.fw, fenetre.fh);
			fenetre.frame.repaint();
			fenetre.frame3.setVisible(false);
		}

	}

//	public static void main(String[] args)
//	{
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run()
//			{
//				createAndShowGUI();
//			}
//		});
//	}

}

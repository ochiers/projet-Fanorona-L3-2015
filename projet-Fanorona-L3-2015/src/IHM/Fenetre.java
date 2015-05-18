package IHM;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import IHM.EcouteurDeBouton;

public class Fenetre implements Runnable,Affichage {
	JFrame frame = new JFrame(" -- Fanorona -- ");
	AireDeDessin monDessin;
	
	public void run(){
		frame.setSize(500, 500);  
		JPanel panelAccueil = new JPanel();
		panelAccueil.setLayout(null);
		/* setBounds(int x, int y, int width, int height)
		Moves and resizes this component. */
		
		//grille
		monDessin = new AireDeDessin(this);
		
 		//barre de Menu
		JMenuBar menuBar = new JMenuBar();
		//menu1
		JMenu partie = new JMenu(" Partie ");
		JMenuItem mi1 = new JMenuItem(" Nouvelle Partie ");
		//mi1.addActionListener(new EcouteurDeBouton(this,mi1.getText()));
		JMenuItem mi2 = new JMenuItem(" Recommencer ");	
		//mi2.addActionListener(new EcouteurDeBouton(this,mi2.getText()));
		JMenuItem mi3 = new JMenuItem(" Sauvegarder ");
		//mi3.addActionListener(new EcouteurDeBouton(this,mi3.getText()));
		JMenuItem mi4 = new JMenuItem(" Charger ");
		//mi4.addActionListener(new EcouteurDeBouton(this,mi4.getText()));
		JMenuItem mi5 = new JMenuItem(" Quitter ");
		mi5.addActionListener(new EcouteurDeBouton(this,mi5.getText()));
		//ajout au menu1
		partie.add(mi1);
		partie.add(mi2);
		partie.add(mi3);
		partie.add(mi4);
		partie.add(mi5);
		//menu2
		JMenu options = new JMenu(" Options ");
		JMenuItem mi1 = new JMenuItem(" Paramètres Partie ");
		//mi1.addActionListener(new EcouteurDeBouton(this,mi1.getText()));
		JMenuItem mi2 = new JMenuItem(" Préférences ");	
		//mi2.addActionListener(new EcouteurDeBouton(this,mi2.getText()));
		JMenuItem mi3 = new JMenuItem(" Historique Scores ");
		//mi3.addActionListener(new EcouteurDeBouton(this,mi3.getText()));
		//ajout au menu2
		options.add(mi1);
		options.add(mi2);
		options.add(mi3);
		//menu3
		JMenu aide = new JMenu(" Aide ");
		JMenuItem mi1 = new JMenuItem(" Règles du Jeu ");
		//mi1.addActionListener(new EcouteurDeBouton(this,mi1.getText()));
		JMenuItem mi2 = new JMenuItem(" A Propos ");	
		//mi2.addActionListener(new EcouteurDeBouton(this,mi2.getText()));
		//ajout au menu3
		aide.add(mi1);
		aide.add(mi2);		
 		
		//boutons commandes
		JButton annuler = new JButton(" Annuler Coup ");
		annuler.addActionListener(new EcouteurDeBouton();
		JButton refaire = new JButton(" Refaire Coup ");
		refaire.addActionListener(new EcouteurDeBouton();
		JButton stopper = new JButton(" Pause ");
		stopper.addActionListener(new EcouteurDeBouton();
		
		//affichages joueurs
		/*
		 noms
		 SCORE
		 score dans carré
		 ordi+niv ou humain
		 */
		
 		//ajouts 
 		menuBar.add(partie);
		menuBar.add(options);
		menuBar.add(aide);
		frame.setJMenuBar(menuBar);
 		panelAccueil.add(monDessin);
 		panelAccueil.add(annuler);
 		panelAccueil.add(refaire);
 		panelAccueil.add(stopper);
 		
 		frame.add(panelAccueil);  
 		/* f ou p ??   .setBackground(Color.GREEN);
		setResizable(boolean resizable)
		Sets whether this frame is resizable by the user. */
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void afficherJeu(){
		
	}
	public void afficherVictoire(){
		
	}
	
	public static void main(String args[]) throws InterruptedException{
		Fenetre f=new Fenetre();
		SwingUtilities.invokeLater(f);
	}
	
}

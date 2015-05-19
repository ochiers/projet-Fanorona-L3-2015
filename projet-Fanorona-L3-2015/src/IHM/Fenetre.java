package IHM;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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
		frame.setSize(800, 600);  
		JPanel panelAccueil = new JPanel();
		//panelAccueil.setLayout(null);
		/* setBounds(int x, int y, int width, int height)
		Moves and resizes this component. */
		
			//grille
		monDessin = new AireDeDessin(this);
			monDessin.addMouseListener(new EcouteurDeSouris(monDessin));
		
 			//barre de Menu
		JMenuBar menuBar = new JMenuBar();
			//menu1
		JMenu partie = new JMenu(" Partie ");
		JMenuItem mi1b1 = new JMenuItem(" Nouvelle Partie ");
			mi1b1.addActionListener(new EcouteurDeBouton(this,mi1b1.getText()));
		JMenuItem mi1b2 = new JMenuItem(" Recommencer ");	
			mi1b2.addActionListener(new EcouteurDeBouton(this,mi1b2.getText()));
		JMenuItem mi1b3 = new JMenuItem(" Sauvegarder ");
			mi1b3.addActionListener(new EcouteurDeBouton(this,mi1b3.getText()));
		JMenuItem mi1b4 = new JMenuItem(" Charger ");
			mi1b4.addActionListener(new EcouteurDeBouton(this,mi1b4.getText()));
		JMenuItem mi1b5 = new JMenuItem(" Quitter ");
			mi1b5.addActionListener(new EcouteurDeBouton(this,mi1b5.getText()));
			//ajout au menu1
		partie.add(mi1b1);
		partie.add(mi1b2);
		partie.add(mi1b3);
		partie.add(mi1b4);
		partie.add(mi1b5);
			//menu2
		JMenu options = new JMenu(" Options ");
		JMenuItem mi2b1 = new JMenuItem(" Paramètres Partie ");
			mi2b1.addActionListener(new EcouteurDeBouton(this,mi2b1.getText()));
		JMenuItem mi2b2 = new JMenuItem(" Préférences ");	
			mi2b2.addActionListener(new EcouteurDeBouton(this,mi2b2.getText()));
		JMenuItem mi2b3 = new JMenuItem(" Historique Scores ");
			mi2b3.addActionListener(new EcouteurDeBouton(this,mi2b3.getText()));
			//ajout au menu2
		options.add(mi2b1);
		options.add(mi2b2);
		options.add(mi2b3);
			//menu3
		JMenu aide = new JMenu(" Aide ");
		JMenuItem mi3b1 = new JMenuItem(" Règles du Jeu ");
			mi3b1.addActionListener(new EcouteurDeBouton(this,mi3b1.getText()));
		JMenuItem mi3b2 = new JMenuItem(" A Propos ");	
			mi3b2.addActionListener(new EcouteurDeBouton(this,mi3b2.getText()));
			//ajout au menu3
		aide.add(mi3b1);
		aide.add(mi3b2);		
 		
			//boutons commandes
		JButton annuler = new JButton(" Annuler Coup ");
			annuler.addActionListener(new EcouteurDeBouton(this,annuler.getText()));
		JButton refaire = new JButton(" Refaire Coup ");
			refaire.addActionListener(new EcouteurDeBouton(this,refaire.getText()));
		JButton stopper = new JButton(" Pause ");
			stopper.addActionListener(new EcouteurDeBouton(this,stopper.getText()));
		JButton valider = new JButton(" Fin du tour ");
			valider.addActionListener(new EcouteurDeBouton(this,valider  .getText()));
			
			//affichages joueurs
		/*
		 nom J1 ou J2
		 ordi+niv ou humain
		 Score dans carré
		 Pions restants
		 A voutre tour de jouer
		 */
			//lettrages
		JLabel j1 = new JLabel(" # Joueur 1 ");
		JLabel j2 = new JLabel(" # Joueur 2 ");
		JLabel idj1 = new JLabel(" # Joueur 1 ");
		JLabel idj2 = new JLabel(" # Joueur 1 ");
		JLabel j1 = new JLabel(" # Joueur 1 ");
			//carré score
		
		
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

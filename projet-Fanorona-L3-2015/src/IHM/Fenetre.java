package IHM;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
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
		
		//grille
		monDessin = new AireDeDessin(this);
 		panelAccueil.add(monDessin);
		
 		//
 		
 		
 		//barre de Menu
		JMenuBar menuBar = new JMenuBar();
		//menu1
		JMenu partie = new JMenu(" Partie ");
		JMenuItem mi1 = new JMenuItem(" Nouvelle Partie ");
		mi1.addActionListener(new EcouteurDeBouton(this,mi1.getText()));
		JMenuItem mi2 = new JMenuItem(" Recommencer ");	
		mi2.addActionListener(new EcouteurDeBouton(this,mi2.getText()));
		JMenuItem mi3 = new JMenuItem(" Sauvegarder ");
		mi3.addActionListener(new EcouteurDeBouton(this,mi3.getText()));
		JMenuItem mi4 = new JMenuItem(" Charger ");
		mi4.addActionListener(new EcouteurDeBouton(this,mi4.getText()));
		JMenuItem mi5 = new JMenuItem(" Quitter ");
		mi5.addActionListener(new EcouteurDeBouton(this,mi5.getText()));
		//ajout au menu1
		partie.add(mi1);
		partie.add(mi2);
		partie.add(mi3);
		partie.add(mi4);
		partie.add(mi5);
		
		JMenu menu2 = new JMenu("Edition");
		mi5=new JMenuItem("Annuler");
		mi5.addActionListener(new EcouteurDeBouton(this,mi5.getText()));
		mi5.setEnabled(false);
		mi6=new JMenuItem("Retablir");
		mi6.addActionListener(new EcouteurDeBouton(this,mi6.getText()));
		mi6.setEnabled(false);
		menu2.add(mi5);
		menu2.add(mi6);
		
		JMenu menu3 = new JMenu("Option");
		JRadioButtonMenuItem jrb1 = new JRadioButtonMenuItem("J1 vs J2");
		jrb1.addActionListener(new EcouteurDeBouton(this,jrb1.getText()));
		JRadioButtonMenuItem jrb2 = new JRadioButtonMenuItem("J1 vs PC1");
		jrb2.addActionListener(new EcouteurDeBouton(this,jrb2.getText()));
		JRadioButtonMenuItem jrb3 = new JRadioButtonMenuItem("PC1 vs PC2");
		jrb3.addActionListener(new EcouteurDeBouton(this,jrb3.getText()));
		if(e.partieCourante.J1.aiPlayer && e.partieCourante.J2.aiPlayer)
			jrb3.setSelected(true);
		else if(!e.partieCourante.J1.aiPlayer && !e.partieCourante.J2.aiPlayer)
			jrb1.setSelected(true);
		else 
			jrb2.setSelected(true);
		ButtonGroup bg = new ButtonGroup();
		bg.add(jrb1);
		bg.add(jrb2);
		bg.add(jrb3);
		menu3.add(jrb1);
		menu3.add(jrb2);
		menu3.add(jrb3);
 		
 		//ajouts
 		frame.add(panelAccueil);  
 		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		
 		//frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public void afficherJeu(){
		
	}
	public void afficherVictoire(){
		
	}
	
	public static void main(String argv[]) throws InterruptedException{
		Fenetre f=new Fenetre();
		SwingUtilities.invokeLater(f);
	}
	
}

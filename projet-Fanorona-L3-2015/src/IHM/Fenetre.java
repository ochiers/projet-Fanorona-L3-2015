package IHM;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Fenetre implements Runnable,Affichage {
	JFrame frame = new JFrame(" Fanorona ");
	AireDeDessin monDessin;
	
	public void run(){
		frame.setSize(800, 600);  
		JPanel panelAccueil = new JPanel();
		//Ajout grille
		monDessin = new AireDeDessin(this);
 		panelAccueil.add(monDessin);
		
 		frame.add(panelAccueil);  
 		//frame.pack();
 		//frame.addComponentListener(new Gestionaire(frame));
 		frame.setResizable(false);
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

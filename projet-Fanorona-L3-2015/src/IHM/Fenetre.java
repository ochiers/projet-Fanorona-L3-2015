package IHM;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import engine.*;
import IHM.*;

public class Fenetre implements Runnable,Affichage {
	static JFrame frame = new JFrame(" -- Fanorona -- ");
	AireDeDessin monDessin;
	Engine engine;
	
	public Fenetre(Engine e){
		engine=e;
	}
	
	public void run(){		

		frame.setSize(1280, 720);
		int fw = frame.getWidth();
		int fh = frame.getHeight();
		JPanel panelAccueil = new ImagePanel(new ImageIcon("src/images/image6.jpg").getImage(), fw, fh);
		panelAccueil.setLayout(null);
		
			//grille
		monDessin = new AireDeDessin(this);
			monDessin.addMouseListener(new EcouteurDeSouris(monDessin));
			monDessin.setBounds(fw/6, (int)(0.3*fh/6), 4*fw/6, 4*fh/6);
	/*	JPanel panelPlateau = new ImagePanel(new ImageIcon("src/images/plateau9x5.jpg").getImage(), 4*fw/6, 4*fh/6);
			panelPlateau.setBounds(fw/6, (int)(0.3*fh/6), 4*fw/6, 4*fh/6);
		JPanel panelGrille = new ImagePanel(new ImageIcon("src/images/Fanorona.png").getImage(), (int)(3.4*fw/6), (int)(2.8*fh/6));
			panelGrille.setBounds((int)(1.3*fw/6), (int)(0.9*fh/6), (int)(3.4*fw/6), (int)(2.8*fh/6));
	*/		
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
		int temp = (int)(0.8*fh/6)/2;
		JButton annuler = new JButton(" Annuler Coup ");
			annuler.addActionListener(new EcouteurDeBouton(this,annuler.getText()));
			annuler.setBounds((int)((1.5*fw/6)-temp), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
		JButton refaire = new JButton(" Refaire Coup ");
			refaire.addActionListener(new EcouteurDeBouton(this,refaire.getText()));
			refaire.setBounds((int)(((1.5*fw/6)-temp)+fw/6), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
		JButton stopper = new JButton(" Pause ");
			stopper.addActionListener(new EcouteurDeBouton(this,stopper.getText()));
			stopper.setBounds((int)(((1.5*fw/6)-temp)+2*fw/6), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
		JButton valider = new JButton(" Fin du tour ");
			valider.addActionListener(new EcouteurDeBouton(this,valider.getText()));
			valider.setBounds((int)(((1.5*fw/6)-temp)+3*fw/6), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
			
			//affichages joueurs
		JLabel j1 = new JLabel(" # Joueur 1 ");
			j1.setBounds((int)(0.25*fw/6), (int)(0.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel idj1 = new JLabel(" Erreur ");
		JLabel levelj1 = new JLabel(" Erreur ");
		JLabel j2 = new JLabel(" # Joueur 2 ");
			j2.setBounds((int)(5.15*fw/6), (int)(0.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel idj2 = new JLabel(" Erreur ");
		JLabel levelj2 = new JLabel(" Erreur ");
		String level = engine.partieCourante.joueurBlanc.getNiveau();
		if (level.equals("Humain")){
			idj1 = new JLabel(engine.partieCourante.joueurBlanc.name);
			levelj1 = new JLabel(" Bonne Chance ! ");
		}
		if (level.equals("IA Facile")){
			idj1 = new JLabel(" Ordinateur ");
			levelj1 = new JLabel(" Facile ");
		}
		if (level.equals("IA Moyenne")){
			idj1 = new JLabel(" Ordinateur ");
			levelj1 = new JLabel(" Moyen ");
		}
		if (level.equals("IA Difficle")){
			idj1 = new JLabel(" Ordinateur ");
			levelj1 = new JLabel(" Difficile ");
		}
		if (level.equals("Humain")){
			idj2 = new JLabel(engine.partieCourante.joueurNoir.name);
			levelj2 = new JLabel(" Bonne Chance ! ");
		}
		if (level.equals("IA Facile")){
			idj2 = new JLabel(" Ordinateur ");
			levelj2 = new JLabel(" Facile ");
		}
		if (level.equals("IA Moyenne")){
			idj2 = new JLabel(" Ordinateur ");
			levelj2 = new JLabel(" Moyen ");
		}
		if (level.equals("IA Difficle")){
			idj2 = new JLabel(" Ordinateur ");
			levelj2 = new JLabel(" Difficile ");
		}
			idj1.setBounds((int)(0.25*fw/6), (int)(1.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			levelj1.setBounds((int)(0.25*fw/6), (int)(1.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			idj2.setBounds((int)(5.15*fw/6), (int)(1.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			levelj2.setBounds((int)(5.15*fw/6), (int)(1.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel scoreInt1 = new JLabel("" + engine.partieCourante.nombrePionBlanc);
			scoreInt1.setBounds((int)(0.25*fw/6), (int)(2.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel scoreInt2 = new JLabel("" + engine.partieCourante.nombrePionNoir);
			scoreInt2.setBounds((int)(5.15*fw/6), (int)(2.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel score1 = new JLabel(" Pions restants ");
			score1.setBounds((int)(0.25*fw/6), (int)(2.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel score2 = new JLabel(" Pions restants ");
			score2.setBounds((int)(5.15*fw/6), (int)(2.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel tour1 = new JLabel(" A votre tour ! ");
			tour1.setBounds((int)(0.25*fw/6), (int)(3*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel tour2 = new JLabel(" A votre tour ! ");
			tour2.setBounds((int)(5.15*fw/6), (int)(3*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		
 			//ajouts 
 		menuBar.add(partie);
		menuBar.add(options);
		menuBar.add(aide);
		frame.setJMenuBar(menuBar);
 		//panelPlateau.add(panelGrille);
 		panelAccueil.add(monDessin);
 		panelAccueil.add(annuler);
 		panelAccueil.add(refaire);
 		panelAccueil.add(stopper);
 		panelAccueil.add(valider);
 		panelAccueil.add(j1);
 		panelAccueil.add(j2); 		 
 		panelAccueil.add(idj1);
 		panelAccueil.add(idj2);
 		panelAccueil.add(levelj1);
 		panelAccueil.add(levelj2);
 		panelAccueil.add(scoreInt1);
 		panelAccueil.add(scoreInt2);
 		panelAccueil.add(score1); 
 		panelAccueil.add(score2); 		
 		panelAccueil.add(tour1);
 		panelAccueil.add(tour2);
 		//panelAccueil.add(panelPlateau);
 		//panelAccueil.add(panelGrille);
 		//panelPlateau.add(panelGrille);
 		
 		frame.add(panelAccueil);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public void afficherJeu(){
		monDessin.repaint();
	}

	public void afficherPionsPossibles(ArrayList<Case> l) {
		// TODO Auto-generated method stub
	}

	public void afficherVictoire(Player p) {
		// TODO Auto-generated method stub	
	}
	
}

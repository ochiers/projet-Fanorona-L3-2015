package IHM;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import engine.*;

public class Fenetre implements Runnable,Affichage {
	static JFrame frame = new JFrame(" -- Fanorona -- ");
	static JFrame frame2 = new JFrame(" -- Parametres -- ");
	static JFrame frame3 = new JFrame(" -- Preferences -- ");
	Parametres parametre;
	Preferences preference;
	AireDeDessin monDessin;
	EngineServices engine;
	
	int lvlPC1;
	int lvlPC2;
	int mode;
	
/*	PlayerType lvlPC1;
	PlayerType lvlPC2;
	Configuration mode;
*/	
	JLabel scoreInt1;
	JLabel scoreInt2;
	JButton annuler;
	JButton refaire;
	JButton stopper;
	JButton valider;
	JLabel tour1;
	JLabel tour2;
	ImagePanel panelAccueil;
	int fw, fh;
	JLabel idj1;
	JLabel levelj1;
	JLabel idj2;
	JLabel levelj2;
	Color pion1 = Color.black;
	Color pion2 = Color.white;
	String nameJ1;
	String nameJ2;
	
	public Fenetre(EngineServices e){
		engine=e;
		
	}
	
	public void run(){		

		frame.setSize(1280, 720);
		fw = frame.getWidth();
		fh = frame.getHeight();
		panelAccueil = new ImagePanel(new ImageIcon("src/images/imageDefault.jpg").getImage(), fw, fh);
		panelAccueil.setLayout(null);
		
			//grille
		monDessin = new AireDeDessin(this);
			monDessin.addMouseListener(new EcouteurDeSouris(monDessin));
			//monDessin.setBounds(fw/6, (int)(0.3*fh/6), 4*fw/6, 4*fh/6);
			//monDessin.setBounds((int)(0.8*fw/6), (int)(0.2*fh/6), (int)(4.4*fw/6), (int)(4.4*fh/6)); //MODIF TAILLE POUR FANO
			monDessin.setBounds(0, 0, fw, 5*fh/6); // RE MODIF TAILLE POUR FANO
			
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
		JMenuItem mi2b1 = new JMenuItem(" Parametres Partie ");
			mi2b1.addActionListener(new EcouteurDeBouton(this,mi2b1.getText()));
		JMenuItem mi2b2 = new JMenuItem(" Preferences ");	
			mi2b2.addActionListener(new EcouteurDeBouton(this,mi2b2.getText()));
		JMenuItem mi2b3 = new JMenuItem(" Historique Scores ");
			mi2b3.addActionListener(new EcouteurDeBouton(this,mi2b3.getText()));
			//ajout au menu2
		options.add(mi2b1);
		options.add(mi2b2);
		options.add(mi2b3);
			//menu3
		JMenu aide = new JMenu(" Aide ");
		JMenuItem mi3b1 = new JMenuItem(" Regles du Jeu ");
			mi3b1.addActionListener(new EcouteurDeBouton(this,mi3b1.getText()));
		JMenuItem mi3b2 = new JMenuItem(" A Propos ");	
			mi3b2.addActionListener(new EcouteurDeBouton(this,mi3b2.getText()));
			//ajout au menu3
		aide.add(mi3b1);
		aide.add(mi3b2);		
 		
			//boutons commandes
		//TODO VERIF DIMENSIONS 
		int temp = (int)(0.6*fh/6)/2;
		annuler = new JButton(" Annuler Coup ");
			annuler.addActionListener(new EcouteurDeBouton(this,annuler.getText()));
			annuler.setBounds((int)((1.2*fw/6)-temp), (int)(5.1*fh/6), (int)(1.2*fh/6), (int)(0.3*fh/6));
		refaire = new JButton(" Refaire Coup ");
			refaire.addActionListener(new EcouteurDeBouton(this,refaire.getText()));
			refaire.setBounds((int)(((1.2*fw/6)-temp)+(0.8*fw/6)), (int)(5.1*fh/6), (int)(1.2*fh/6), (int)(0.3*fh/6));
		stopper = new JButton(" Pause ");
			stopper.addActionListener(new EcouteurDeBouton(this,stopper.getText()));
			
			//stopper.setBounds((int)(((1.5*fw/6)-temp)+2*fw/6), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
			stopper.setBounds((int)(((1.2*fw/6)-temp)+(1.6*fw/6)), (int)(5.1*fh/6), (int)(1.2*fh/6), (int)(0.3*fh/6));
		valider = new JButton(" Fin du tour ");
			valider.addActionListener(new EcouteurDeBouton(this,valider.getText()));
			valider.setBounds((int)(((1.2*fw/6)-temp)+(2.4*fw/6)), (int)(5.1*fh/6), (int)(1.2*fh/6), (int)(0.3*fh/6));
		JButton suggestion = new JButton(" Suggerer coup ");
			suggestion.addActionListener(new EcouteurDeBouton(this,suggestion.getText()));
			suggestion.setBounds((int)(((1.2*fw/6)-temp)+(3.2*fw/6)), (int)(5.1*fh/6), (int)(1.2*fh/6), (int)(0.3*fh/6));
			
			//affichages joueurs
		JLabel j1 = new JLabel(" # Joueur 1 ");
			j1.setBounds((int)(0.2*fw/6), (int)(0.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		idj1 = new JLabel(" Erreur ");
		levelj1 = new JLabel(" Erreur ");
		JLabel j2 = new JLabel(" # Joueur 2 ");
			j2.setBounds((int)(5.4*fw/6), (int)(0.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		idj2 = new JLabel(" Erreur ");
		levelj2 = new JLabel(" Erreur ");
		
			idj1.setBounds((int)(0.2*fw/6), (int)(1.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			levelj1.setBounds((int)(0.2*fw/6), (int)(1.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			idj2.setBounds((int)(5.4*fw/6), (int)(1.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			levelj2.setBounds((int)(5.4*fw/6), (int)(1.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
			
		scoreInt1 = new JLabel("");
			scoreInt1.setBounds((int)(0.35*fw/6), (int)(2.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		scoreInt2 = new JLabel("");
			scoreInt2.setBounds((int)(5.55*fw/6), (int)(2.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel score1 = new JLabel(" Pions restants ");
			score1.setBounds((int)(0.2*fw/6), (int)(2.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		JLabel score2 = new JLabel(" Pions restants ");
			score2.setBounds((int)(5.4*fw/6), (int)(2.4*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		tour1 = new JLabel(" A votre tour ! ");
			tour1.setBounds((int)(0.2*fw/6), (int)(3*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		tour2 = new JLabel(" A votre tour ! ");
			tour2.setBounds((int)(5.4*fw/6), (int)(3*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		
 			//ajouts 
 		menuBar.add(partie);
		menuBar.add(options);
		menuBar.add(aide);
		frame.setJMenuBar(menuBar);
 		panelAccueil.add(monDessin);
 		panelAccueil.add(annuler);
 		panelAccueil.add(refaire);
 		panelAccueil.add(stopper);
 		panelAccueil.add(valider);
 		panelAccueil.add(suggestion);
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

		nameJ1 = engine.getJoueurBlanc().name;
		nameJ2 = engine.getJoueurNoir().name;
		
		parametre = new Parametres(this);
		parametre.majParam();
		//parametre.majParam2();
		//frame.repaint();
		preference = new Preferences(this);
		preference.majPref();
		//frame.repaint();
		
 		frame.add(panelAccueil);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void afficherJeu(){
		//System.out.println("11111111");
		monDessin.repaint();
	}

	public void afficherPionsPossibles(ArrayList<Case> l) {
		//System.out.println("22222222");

		monDessin.pionPossible=l;
		monDessin.repaint();
	}

	public void afficherVictoire(Player p) {
		monDessin.finPartie=true;
		System.out.println("VICTOIRE");
		monDessin.repaint();
	}
	
	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2){
		//if(!engine.partieCourante.joueurCourant.aiPlayer){
		if(!engine.getCurrentGame().joueurCourant.aiPlayer){
			//System.out.println("------------MULTI DIRECTION------------------");
			monDessin.doitChoisir=true;
			monDessin.l1=l1;
			monDessin.l2=l2;
		}
		monDessin.repaint();
	}

	@Override
	public void afficherPionDuCombo(Case pionCourant) {
		//System.out.println("33333333333333");
		monDessin.pionCombo=pionCourant;
		//System.out.println("le pion combo est :"+pionCourant.position.ligne+" "+pionCourant.position.colonne);
		monDessin.repaint();
		//monDessin.estEnCombo=false;
		
	}

	@Override
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo){
		//System.out.println("COOOOOOOOOOOOOOOOMBO");
		monDessin.combo=combo;
		monDessin.repaint();
		
	}

	@Override
	public void sauvegardeReussie(boolean reussi)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chargementReussi(boolean reussi)
	{
		// TODO Auto-generated method stub
		
	}
	
}

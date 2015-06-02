package IHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import engine.*;

public class Fenetre implements Runnable,Affichage {
	JFrame frame = new JFrame(" -- Fanorona -- ");
	JFrame frame2 = new JFrame(" -- Parametres -- ");
	JFrame frame3 = new JFrame(" -- Preferences -- ");
	Parametres parametre;
	PreferencesOnglets preference;
	AireDeDessin monDessin;
	EngineServices engine;
	Dimension size = new Dimension(9,5);
	
/*	int lvlPC1;
	int lvlPC2;
	int mode;
*/	
	PlayerType lvlPC1;
	PlayerType lvlPC2;
	Configuration mode;
	boolean commencer;
	PlayerType defaut=PlayerType.IAMoyenne;
	
	JLabel scoreInt1;
	JLabel scoreInt2;
	
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
	
	JMenuBar menuBar;
	JMenu partie;
	JMenu options;
	JMenu aide;
	
	JMenuItem partie_nouvellePartie;
	JMenuItem partie_recommencer;
	JMenuItem partie_sauvegarder;
	JMenuItem partie_charger;
	JMenuItem partie_quitter;
	
	JMenuItem options_parametresPartie;
	JMenuItem options_preferences;
	JMenuItem options_historiqueScores;
	
	JMenuItem aide_reglesDuJeu;
	JMenuItem aide_aPropos;
	
	JButton annuler;
	JButton refaire;
	JButton stopper;
	JButton finTour;
	JButton suggestion;
	
	int wmin = 673;
	int hmin = 405;
	int wmax = 1280;
	int hmax = 720;
	
	
	public Fenetre(EngineServices e){
		engine=e;
	}
	
	public void run(){		
		System.out.println("//////////////////////////////////////////////////////");
		frame.setSize(842, 507);
		//frame.setSize(wmax,hmax);
		frame.setMinimumSize(new Dimension(wmin, hmin));
		frame.setMaximumSize(new Dimension(wmax, hmax));
		fw = frame.getWidth();
		fh = frame.getHeight();
		panelAccueil = new ImagePanel(new ImageIcon("src/images/imageDefault.jpg").getImage(), fw, fh);
		panelAccueil.setLayout(new BorderLayout( 20, 10));
		
			//grille
		monDessin = new AireDeDessin(this);
		monDessin.addMouseListener(new EcouteurDeSouris(monDessin));

			//barre de Menu
		menuBar = new JMenuBar();
			//menu1
		partie = new JMenu(" Partie ");
		partie_nouvellePartie = new JMenuItem(" Nouvelle Partie ");
		partie_recommencer = new JMenuItem(" Recommencer ");	
		partie_sauvegarder = new JMenuItem(" Sauvegarder ");
		partie_charger = new JMenuItem(" Charger ");
		partie_quitter = new JMenuItem(" Quitter ");
		
		partie_nouvellePartie.addActionListener(new ItemAction_partie_nouvellePartie());	
		partie_recommencer.addActionListener(new ItemAction_partie_recommencer());
		partie_sauvegarder.addActionListener(new ItemAction_partie_sauvegarder());
		partie_charger.addActionListener(new ItemAction_partie_charger());
		partie_quitter.addActionListener(new ItemAction_partie_quitter());
		
		partie.add(partie_nouvellePartie);
		partie.add(partie_recommencer);
		partie.add(partie_sauvegarder);
		partie.add(partie_charger);
		partie.add(partie_quitter);
			
			//menu2
		options = new JMenu(" Options ");
		options_parametresPartie = new JMenuItem(" Parametres Partie ");
		options_preferences = new JMenuItem(" Preferences ");	
		options_historiqueScores = new JMenuItem(" Historique Scores ");
		
		options_parametresPartie.addActionListener(new ItemAction_options_parametresPartie());
		options_preferences.addActionListener(new ItemAction_options_preferences());
		options_historiqueScores.addActionListener(new ItemAction_options_historiqueScores());

		options.add(options_parametresPartie);
		options.add(options_preferences);
		options.add(options_historiqueScores);
		
			//menu3
		aide = new JMenu(" Aide ");
		aide_reglesDuJeu = new JMenuItem(" Regles du Jeu ");
		aide_aPropos = new JMenuItem(" A Propos ");
		
		aide_reglesDuJeu.addActionListener(new ItemAction_aide_reglesDuJeu());
		aide_aPropos.addActionListener(new ItemAction_aide_aPropos());
		
		aide.add(aide_reglesDuJeu);
		aide.add(aide_aPropos);	

			//ajouts dans barre de menu
		menuBar.add(partie);
		menuBar.add(options);
		menuBar.add(aide);
 		
			//boutons commandes
		annuler = new JButton(" Annuler Coup ");
		refaire = new JButton(" Refaire Coup ");
		stopper = new JButton(" Pause ");
		finTour = new JButton(" Fin du tour ");
		suggestion = new JButton(" Suggerer coup ");
		
		annuler.addActionListener(new ItemAction_annuler());
		refaire.addActionListener(new ItemAction_refaire());
		stopper.addActionListener(new ItemAction_stopper());
		finTour.addActionListener(new ItemAction_finTour());
		suggestion.addActionListener(new ItemAction_suggestion());
 		
			//boutons
		JPanel panelSud = new JPanel(new FlowLayout());
		panelSud.setOpaque(false);
		panelSud.add(annuler);
		panelSud.add(refaire);
		panelSud.add(stopper);
		panelSud.add(finTour);
		panelSud.add(suggestion);
		
			//affichages joueurs
		JLabel j1 = new JLabel(" # Joueur 1 ");
		idj1 = new JLabel(" Erreur ");
		levelj1 = new JLabel(" Erreur ");
		JLabel j2 = new JLabel(" # Joueur 2 ");
		idj2 = new JLabel(" Erreur ");
		levelj2 = new JLabel(" Erreur ");
		scoreInt1 = new JLabel("");
		scoreInt2 = new JLabel("");	
		JLabel score1 = new JLabel(" Pions restants ");
		JLabel score2 = new JLabel(" Pions restants ");
		tour1 = new JLabel(" A votre tour ! ");
		tour2 = new JLabel(" A votre tour ! ");
 		
			//joueur 1
		JPanel panelOuest = new JPanel();
		panelOuest.setLayout(new BoxLayout(panelOuest, 1));
		panelOuest.setOpaque(false);
		panelOuest.add(j1);
		panelOuest.add(idj1);
		panelOuest.add(levelj1);
		panelOuest.add(scoreInt1);
		panelOuest.add(score1);
		panelOuest.add(tour1);
 		
			//joueur 2
 		JPanel panelEst = new JPanel();
 		panelEst.setLayout(new BoxLayout(panelEst, 1));
 		panelEst.setOpaque(false);
 		panelEst.add(j2); 		 
 		panelEst.add(idj2);
 		panelEst.add(levelj2);
 		panelEst.add(scoreInt2);
 		panelEst.add(score2); 		
 		panelEst.add(tour2);
 		
 			//ajout au panel accueil
 		panelAccueil.add(monDessin, BorderLayout.CENTER);
 		panelAccueil.add(panelEst, BorderLayout.EAST);
 		panelAccueil.add(panelOuest, BorderLayout.WEST);
 		panelAccueil.add(panelSud, BorderLayout.SOUTH);
 		
 			//recuperation joueurs
		nameJ1 = engine.getJoueurBlanc().name;
		nameJ2 = engine.getJoueurNoir().name;
		
			// ajout fenetres details
		parametre = new Parametres(this);
		parametre.majParam();
		preference = new PreferencesOnglets(this);
		preference.majPref();

			// FENETRE
		frame.setJMenuBar(menuBar);
 		frame.add(panelAccueil);
		//frame.setResizable(false);
		frame.setVisible(true);
		frame.addWindowListener(new EcouteurDeFenetre(engine));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// ACTIONLISTENER
	
	class ItemAction_partie_nouvellePartie implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Player humain1= Tools.createPlayer(engine, PlayerType.Humain, nameJ1);
			Player humain2= Tools.createPlayer(engine, PlayerType.Humain, nameJ2);
			Player pc1= Tools.createPlayer(engine, lvlPC1, "Ordi");
			Player pc2= Tools.createPlayer(engine, lvlPC2, "Ordi");
			
			if(mode.ordinal()==0)		engine.nouvellePartie(humain1, humain2, (commencer?0:1), size);
			else if(mode.ordinal()==1)	engine.nouvellePartie(humain1, pc1, (commencer?0:1), size);
			else if(mode.ordinal()==2)	engine.nouvellePartie(pc1, pc2, (commencer?0:1), size);
			monDessin.finPartie=false;
	    }               

	}
	
	class ItemAction_partie_recommencer implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Player p1=Tools.createPlayer(engine, Tools.getTypeOfPlayer((engine.getCurrentGame().joueurBlanc)), engine.getCurrentGame().joueurBlanc.name);
			Player p2=Tools.createPlayer(engine, Tools.getTypeOfPlayer((engine.getCurrentGame().joueurNoir)), engine.getCurrentGame().joueurNoir.name);
			engine.nouvellePartie(p1,p2,(commencer?0:1), size);
			monDessin.finPartie=false;
	    }               

	}
	
	class ItemAction_partie_sauvegarder implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(frame);
			engine.sauvegarderPartie(save.getSelectedFile().getAbsolutePath());
	    }               

	}
	
	class ItemAction_partie_charger implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			try{
			JFileChooser load = new JFileChooser();
			load.showOpenDialog(frame);
			engine.chargerPartie(load.getSelectedFile().getAbsolutePath());
			modifChargement();
			System.out.println("////TEST////"+mode+" "+lvlPC1+" "+lvlPC2+" "+commencer);
			}
			catch(Exception ex){
				
			}
	    }               

	}
	
	class ItemAction_partie_quitter implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
	    }               

	}
	
	class ItemAction_options_parametresPartie implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			parametre.box1.setSelectedIndex(mode.ordinal());
			parametre.box2.setSelectedIndex(lvlPC1.ordinal()-1);
			parametre.box3.setSelectedIndex(lvlPC2.ordinal()-1);
			
			parametre.saveMode=mode;
			parametre.savelvlPC1=lvlPC1;
			parametre.savelvlPC2=lvlPC2;
			frame2.setVisible(true);
	    }               

	}
	
	class ItemAction_options_preferences implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			System.out.println("test");
			frame3.setVisible(true);
	    }               
		
	}
	
	class ItemAction_options_historiqueScores implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
	    }               

	}
	
	class ItemAction_aide_reglesDuJeu implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
	    }               

	}
	
	class ItemAction_aide_aPropos implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
	    }               

	}
	
	class ItemAction_annuler implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(monDessin.finPartie)monDessin.finPartie=false;
			engine.annuler(true);
	    }               

	}
	
	class ItemAction_refaire implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			engine.refaire(true);
	    }               

	}
	
	class ItemAction_stopper implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(engine.getCurrentGame().isPaused()){
				engine.getCurrentGame().reprendre();
				stopper.setText(" Pause ");
				//System.out.println("reprise");
			}
			else{
				engine.getCurrentGame().pause();
				stopper.setText(" Reprendre ");
				//System.out.println("en pause");
			}
	    }               

	}
	
	class ItemAction_finTour implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			monDessin.pionCliquer=false;
			engine.getCurrentGame().finirSonTour();
	    }               

	}
	
	class ItemAction_suggestion implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			
	    }               

	}
	
	// METHODE AFFICHAGE
	
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
		System.out.println("//////SaveReussi?: "+reussi);
		
	}

	@Override
	public void chargementReussi(boolean reussi)
	{
		System.out.println("///////LoadReussi?: "+reussi);
		
	}
	
	@Override
	public void afficherCoupJoue(Coup c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherPionsCaptures(ArrayList<Case> list) {
		// TODO Auto-generated method stub
		
	}

	// AUTRE METHODE
	
	public void modifChargement(){
		mode=Tools.getTypePartie(engine.getCurrentGame());
		if(mode.ordinal()==0){
			lvlPC1=defaut;
			lvlPC2=defaut;
		}else if(mode.ordinal()==1){
			lvlPC1=(engine.getCurrentGame().joueurBlanc.aiPlayer?Tools.getTypeOfPlayer(engine.getCurrentGame().joueurBlanc):Tools.getTypeOfPlayer(engine.getCurrentGame().joueurNoir));
			lvlPC2=defaut;
		}else if(mode.ordinal()==2){
			lvlPC1=Tools.getTypeOfPlayer(engine.getCurrentGame().joueurBlanc);
			lvlPC2=Tools.getTypeOfPlayer(engine.getCurrentGame().joueurNoir);	
		}
		commencer=engine.getPremierJoueur();
		parametre.box1.setSelectedIndex(mode.ordinal());
		parametre.box2.setSelectedIndex(lvlPC1.ordinal()-1);
		parametre.box3.setSelectedIndex(lvlPC2.ordinal()-1);
		parametre.box4.setSelectedIndex((commencer?0:1));
	}

	
	
}

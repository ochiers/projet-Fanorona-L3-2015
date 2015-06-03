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
	//	frame.setSize(842, 507);
		frame.setSize(1200, 700);
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
		JLabel j1 = new JLabel(" # Joueur 1 ", SwingConstants.CENTER );
		idj1 = new JLabel(" Erreur ", SwingConstants.CENTER);
		levelj1 = new JLabel(" Erreur ", SwingConstants.CENTER);
		JLabel j2 = new JLabel(" # Joueur 2 ", SwingConstants.CENTER);
		idj2 = new JLabel(" Erreur ", SwingConstants.CENTER);
		levelj2 = new JLabel(" Erreur ", SwingConstants.CENTER);
		scoreInt1 = new JLabel("  ", SwingConstants.CENTER);
		scoreInt2 = new JLabel("  ", SwingConstants.CENTER);	
		JLabel score1 = new JLabel(" Pions restants ", SwingConstants.CENTER);
		JLabel score2 = new JLabel(" Pions restants ", SwingConstants.CENTER);
		tour1 = new JLabel(" A votre tour ! ", SwingConstants.CENTER);
		tour2 = new JLabel(" A votre tour ! ", SwingConstants.CENTER);
		
			//joueur 1
		JPanel panelOuest = new JPanel(new GridLayout(5,1));
		panelOuest.setOpaque(false);
		//panel vide
		JPanel vide1 = new JPanel();
		vide1.setOpaque(false);
		//nom joueur et niveau
		JPanel play1 = new JPanel();
		play1.setLayout(new BoxLayout(play1, 1));
		play1.setBackground(Color.LIGHT_GRAY);
		play1.add(j1);
		play1.add(idj1);
		play1.add(levelj1);
		//score
		JPanel pions1 = new JPanel();
		pions1.setLayout(new BoxLayout(pions1, 1));
		pions1.setBackground(Color.GRAY);
		pions1.setSize(panelOuest.getWidth(), panelOuest.getHeight()/2);
		pions1.add(scoreInt1);
		pions1.add(score1);
		//annonce tour
		JPanel turn1 = new JPanel();
		turn1.setBackground(Color.LIGHT_GRAY);
		turn1.add(tour1);
		//panel vide
		JPanel vide2 = new JPanel();
		vide2.setOpaque(false);
		//ajout panel ouest
		panelOuest.add(vide1);
		panelOuest.add(play1);
		panelOuest.add(pions1);
		panelOuest.add(turn1);
		panelOuest.add(vide2);
 		
			//joueur 2
		JPanel panelEst = new JPanel(new GridLayout(5,1));
		panelEst.setOpaque(false);
		//panel vide
		JPanel empty1 = new JPanel();
		empty1.setOpaque(false);
		//nom joueur et niveau
		JPanel play2 = new JPanel();
		play2.setLayout(new BoxLayout(play2, 1));
		play2.setBackground(Color.LIGHT_GRAY);
		play2.add(j2);
		play2.add(idj2);
		play2.add(levelj2);
		//score
		JPanel pions2 = new JPanel();
		pions2.setLayout(new BoxLayout(pions2, 1));
		pions2.setBackground(Color.GRAY);
		pions2.setSize(panelEst.getWidth(), panelEst.getHeight()/2);
		pions2.add(scoreInt2);
		pions2.add(score2);
		//annonce tour
		JPanel turn2 = new JPanel();
		turn2.setBackground(Color.LIGHT_GRAY);
		turn2.add(tour2);
		//panel vide
		JPanel empty2 = new JPanel();
		empty2.setOpaque(false);
		//ajout panel ouest
		panelEst.add(empty1);
		panelEst.add(play2);
		panelEst.add(pions2);
		panelEst.add(turn2);
		panelEst.add(empty2);
 		
 			//ajout au panel accueil
 		panelAccueil.add(monDessin, BorderLayout.CENTER);
 		panelAccueil.add(panelOuest, BorderLayout.WEST);
 		panelAccueil.add(panelEst, BorderLayout.EAST);
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
			preference.save=panelAccueil.getImage();
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

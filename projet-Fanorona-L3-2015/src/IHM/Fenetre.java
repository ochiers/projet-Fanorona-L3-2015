package IHM;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;
import engine.*;

public class Fenetre implements Runnable,Affichage {
	static JFrame frame = new JFrame(" -- Fanorona -- ");
	static JFrame frame2 = new JFrame(" -- Paramètres -- ");
	static JFrame frame3 = new JFrame(" -- Préférences -- ");
	AireDeDessin monDessin;
	Engine engine;
	int lvlPC1;
	int lvlPC2;
	int mode;
	JLabel scoreInt1;
	JLabel scoreInt2;
	JButton annuler;
	JButton refaire;
	JButton stopper;
	
	public Fenetre(Engine e){
		engine=e;
	}
	
	public void run(){		

		frame.setSize(1280, 720);
		int fw = frame.getWidth();
		int fh = frame.getHeight();
		JPanel panelAccueil = new ImagePanel(new ImageIcon("src/images/imageDefault.jpg").getImage(), fw, fh);
		panelAccueil.setLayout(null);
		
			//grille
		monDessin = new AireDeDessin(this);
			monDessin.addMouseListener(new EcouteurDeSouris(monDessin));
			monDessin.setBounds(fw/6, (int)(0.3*fh/6), 4*fw/6, 4*fh/6);
	/*	JPanel panelFano = new ImagePanel(new ImageIcon("src/images/Fano9x5.jpg").getImage(), 4*fw/6, 4*fh/6);
			panelFano.setBounds(fw/6, (int)(0.3*fh/6), 4*fw/6, 4*fh/6);
			// dans ecouteurSours panel et non aireDeDessin faire 
			// //System.out.println(" ( " + e.getX() + " , " + e.getY() + " ) ");
			// pour calculer les ouvelles positions des pions
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
		int temp = (int)(0.8*fh/6)/2;
		annuler = new JButton(" Annuler Coup ");
			annuler.addActionListener(new EcouteurDeBouton(this,annuler.getText()));
			annuler.setBounds((int)((1.5*fw/6)-temp), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
		refaire = new JButton(" Refaire Coup ");
			refaire.addActionListener(new EcouteurDeBouton(this,refaire.getText()));
			refaire.setBounds((int)(((1.5*fw/6)-temp)+fw/6), (int)(4.6*fh/6), (int)(0.8*fh/6), (int)(0.8*fh/6));
		stopper = new JButton(" Pause ");
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
		scoreInt1 = new JLabel("" + engine.partieCourante.nombrePionBlanc);
			scoreInt1.setBounds((int)(0.25*fw/6), (int)(2.2*fh/6), (int)(0.8*fw/6), (int)(fh/6));
		scoreInt2 = new JLabel("" + engine.partieCourante.nombrePionNoir);
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
 		//panelAccueil.add(panelFano);
 		
 		frame.add(panelAccueil);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			//fenetre parametre
		frame2.setSize(500, 500);
		JPanel panelAccueil2 = new JPanel(new GridLayout(0,1));
		
		JLabel label1= new JLabel("Mode de jeu");
		panelAccueil2.add(label1);
		ButtonGroup bg1 = new ButtonGroup();
		JRadioButton r1b1 = new JRadioButton("Joueur 1 vs Joueur 2");
		JRadioButton r1b2 = new JRadioButton("Joueur 1 vs Ordi 1");
		JRadioButton r1b3 = new JRadioButton("Ordi 1 vs Ordi 2");
		if(engine.partieCourante.joueurBlanc.aiPlayer || engine.partieCourante.joueurNoir.aiPlayer){
			if(engine.partieCourante.joueurBlanc.aiPlayer && engine.partieCourante.joueurNoir.aiPlayer){
				r1b3.setSelected(true);
				mode=3;
			}
			else{
				r1b2.setSelected(true);
				mode=2;
			}
		}
		else{
			r1b1.setSelected(true);
			mode=1;
		}
		// ajout des boutons radio dans le groupe bg
		bg1.add(r1b1);
		bg1.add(r1b2);
		bg1.add(r1b3);
		panelAccueil2.add(r1b1);
		panelAccueil2.add(r1b2);
		panelAccueil2.add(r1b3);
		
		JLabel label2= new JLabel("Difficulte Ordi 1");
		panelAccueil2.add(label2);
		ButtonGroup bg2 = new ButtonGroup();
		JRadioButton r2b1 = new JRadioButton("Facile");
		JRadioButton r2b2 = new JRadioButton("Moyen");
		JRadioButton r2b3 = new JRadioButton("Difficile");
		
		// ajout des boutons radio dans le groupe bg
		bg2.add(r2b1);
		bg2.add(r2b2);
		bg2.add(r2b3);
		panelAccueil2.add(r2b1);
		panelAccueil2.add(r2b2);
		panelAccueil2.add(r2b3);
		
		JLabel label3= new JLabel("Difficulte Ordi 2");
		panelAccueil2.add(label3);
		ButtonGroup bg3 = new ButtonGroup();
		JRadioButton r3b1 = new JRadioButton("Facile");
		JRadioButton r3b2 = new JRadioButton("Moyen");
		JRadioButton r3b3 = new JRadioButton("Difficile");
		
		if(engine.partieCourante.joueurBlanc.aiPlayer || engine.partieCourante.joueurNoir.aiPlayer)
			if(engine.partieCourante.joueurBlanc.aiPlayer && engine.partieCourante.joueurNoir.aiPlayer){
				switch(engine.partieCourante.joueurBlanc.getNiveau()){
					case "IA Facile":
						r2b1.setSelected(true);
						lvlPC1=1;
						break;
					case "IA Moyen":
						r2b2.setSelected(true);
						lvlPC1=2;
						break;
					case "IA Difficile":
						r2b3.setSelected(true);
						lvlPC1=3;
						break;
					default:
						System.out.println("Niveau IA blanc inconnu");
						break;
				}
				switch(engine.partieCourante.joueurNoir.getNiveau()){
					case "IA Facile":
						r3b1.setSelected(true);
						lvlPC2=1;
						break;
					case "IA Moyen":
						r3b2.setSelected(true);
						lvlPC2=2;
						break;
					case "IA Difficile":
						r3b3.setSelected(true);
						lvlPC2=3;
						break;
					default:
						System.out.println("Niveau IA noir inconnu");
						break;
				}
			}
			else{
				if(engine.partieCourante.joueurBlanc.aiPlayer){
					switch(engine.partieCourante.joueurBlanc.getNiveau()){
						case "IA Facile":
							r2b1.setSelected(true);
							lvlPC1=1;
							break;
						case "IA Moyen":
							r2b2.setSelected(true);
							lvlPC1=2;
							break;
						case "IA Difficile":
							r2b3.setSelected(true);
							lvlPC1=3;
							break;
						default:
							System.out.println("Niveau IA blanc inconnu");
							break;
					}
				}
				else{
					switch(engine.partieCourante.joueurNoir.getNiveau()){
						case "IA Facile":
							r2b1.setSelected(true);
							lvlPC1=1;
							break;
						case "IA Moyen":
							r2b2.setSelected(true);
							lvlPC1=2;
							break;
						case "IA Difficile":
							r2b3.setSelected(true);
							lvlPC1=3;
							break;
						default:
							System.out.println("Niveau IA noir inconnu");
							break;
					}
				}
			}
		else{
			r2b2.setSelected(true);
			r3b2.setSelected(true);
			lvlPC1=2;
			lvlPC2=2;
		}
		// ajout des boutons radio dans le groupe bg
		bg3.add(r3b1);
		bg3.add(r3b2);
		bg3.add(r3b3);
		panelAccueil2.add(r3b1);
		panelAccueil2.add(r3b2);
		panelAccueil2.add(r3b3);
		
		frame2.add(panelAccueil2);
		frame2.setResizable(false);
		frame2.setVisible(false);
		
		//NE FONCTIONNE PAS, NE PAS DECOMMENTER !!!!!
			//fenetre preferences
		frame3.setSize(500, 500);
		JPanel panelAccueil3 = new JPanel(new GridLayout(0,1));
		
			// FOND
		JLabel fond = new JLabel(" Choix fond d'écran ");
		panelAccueil3.add(fond);
		ButtonGroup groupe1 = new ButtonGroup();
		JRadioButtonMenuItem rb1 = new JRadioButtonMenuItem(" image1 ", new ImageIcon("src/images/image1"), false);
		JRadioButtonMenuItem rb2 = new JRadioButtonMenuItem(" image2 ", new ImageIcon("src/images/image2"), false);
		JRadioButtonMenuItem rb3 = new JRadioButtonMenuItem(" image3 ", new ImageIcon("src/images/image3"), false);
		JRadioButtonMenuItem rb4 = new JRadioButtonMenuItem(" image4 ", new ImageIcon("src/images/image4"), false);
		JRadioButtonMenuItem rb5 = new JRadioButtonMenuItem(" image5 ", new ImageIcon("src/images/image5"), false);
		JRadioButtonMenuItem rb6 = new JRadioButtonMenuItem(" image6 ", new ImageIcon("src/images/image6"), false);
		JRadioButtonMenuItem rb7 = new JRadioButtonMenuItem(" image7 ", new ImageIcon("src/images/image7"), false);
		JRadioButtonMenuItem rb8 = new JRadioButtonMenuItem(" fond par défaut ", new ImageIcon("src/images/imageDefault"), true);
		if(rb1.isSelected() == true) panelAccueil = new ImagePanel(rb1.getIcon(), fw, fh);
		if(rb2.isSelected() == true) panelAccueil = new ImagePanel(rb2.getIcon(), fw, fh);
		if(rb3.isSelected() == true) panelAccueil = new ImagePanel(rb3.getIcon(), fw, fh);
		if(rb4.isSelected() == true) panelAccueil = new ImagePanel(rb4.getIcon(), fw, fh);
		if(rb5.isSelected() == true) panelAccueil = new ImagePanel(rb5.getIcon(), fw, fh);
		if(rb6.isSelected() == true) panelAccueil = new ImagePanel(rb6.getIcon(), fw, fh);
		if(rb7.isSelected() == true) panelAccueil = new ImagePanel(rb7.getIcon(), fw, fh);
		else panelAccueil = new ImagePanel(rb8.getIcon(), fw, fh);
		// ajout des boutons radio dans le groupe bg
		groupe1.add(rb1);
		groupe1.add(rb2);
		groupe1.add(rb3);
		groupe1.add(rb4);
		groupe1.add(rb5);
		groupe1.add(rb6);
		groupe1.add(rb7);
		groupe1.add(rb8);
		//ajout de bg au panel
		panelAccueil3.add(rb1);
		panelAccueil3.add(rb2);
		panelAccueil3.add(rb3);
		panelAccueil3.add(rb4);
		panelAccueil3.add(rb5);
		panelAccueil3.add(rb6);
		panelAccueil3.add(rb7);
		panelAccueil3.add(rb8);
		
			// NOMS
		JLabel nom = new JLabel(" Choix nom joueurs humains ");
		panelAccueil3.add(nom);
		String nameJ1 = engine.partieCourante.joueurBlanc.getNiveau();
		JTextField name1 = new JTextField(" Humain 1 ");
		if (nameJ1.equals("Humain")){
					// ajout boite de dialogue
			engine.partieCourante.joueurBlanc.name = name1.getText();
			idj1 = new JLabel((String)(name1.getText()));
			levelj1 = new JLabel(" Bonne Chance ! ");
		}
		String nameJ2 = engine.partieCourante.joueurNoir.getNiveau();
		JTextField name2 = new JTextField(" Humain 2 ");
		if (nameJ2.equals("Humain")){
			engine.partieCourante.joueurNoir.name = name2.getText();
			idj2 = new JLabel((String)(name2.getText()));
			levelj2 = new JLabel(" Bonne Chance ! ");
		}
		else{
			// verifier parametres par defaut !!!!!!!!!!!!!!! 
			idj1 = new JLabel(" Humain ");
			levelj1 = new JLabel(" Bonne Chance ! ");
			idj2 = new JLabel(" Humain ");
			levelj2 = new JLabel(" Bonne Chance ! ");
		}
		
		// ajout des boites de dialogue
		panelAccueil3.add(name1);
		panelAccueil3.add(name2);

			// COULEUR PIONS
		JLabel pion = new JLabel(" Choix couleur pions ");
		panelAccueil3.add(pion);
		ButtonGroup groupe2 = new ButtonGroup();
		JRadioButton rb10 = new JRadioButton(" Noir ");
		JRadioButton rb20 = new JRadioButton(" Blanc ");
		JRadioButton rb30 = new JRadioButton(" Bleu ");
		JRadioButton rb40 = new JRadioButton(" Rouge ");
		JRadioButton rb50 = new JRadioButton(" Vert ");
		JRadioButton rb60 = new JRadioButton(" Jaune ");
		JRadioButton rb70 = new JRadioButton(" Multicolor ");
		if(rb10.isSelected() == true){
			
		}
		if(rb20.isSelected() == true){
			
		}
		if(rb30.isSelected() == true){
			
		}
		if(rb40.isSelected() == true){
			
		}
		if(rb50.isSelected() == true){
			
		}
		if(rb60.isSelected() == true){
			
		}
		if(rb70.isSelected() == true){
			
		}
		else{
			
		}
		
		// ajout des boutons radio dans le groupe bg
		groupe2.add(rb10);
		groupe2.add(rb20);
		groupe2.add(rb30);
		groupe2.add(rb40);
		groupe2.add(rb50);
		groupe2.add(rb60);
		groupe2.add(rb70);
		//ajout de bg au panel
		panelAccueil3.add(rb10);
		panelAccueil3.add(rb20);
		panelAccueil3.add(rb30);
		panelAccueil3.add(rb40);
		panelAccueil3.add(rb50);
		panelAccueil3.add(rb60);
		panelAccueil3.add(rb70);
		
		frame3.add(panelAccueil3);
		frame3.setResizable(false);
		frame3.setVisible(false);
	}

	public void afficherJeu(){
		monDessin.repaint();
	}

	public void afficherPionsPossibles(ArrayList<Case> l) {
		monDessin.pionPossible=l;
		monDessin.repaint();
	}

	public void afficherVictoire(Player p) {
		// TODO Auto-generated method stub	
	}
	
	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2){
		System.out.println("------------MULTI DIRECTION------------------");
		monDessin.doitChoisir=true;
		monDessin.l1=l1;
		monDessin.l2=l2;
		monDessin.repaint();
	}

	@Override
	public void afficherPionDuCombo(Case pionCourant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo){
		System.out.println("COOOOOOOOOOOOOOOOMBO");
		monDessin.combo=combo;
		monDessin.repaint();
		
	}
	
}

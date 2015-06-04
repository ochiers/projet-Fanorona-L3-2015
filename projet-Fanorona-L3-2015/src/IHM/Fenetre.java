package IHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import AI.HumanPlayer;
import network.NetworkPlayer;
import engine.*;

public class Fenetre implements Runnable, Affichage {
	JFrame				frame	= new JFrame(" -- Fanorona -- ");
	JFrame				frame2	= new JFrame(" -- Parametres -- ");
	JFrame				frame3	= new JFrame(" -- Preferences -- ");
	Parametres			parametre;
	PreferencesOnglets	preference;
	AireDeDessin		monDessin;
	Firework			victoire = new Firework(this);
	EngineServices		engine;
	Dimension			size	= new Dimension(9, 5);

	PlayerType			lvlPC1;
	PlayerType			lvlPC2;
	Configuration		mode;
	boolean				commencer;
	PlayerType			defaut	= PlayerType.IAMoyenne;

	JLabel				scoreInt1;
	JLabel				scoreInt2;

	JLabel				tour1;
	JLabel				tour2;
	ImagePanel			panelAccueil;
	int					fw, fh;
	JLabel				idj1;
	JLabel				levelj1;
	JLabel				idj2;
	JLabel				levelj2;
	Color				pion1	= Color.black;
	Color				pion2	= Color.white;
	String				nameJ1;
	String				nameJ2;

	JMenuBar			menuBar;
	JMenu				partie;
	JMenu				options;
	JMenu				aide;
	JMenu				reseau;

	JMenuItem			partie_nouvellePartie;
	JMenuItem			partie_recommencer;
	JMenuItem			partie_sauvegarder;
	JMenuItem			partie_charger;
	JMenuItem			partie_quitter;

	JMenuItem			options_parametresPartie;
	JMenuItem			options_preferences;
	JMenuItem			options_historiqueScores;

	JMenuItem			aide_reglesDuJeu;
	JMenuItem			aide_aPropos;

	JMenuItem			reseau_heberger;
	JMenuItem			reseau_rejoindre;

	JButton				annuler;
	JButton				refaire;
	JButton				stopper;
	JButton				finTour;
	JButton				suggestion;

	int					wmin	= 673;
	int					hmin	= 405;
	int					wmax	= 1280;
	int					hmax	= 720;

	int					taillePion;
	JPanel				panelVictoire;

	public Fenetre(EngineServices e)
	{
		engine = e;
	}

	public void run()
	{
		System.out.println("//////////////////////////////////////////////////////");
		// frame.setSize(842, 507);
		frame.setSize(1200, 700);
		// frame.setSize(wmax,hmax);
		frame.setMinimumSize(new Dimension(wmin, hmin));
		frame.setMaximumSize(new Dimension(wmax, hmax));
		fw = frame.getWidth();
		fh = frame.getHeight();
		panelAccueil = new ImagePanel(new ImageIcon("src/images/imageDefault.jpg").getImage(), fw, fh);
		panelAccueil.setLayout(new BorderLayout(20, 10));
		panelVictoire = new JPanel();
		panelVictoire.setSize(panelAccueil.getSize());
		panelVictoire.setOpaque(false);
		panelVictoire.setVisible(false);

		// grille
		monDessin = new AireDeDessin(this);
		monDessin.addMouseListener(new EcouteurDeSouris(monDessin));

		// barre de Menu
		menuBar = new JMenuBar();
		// menu1
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

		// menu2
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

		// Menu Reseau
		reseau = new JMenu(" Reseau ");
		reseau_heberger = new JMenuItem(" Heberger une partie ");
		reseau_rejoindre = new JMenuItem(" Rejoindre une partie ");

		reseau_heberger.addActionListener(new ItemAction_reseau_heberger());
		reseau_rejoindre.addActionListener(new ItemAction_reseau_rejoindre());

		reseau.add(reseau_heberger);
		reseau.add(reseau_rejoindre);

		// menu3
		aide = new JMenu(" Aide ");
		aide_reglesDuJeu = new JMenuItem(" Regles du Jeu ");
		aide_aPropos = new JMenuItem(" A Propos ");

		aide_reglesDuJeu.addActionListener(new ItemAction_aide_reglesDuJeu());
		aide_aPropos.addActionListener(new ItemAction_aide_aPropos());

		aide.add(aide_reglesDuJeu);
		aide.add(aide_aPropos);

		// ajouts dans barre de menu
		menuBar.add(partie);
		menuBar.add(options);
		menuBar.add(reseau);
		menuBar.add(aide);

		// boutons commandes
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

		// boutons
		JPanel panelSud = new JPanel(new FlowLayout());
		panelSud.setOpaque(false);
		panelSud.add(annuler);
		panelSud.add(refaire);
		panelSud.add(stopper);
		panelSud.add(finTour);
		panelSud.add(suggestion);

		// affichages joueurs
		JLabel j1 = new JLabel(" # Joueur 1 ", SwingConstants.CENTER);
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
		DessinPion monPion1 = new DessinPion(this, pion1, taillePion);
		DessinPion monPion2 = new DessinPion(this, pion2, taillePion);

		// joueur 1
		JPanel panelOuest = new JPanel(new GridLayout(9, 1));
		taillePion = panelOuest.getWidth();
		JLabel vide1 = new JLabel();
		panelOuest.setBackground(new Color(255, 255, 255, 128));
		panelOuest.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
		panelOuest.add(vide1);
		panelOuest.add(j1);
		panelOuest.add(idj1);
		panelOuest.add(levelj1);
		panelOuest.add(monPion1);
		panelOuest.add(scoreInt1);
		panelOuest.add(score1);
		panelOuest.add(tour1);

		// joueur 2
		JPanel panelEst = new JPanel(new GridLayout(9, 1));
		taillePion = panelEst.getWidth();
		JLabel vide2 = new JLabel();
		panelEst.setBackground(new Color(255, 255, 255, 128));
		panelEst.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.black));
		panelEst.add(vide2);
		panelEst.add(j2);
		panelEst.add(idj2);
		panelEst.add(levelj2);
		panelEst.add(monPion2);
		panelEst.add(scoreInt2);
		panelEst.add(score2);
		panelEst.add(tour2);

		// ajout au panel accueil
		panelAccueil.add(monDessin, BorderLayout.CENTER);
		panelAccueil.add(panelOuest, BorderLayout.WEST);
		panelAccueil.add(panelEst, BorderLayout.EAST);
		panelAccueil.add(panelSud, BorderLayout.SOUTH);

		// recuperation joueurs
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
		// frame.setResizable(false);
		frame.setVisible(true);
		frame.addWindowListener(new EcouteurDeFenetre(engine));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// ACTIONLISTENER

	class ItemAction_partie_nouvellePartie implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			Player humain1 = Tools.createPlayer(engine, PlayerType.Humain, nameJ1);
			Player humain2 = Tools.createPlayer(engine, PlayerType.Humain, nameJ2);
			Player pc1 = Tools.createPlayer(engine, lvlPC1, "Ordi");
			Player pc2 = Tools.createPlayer(engine, lvlPC2, "Ordi");

			if (mode.ordinal() == 0)
				engine.nouvellePartie(humain1, humain2, (commencer ? 0 : 1), size);
			else if (mode.ordinal() == 1)
				engine.nouvellePartie(humain1, pc1, (commencer ? 0 : 1), size);
			else if (mode.ordinal() == 2)
				engine.nouvellePartie(pc1, pc2, (commencer ? 0 : 1), size);
			monDessin.finPartie = false;
		}

	}

	class ItemAction_partie_recommencer implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			Player p1 = Tools.createPlayer(engine, Tools.getTypeOfPlayer((engine.getCurrentGame().joueurBlanc)), engine.getCurrentGame().joueurBlanc.name);
			Player p2 = Tools.createPlayer(engine, Tools.getTypeOfPlayer((engine.getCurrentGame().joueurNoir)), engine.getCurrentGame().joueurNoir.name);
			engine.nouvellePartie(p1, p2, (commencer ? 0 : 1), size);
			monDessin.finPartie = false;
		}

	}

	class ItemAction_partie_sauvegarder implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(frame);
			engine.sauvegarderPartie(save.getSelectedFile().getAbsolutePath());
		}

	}

	class ItemAction_partie_charger implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				JFileChooser load = new JFileChooser();
				load.showOpenDialog(frame);
				engine.chargerPartie(load.getSelectedFile().getAbsolutePath());
				modifChargement();
				System.out.println("////TEST////" + mode + " " + lvlPC1 + " " + lvlPC2 + " " + commencer);
			} catch (Exception ex)
			{

			}
		}

	}

	class ItemAction_partie_quitter implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}

	}

	class ItemAction_options_parametresPartie implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			parametre.box1.setSelectedIndex(mode.ordinal());
			parametre.box2.setSelectedIndex(lvlPC1.ordinal() - 1);
			parametre.box3.setSelectedIndex(lvlPC2.ordinal() - 1);

			parametre.saveMode = mode;
			parametre.savelvlPC1 = lvlPC1;
			parametre.savelvlPC2 = lvlPC2;
			frame2.setVisible(true);
		}

	}

	class ItemAction_options_preferences implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("test");
			preference.save = panelAccueil.getImage();
			frame3.setVisible(true);
		}

	}

	class ItemAction_options_historiqueScores implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{

		}

	}

	class ItemAction_aide_reglesDuJeu implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{

		}

	}

	class ItemAction_aide_aPropos implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{

		}

	}

	class ItemAction_annuler implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			if (monDessin.finPartie)
				monDessin.finPartie = false;
			engine.annuler(true);
		}

	}

	class ItemAction_refaire implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			engine.refaire(true);
		}

	}

	class ItemAction_stopper implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			if (engine.getCurrentGame().isPaused())
			{
				engine.getCurrentGame().reprendre();
				stopper.setText(" Pause ");
				// System.out.println("reprise");
			} else
			{
				engine.getCurrentGame().pause();
				stopper.setText(" Reprendre ");
				// System.out.println("en pause");
			}
		}

	}

	class ItemAction_finTour implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			monDessin.pionCliquer = false;
			engine.getCurrentGame().finirSonTour();
		}

	}

	class ItemAction_suggestion implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{

		}

	}

	class ItemAction_reseau_heberger implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				engine.hebergerPartie(12345);
			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
			Player p1 = new HumanPlayer(engine, false, "Joueur");
			Player p2 = new NetworkPlayer(engine, false, "Player at " + engine.getNetworkManager().socketEnvoiPrincipal.getInetAddress());
			engine.nouvellePartie(p1, p2, 0, size);
			monDessin.finPartie = false;
		}

	}

	class ItemAction_reseau_rejoindre implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			new RejoindrePartieReseauIHM(engine);
			monDessin.finPartie = false;
		}

	}

	// METHODE AFFICHAGE

	public void afficherJeu()
	{
		monDessin.repaint();
	}

	public void afficherPionsPossibles(ArrayList<Case> l)
	{
		// System.out.println("22222222");

		monDessin.pionPossible = l;
		monDessin.repaint();
	}

	public void afficherVictoire(Player p)
	{
		monDessin.finPartie = true;
		monDessin.repaint();
		victoire.setVisible(true);
		String winner = (this.engine.getWinner()).name;
		System.out.println("VICTOIRE"+ winner);
		panelVictoire.add(victoire);
		panelVictoire.setVisible(true);
		panelAccueil.add(panelVictoire);
		frame.add(panelVictoire);
	}

	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2)
	{
		// if(!engine.partieCourante.joueurCourant.aiPlayer){
		if (!engine.getCurrentGame().joueurCourant.aiPlayer)
		{
			// System.out.println("------------MULTI DIRECTION------------------");
			monDessin.doitChoisir = true;
			monDessin.l1 = l1;
			monDessin.l2 = l2;
		}
		monDessin.repaint();
	}

	@Override
	public void afficherPionDuCombo(Case pionCourant)
	{
		// System.out.println("33333333333333");
		monDessin.pionCombo = pionCourant;
		// System.out.println("le pion combo est :"+pionCourant.position.ligne+" "+pionCourant.position.colonne);
		monDessin.repaint();
		// monDessin.estEnCombo=false;

	}

	@Override
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo)
	{
		// System.out.println("COOOOOOOOOOOOOOOOMBO");
		monDessin.combo = combo;
		monDessin.repaint();

	}

	@Override
	public void sauvegardeReussie(boolean reussi)
	{
		System.out.println("//////SaveReussi?: " + reussi);

	}

	@Override
	public void chargementReussi(boolean reussi)
	{
		System.out.println("///////LoadReussi?: " + reussi);

	}

	@Override
	public void afficherCoupJoue(Coup c)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void afficherPionsCaptures(ArrayList<Case> list)
	{
		// TODO Auto-generated method stub

	}

	// AUTRE METHODE

	public void modifChargement()
	{
		mode = Tools.getTypePartie(engine.getCurrentGame());
		if (mode.ordinal() == 0)
		{
			lvlPC1 = defaut;
			lvlPC2 = defaut;
		} else if (mode.ordinal() == 1)
		{
			lvlPC1 = (engine.getCurrentGame().joueurBlanc.aiPlayer ? Tools.getTypeOfPlayer(engine.getCurrentGame().joueurBlanc) : Tools.getTypeOfPlayer(engine.getCurrentGame().joueurNoir));
			lvlPC2 = defaut;
		} else if (mode.ordinal() == 2)
		{
			lvlPC1 = Tools.getTypeOfPlayer(engine.getCurrentGame().joueurBlanc);
			lvlPC2 = Tools.getTypeOfPlayer(engine.getCurrentGame().joueurNoir);
		}
		commencer = engine.getPremierJoueur();
		parametre.box1.setSelectedIndex(mode.ordinal());
		parametre.box2.setSelectedIndex(lvlPC1.ordinal() - 1);
		parametre.box3.setSelectedIndex(lvlPC2.ordinal() - 1);
		parametre.box4.setSelectedIndex((commencer ? 0 : 1));
	}

}

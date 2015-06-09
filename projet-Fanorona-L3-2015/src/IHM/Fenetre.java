package IHM;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import AI.MediumAI;
import network.NetworkPlayer;
import engine.*;

public class Fenetre implements Runnable, Affichage {
	JFrame						frame				= new JFrame(" -- Fanorona -- ");
	JFrame						frame2				= new JFrame(" -- Parametres -- ");
	JFrame						frame3				= new JFrame(" -- Preferences -- ");
	Parametres					parametre;
	PreferencesOnglets			preference;
	AireDeDessin				monDessin;
	EngineServices				engine;
	Dimension					size				= new Dimension(9, 5);

	PlayerType					lvlPC1, lvlPC2;
	Configuration				mode;
	boolean						commencer;
	PlayerType					defaut				= PlayerType.IAMoyenne;

	JLabel						scoreInt1, scoreInt2;

	JLabel						tour1, tour2;
	private static ImagePanel	panelAccueil;
	ImageIcon					imageActuelle		= new ImageIcon("Ressources/images/imageDefault.jpg");

	int							fw;
	int							fh;
	JLabel						idj1, idj2;
	JLabel						levelj1, levelj2;
	Color						pion1				= Color.black;
	Color						pion2				= Color.white;
	String						nameJ1, nameJ2;

	JMenuBar					menuBar;
	JMenu						partie, options, aide, reseau;

	JMenuItem					partie_nouvellePartie;
	JMenuItem					partie_recommencer;
	JMenuItem					partie_sauvegarder;
	JMenuItem					partie_charger;
	JMenuItem					partie_quitter;

	JMenuItem					options_parametresPartie;
	JMenuItem					options_preferences;
	// JMenuItem options_historiqueScores;

	JMenuItem					aide_reglesDuJeu;
	JMenuItem					aide_aPropos;

	JMenuItem					reseau_heberger;
	JMenuItem					reseau_rejoindre;

	JButton						annuler;
	JButton						refaire;
	JButton						stopper;
	JButton						finTour;
	JButton						suggestion;

	static int					wmin				= 673;
	static int					hmin				= 405;
	int							wmax				= 1280;
	int							hmax				= 720;

	int							taillePion;
	JFrame						frameVictoire;
	JPanel						panelPause;

	String						fichierJoueurBlanc	= "." + File.separator + "Ressources" + File.separator + "Pions" + File.separator + "pionBlanc.png";
	String						fichierJoueurNoir	= "." + File.separator + "Ressources" + File.separator + "Pions" + File.separator + "pionNoir.png";

	public Fenetre(EngineServices e)
	{
		engine = e;
	}

	public void run()
	{
		frame.setSize(1200, 700);
		frame.setMinimumSize(new Dimension(wmin, hmin));
		frame.setMaximumSize(new Dimension(wmax, hmax));
		fw = frame.getWidth();
		fh = frame.getHeight();
		setPanelAccueil(new ImagePanel(frame, new ImageIcon("src/images/imageDefault.jpg").getImage(), fw, fh));
		getPanelAccueil().setLayout(new BorderLayout(20, 10));

		// grille
		monDessin = new AireDeDessin(this);
		monDessin.addMouseListener(new EcouteurDeSouris(monDessin));
		monDessin.addMouseMotionListener(new EcouteurDeSouris(monDessin));

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
		// options_historiqueScores = new JMenuItem(" Historique Scores ");

		options_parametresPartie.addActionListener(new ItemAction_options_parametresPartie());
		options_preferences.addActionListener(new ItemAction_options_preferences());
		// options_historiqueScores.addActionListener(new
		// ItemAction_options_historiqueScores());

		options.add(options_parametresPartie);
		options.add(options_preferences);
		// options.add(options_historiqueScores);

		// Menu Reseau
		reseau = new JMenu(" Reseau ");
		reseau_heberger = new JMenuItem(" Heberger une partie ");
		reseau_rejoindre = new JMenuItem(" Rejoindre une partie ");

		reseau_heberger.addActionListener(new ItemAction_reseau_heberger());
		reseau_rejoindre.addActionListener(new ItemAction_reseau_rejoindre(this));

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

		// redimensionnage
		annuler.setSize(new Dimension((int) (0.1 * fw), (int) (0.15 * fh)));
		refaire.setSize((int) (0.1 * fw), (int) (0.15 * fh));
		stopper.setSize((int) (0.1 * fw), (int) (0.15 * fh));
		finTour.setSize((int) (0.1 * fw), (int) (0.15 * fh));
		suggestion.setSize((int) (0.1 * fw), (int) (0.15 * fh));

		// boutons
		JPanel panelSud = new JPanel(new FlowLayout());
		panelSud.setPreferredSize(new Dimension((int) (0.7 * fw), (int) (0.15 * fh)));
		panelSud.setOpaque(false);
		panelSud.add(annuler);
		panelSud.add(refaire);
		panelSud.add(stopper);
		panelSud.add(finTour);
		panelSud.add(suggestion);

		// affichages joueurs
		JLabel j1 = new JLabel(" # Joueur 1 ", SwingConstants.CENTER);
		JLabel j2 = new JLabel(" # Joueur 2 ", SwingConstants.CENTER);
		idj1 = new JLabel(" Erreur ", SwingConstants.CENTER);
		idj2 = new JLabel(" Erreur ", SwingConstants.CENTER);
		levelj1 = new JLabel(" Erreur ", SwingConstants.CENTER);
		levelj2 = new JLabel(" Erreur ", SwingConstants.CENTER);
		scoreInt1 = new JLabel("  ", SwingConstants.CENTER);
		scoreInt2 = new JLabel("  ", SwingConstants.CENTER);
		JLabel score1 = new JLabel(" Pions restants ", SwingConstants.CENTER);
		JLabel score2 = new JLabel(" Pions restants ", SwingConstants.CENTER);
		tour1 = new JLabel(" A votre tour ! ", SwingConstants.CENTER);
		tour2 = new JLabel(" A votre tour ! ", SwingConstants.CENTER);
		DessinPion monPion2 = new DessinPion(this, taillePion, Pion.Noir);
		DessinPion monPion1 = new DessinPion(this, taillePion, Pion.Blanc);

		// joueur 1
		JPanel panelOuest = new JPanel(new GridLayout(9, 1)){
			public void paintComponent(Graphics g){
				g.setColor(Color.white);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
		taillePion = (int) (0.9 * panelOuest.getWidth());
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
		JPanel panelEst = new JPanel(new GridLayout(9, 1)){
			public void paintComponent(Graphics g){
				g.setColor(Color.white);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
			}
		};
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
		getPanelAccueil().add(monDessin, BorderLayout.CENTER);
		getPanelAccueil().add(panelOuest, BorderLayout.WEST);
		getPanelAccueil().add(panelEst, BorderLayout.EAST);
		getPanelAccueil().add(panelSud, BorderLayout.SOUTH);

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
		frame.add(getPanelAccueil());
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new EcouteurDeFenetre(engine));
		fw = frame.getWidth();
		fh = frame.getHeight();
		frame.repaint();
	}

	// ACTIONLISTENER

	class ItemAction_partie_nouvellePartie implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			Player j1 = null;
			Player j2 = null;
			switch (mode)
			{
				case HumainVSHumain:
					j1 = Tools.createPlayer(engine, PlayerType.Humain, nameJ1);
					j2 = Tools.createPlayer(engine, PlayerType.Humain, nameJ2);
					break;
				case HumainVSIA:
					j1 = Tools.createPlayer(engine, PlayerType.Humain, nameJ1);
					j2 = Tools.createPlayer(engine, lvlPC1, "Ordi");
					break;
				case IAvsIA:
					j1 = Tools.createPlayer(engine, lvlPC1, "Ordi");
					j2 = Tools.createPlayer(engine, lvlPC2, "Ordi");
					break;
				default:
					break;
			}

			engine.nouvellePartie(j1, j2, (commencer ? 0 : 1), size);
			monDessin.finPartie = false;
			monDessin.pionCliquer = false;
			monDessin.surbrillance = false;
		}

	}

	class ItemAction_partie_recommencer implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			engine.recommencer(true);
			monDessin.finPartie = false;
		}

	}

	class ItemAction_partie_sauvegarder implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(frame);
			if (save.getSelectedFile() != null)
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
			} catch (Exception ex)
			{

			}
		}

	}

	class ItemAction_partie_quitter implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			engine.quitter();
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
			preference.save = getPanelAccueil().getImage();
			frame3.setVisible(true);
		}

	}

	/*
	 * class ItemAction_options_historiqueScores implements ActionListener {
	 * 
	 * public void actionPerformed(ActionEvent e) {
	 * 
	 * }
	 * 
	 * }
	 */

	class ItemAction_aide_reglesDuJeu implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			try
			{
				String os = System.getProperty("os.name").toLowerCase();
				Runtime runtime = Runtime.getRuntime();
				if (os.contains("win"))
					runtime.exec("cmd start ." + File.separator + "Ressources" + File.separator + "Fanorona_2.pdf");
				else
					runtime.exec("firefox ." + File.separator + "Ressources" + File.separator + "Fanorona_2.pdf");
				// runtime.exec("Google Chrome ."+File.separator+"Ressources"+File.separator+"Fanorona_2.pdf");

			} catch (IOException e1)
			{
				e1.printStackTrace();
			}
		}

	}

	class ItemAction_aide_aPropos implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			String str = "Jeu du Fanorona\n";
			str += "Projet de fin de licence informatique\n\n";
			str += "Developpé par :\n";
			str += "OCHIER Sébastien et SOULIER Clément (moteur)\n";
			str += "BERNE Corentin et FERNANDES Jérémy (Intelligence Artificielle)\n";
			str += "BOUCHER Jordan et CROUZIER Justine (Interface Homme Machine)\n";
			JOptionPane.showMessageDialog(frame, str,"A Propos", JOptionPane.DEFAULT_OPTION);
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
				engine.reprendre();
				stopper.setText(" Pause ");
			} else
			{
				engine.pause();
				stopper.setText(" Reprendre ");
				panelPause = new EnPause(" Jeu en pause ");
				panelPause.setVisible(true);
			}
		}
	}

	class ItemAction_finTour implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			monDessin.pionCliquer = false;
			if (Tools.getTypeOfPlayer(engine.getJoueurCourant()) == PlayerType.Humain)
				engine.finirSonTour(true);
		}
	}

	class ItemAction_suggestion implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{
			Player p2 = new MediumAI(engine, true, "IA suggestion");
			ArrayList<Case> pionsPossibles = engine.getCurrentGame().lesPionsQuiPeuventManger();
			if (pionsPossibles.size() == 0)
			{
				pionsPossibles = engine.getCurrentGame().lesPionsJouables();
			}
			Case[] tmp = new Case[pionsPossibles.size()];
			Coup c = p2.play(Game.copyMatrice(engine.getPlateau()), pionsPossibles.toArray(tmp));
		}

	}

	class ItemAction_reseau_heberger implements ActionListener {

		public void actionPerformed(ActionEvent e)
		{

			monDessin.attenteReseau = true;
			monDessin.repaint();
			int res = JOptionPane.showConfirmDialog(frame, "Le jeu sera bloqué jusqu'à ce qu'un adversaire se connecte sur le port n°12345,\n voulez vous continuer ?", "Heberger une partie", JOptionPane.YES_NO_OPTION);
			if (res == JOptionPane.YES_OPTION)
			{
				try
				{
					engine.hebergerPartie(12345);
					Player p1 = new HumanPlayer(engine, false, "Joueur");
					Player p2 = new NetworkPlayer(engine, false, "Player at " + engine.getNetworkManager().socketEnvoiPrincipal.getInetAddress());
					engine.nouvellePartie(p1, p2, 0, size);
					monDessin.finPartie = false;
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
			monDessin.attenteReseau = false;
			monDessin.repaint();
		}
	}

	class ItemAction_reseau_rejoindre implements ActionListener {

		Fenetre	f;

		public ItemAction_reseau_rejoindre(Fenetre f)
		{
			this.f = f;
		}

		public void actionPerformed(ActionEvent e)
		{
			new RejoindrePartieReseauIHM(engine, f);
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
		frameVictoire = new Firework(this);
		frameVictoire.setSize(new Dimension(Fenetre.wmin, Fenetre.hmin));
		/*
		 * JPanel gagnant = new JPanel(); gagnant.setOpaque(false);
		 * gagnant.setForeground(new Color(0, 0, 0, 0)); String winner =
		 * (this.engine.getWinner()).name; JLabel win = new JLabel(" " + winner
		 * + " gagne la partie !!!"); JLabel congrats = new
		 * JLabel(" FELICITATIONS !!!"); gagnant.setAlignmentX(fw/2);
		 * //gagnant.setAlignmentY(fh/2); gagnant.add(win);
		 * gagnant.add(congrats); frameVictoire.add(gagnant);
		 */
		frameVictoire.setVisible(true);
		frameVictoire.repaint();
	}

	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2)
	{
		if (!engine.getCurrentGame().joueurCourant.aiPlayer)
		{
			monDessin.doitChoisir = true;
			monDessin.l1 = l1;
			monDessin.l2 = l2;
		}
		monDessin.repaint();
	}

	@Override
	public void afficherPionDuCombo(Case pionCourant)
	{
		monDessin.pionCombo = pionCourant;
		monDessin.repaint();

	}

	@Override
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo)
	{
		monDessin.combo = combo;
		monDessin.repaint();
	}

	@Override
	public void sauvegardeReussie(boolean reussi)
	{
		System.out.println("//////SaveReussi?: " + reussi);
		String str = "La sauvegarde";
		if (reussi)
		{
			str += " a réussie !";
			JOptionPane.showMessageDialog(frame, str, "Sauvagarde", JOptionPane.INFORMATION_MESSAGE);
		} else
		{
			str += " a échouée !";
			JOptionPane.showMessageDialog(frame, str, "Sauvagarde", JOptionPane.ERROR_MESSAGE);
		}

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
		monDessin.pionCombo = engine.getPionCombo();
		monDessin.combo = engine.getComboList();
	}

	@Override
	public boolean demanderConfirmation(String question)
	{
		int res = 0;
		res = JOptionPane.showConfirmDialog(this.frame, question);
		return res == JOptionPane.YES_OPTION;
	}

	@Override
	public boolean demanderSauvegarde()
	{
		int res = JOptionPane.showConfirmDialog(frame, "Voulez vous sauvegarder avant de quitter", "Sauvegarder avant de quiiter", JOptionPane.YES_NO_CANCEL_OPTION);
		if (res == JOptionPane.YES_OPTION)
		{
			JFileChooser save = new JFileChooser();
			save.showSaveDialog(frame);
			if (save.getSelectedFile() != null)
				engine.sauvegarderPartie(save.getSelectedFile().getAbsolutePath());
			return true;
		} else if (res == JOptionPane.NO_OPTION)
			return true;
		else
			return false;
	}

	@Override
	public void afficherMessage(String str)
	{
		JOptionPane.showMessageDialog(frame, str);

	}

	public static ImagePanel getPanelAccueil()
	{
		return panelAccueil;
	}

	public static void setPanelAccueil(ImagePanel panelAccueil)
	{
		Fenetre.panelAccueil = panelAccueil;
	}

}

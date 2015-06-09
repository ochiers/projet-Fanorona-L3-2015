package engine;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import network.NetworkManager;
import network.NetworkPlayer;
import network.RequestType;
import AI.*;
import IHM.Affichage;

/**
 * @author ochiers Definit un moteur pour le jeu
 * 
 * @author soulierc
 *
 */
public class Engine implements EngineServices {

	/**
	 * Booleen permettant de connaitre l'etat courant du jeu. A savoir s'il est
	 * en pause ou non
	 */
	public boolean			gameInProgress;

	/**
	 * C'est la partie sur laquelle on joue. C'est cette partie qui est modifiée
	 * par le mouvement des pions.
	 */
	private Game			partieCourante;

	/**
	 * Affichage utilisé par la partie courante (Graphique ou non)
	 */
	public Affichage		affichage;

	/**
	 * Module pour annuler/refaire un ou plusieurs coups
	 */
	private UndoRedo<Game>	undoRedo;

	/**
	 * Booleen permettant d'eviter que la partie ne soit en pause au lancement
	 */
	public boolean			premierJeu;
	/**
	 * Le gestionnaire du reseau
	 */
	public NetworkManager	networkManager;

	public Engine() {
		this.gameInProgress = false;
		this.undoRedo = new UndoRedo<Game>();
		this.premierJeu = true;
	}

	@Override
	public void begin() {
		try {
			while (true) // On suppose que c'est l'IHM qui tue le thread
							// principal
			{
				while (!gameInProgress) {
					// System.out.print("Attente d'une partie");
					Thread.sleep(500);
				}
				partieCourante.pause();
				if (premierJeu)
					partieCourante.reprendre();
				premierJeu = false;
				partieCourante.commencer();
				if (partieCourante.finish)
					gameInProgress = false;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Methode permettant de changer de joueur en cours de partie
	 * 
	 * @param partie
	 *            Partie sur laquelle on joue et que l'on veut modifiee
	 * @param joueurBlanc
	 *            Joueur blanc
	 * @param joueurNoir
	 *            Joueur noir
	 * @param jCourant
	 *            Joueur dont c'est le tour
	 */
	private void changerPartieCourante(Game partie, Player joueurBlanc, Player joueurNoir, Pion jCourant) {
		if (partieCourante != null) {
			gameInProgress = false;
			partieCourante.finir(true); // On arrete la partie courante qui se
			// deroule
			// dans le thread principal

			boolean nouvJoueurs = (joueurBlanc == null && joueurNoir == null);
			if (nouvJoueurs) {
				joueurBlanc = partieCourante.joueurBlanc;
				joueurNoir = partieCourante.joueurNoir;

			}
			partieCourante = partie;

			/*
			 * On récupere le coup d'avant (partie précédente) et parametre
			 * correctement
			 */
			if (nouvJoueurs) {
				switch (Tools.getTypeOfPlayer(joueurBlanc)) {
					case Humain:
						partieCourante.joueurBlanc = new HumanPlayer(joueurBlanc);
						break;
					case IAFacile:
						partieCourante.joueurBlanc = new EasyAI(this, true, joueurBlanc.name);
						break;
					case IAMoyenne:
						partieCourante.joueurBlanc = new MediumAI(this, true, joueurBlanc.name);
						break;
					case IADifficile:
						partieCourante.joueurBlanc = new HardAI(this, true, joueurBlanc.name);
						break;
					case Reseau:
						partieCourante.joueurBlanc = new NetworkPlayer(this, false, joueurBlanc.name);
						break;
				}
				switch (Tools.getTypeOfPlayer(joueurNoir)) {
					case Humain:
						partieCourante.joueurNoir = new HumanPlayer(joueurNoir);
						break;
					case IAFacile:
						partieCourante.joueurNoir = new EasyAI(this, true, joueurNoir.name);
						break;
					case IAMoyenne:
						partieCourante.joueurNoir = new MediumAI(this, true, joueurNoir.name);
						break;
					case IADifficile:
						partieCourante.joueurNoir = new HardAI(this, true, joueurNoir.name);
						break;
					case Reseau:
						partieCourante.joueurNoir = new NetworkPlayer(this, false, joueurNoir.name);
						break;
				}
			} else {
				partieCourante.joueurNoir = joueurNoir;
				partieCourante.joueurBlanc = joueurBlanc;
			}
			partieCourante.joueurCourant = (jCourant == Pion.Blanc) ? partieCourante.joueurBlanc : partieCourante.joueurNoir;
			System.out.println("Partie créée avec " + partieCourante.joueurBlanc + " " + partieCourante.joueurNoir + " " + partieCourante.joueurCourant);
			partieCourante.finish = false;
			partieCourante.stopped = false;
			partieCourante.leMoteur = this;
			partieCourante.pause();
			/*
			 * try { Thread.sleep(500); } catch (InterruptedException e) {
			 * e.printStackTrace(); }
			 */
			gameInProgress = true;
			affichage.afficherJeu();
		} else {
			partieCourante = partie;
		}
	}

	@Override
	public void nouvellePartie(Player p1, Player p2, int premierJoueur, Dimension size) {
		Game g = new Game(this, premierJoueur, p1, p2, size);

		this.premierJeu = true;
		changerPartieCourante(g, p1, p2, (premierJoueur == 0) ? Pion.Blanc : Pion.Noir);

		this.undoRedo.vider();
		this.undoRedo.addItem(new Game(partieCourante));
		this.gameInProgress = true;
	}

	@Override
	public void stopper() {
		if (partieCourante != null) {
			this.partieCourante.finir(true);
			this.gameInProgress = false;
		}
	}

	@Override
	public void annuler(boolean notifReseau) {
		if (undoRedo.canUndo()) {
			if (this.networkManager != null && notifReseau) {
				networkManager.demanderConfirmation(RequestType.DemanderConfirmationAnnuler);
				int r = networkManager.getConfirmation();
				while (r == -1)
					try {
						r = networkManager.getConfirmation();
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (r == 0)
					return;
				networkManager.sendRequete(RequestType.Annuler);
			}
			changerPartieCourante(this.undoRedo.undo(), null, null, (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Noir : Pion.Blanc);
		}
	}

	@Override
	public void refaire(boolean notifReseau) {
		if (undoRedo.canRedo()) {
			if (this.networkManager != null && notifReseau) {
				networkManager.demanderConfirmation(RequestType.DemanderConfirmationRefaire);
				int r = networkManager.getConfirmation();
				while (r == -1)
					try {
						r = networkManager.getConfirmation();
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (r == 0)
					return;

				networkManager.sendRequete(RequestType.Refaire);
			}
			changerPartieCourante(this.undoRedo.redo(), null, null, (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Noir : Pion.Blanc);
		}
	}

	@Override
	public boolean peutAnnuler() {
		return undoRedo.canUndo();
	}

	@Override
	public boolean peutRefaire() {
		return undoRedo.canRedo();
	}

	@Override
	public void sauvegarderPartie(String path) {
		File f = new File(path);
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));

			out.writeObject(partieCourante);
			System.out.println("Partie sauvegardee");
			this.affichage.sauvegardeReussie(true);
			out.close();

		} catch (IOException e) {
			this.affichage.sauvegardeReussie(false);
			e.printStackTrace();
		}

	}

	@Override
	public void chargerPartie(String path) {
		File fichier = new File(path);

		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(fichier));
			Game g = (Game) ois.readObject();
			g.leMoteur = this;
			g.combo = new ArrayList<Case>();
			g.joueurBlanc.leMoteur = this;
			g.joueurNoir.leMoteur = this;
			g.joueurCourant.leMoteur = this;
			this.undoRedo.vider();
			this.undoRedo = g.annulerRefaire;
			Pion Jcourant = (g.joueurBlanc == g.joueurCourant) ? Pion.Blanc : Pion.Noir;
			changerPartieCourante(g, g.joueurBlanc, g.joueurNoir, Jcourant);
		} catch (Exception e) {
			this.affichage.chargementReussi(false);
			e.printStackTrace();
		}
	}

	@Override
	public void setDisplay(Affichage display) {
		this.affichage = display;

	}

	@Override
	public Game getCurrentGame() {
		return this.partieCourante;
	}

	@Override
	public Case[][] getPlateau() {
		return this.partieCourante.matricePlateau;
	}

	@Override
	public Player getJoueurBlanc() {
		return this.partieCourante.joueurBlanc;
	}

	@Override
	public Player getJoueurNoir() {

		return this.partieCourante.joueurNoir;
	}

	@Override
	public Player getWinner() {
		return this.partieCourante.getWinner();
	}

	@Override
	public int getNombrePionsBlancs() {
		return this.partieCourante.nombrePionBlanc;
	}

	@Override
	public int getNombrePionsNoirs() {
		return this.partieCourante.nombrePionNoir;
	}

	@Override
	public Case getPionCombo() {
		return this.partieCourante.pionCombo;
	}

	@Override
	public ArrayList<Case> getComboList() {
		return this.partieCourante.combo;
	}

	@Override
	public boolean enCombo() {
		return this.partieCourante.enCombo;
	}

	@Override
	public boolean isGamePaused() {
		return this.partieCourante.isPaused();
	}

	@Override
	public boolean isGameStopped() {
		return this.partieCourante.stopped;
	}

	@Override
	public void finirSonTour(boolean notifReseau) {
		if (this.networkManager != null && notifReseau) {
			networkManager.sendRequete(RequestType.FinDuTour);
		}
		this.partieCourante.finirSonTour();
		this.affichage.afficherJeu();
	}

	@Override
	public void pause() {
		this.partieCourante.pause();
		this.affichage.afficherJeu();
	}

	@Override
	public void reprendre() {
		this.partieCourante.reprendre();
		this.affichage.afficherJeu();
	}

	@Override
	public Player getJoueurCourant() {
		return this.partieCourante.joueurCourant;
	}

	@Override
	public boolean getPremierJoueur() {
		return this.partieCourante.premierJoueur;
	}

	@Override
	public void changerLesJoueurs(Player nouveauJBlanc, Player nouveauJNoir) {
		Game partie = new Game(partieCourante);
		if (getJoueurBlanc() == getJoueurCourant())
			partie.joueurCourant = nouveauJBlanc;
		else
			partie.joueurCourant = nouveauJNoir;
		partie.joueurBlanc = nouveauJBlanc;
		partie.joueurNoir = nouveauJNoir;
		System.out.println(partieCourante.joueurBlanc + " en  " + partie.joueurBlanc);
		System.out.println(partieCourante.joueurNoir + " en  " + partie.joueurNoir);
		changerPartieCourante(partie, partie.joueurBlanc, partie.joueurNoir, ((partie.joueurBlanc == partie.joueurCourant) ? Pion.Blanc : Pion.Noir));

	}

	@Override
	public void hebergerPartie(int portEcoute) throws Exception {
		if (this.networkManager != null) {
			try {
				this.networkManager.terminerPartieReseau();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		this.networkManager = new NetworkManager(this, portEcoute, null);
		this.networkManager.hebergerPartie();
		this.networkManager.start();

	}

	@Override
	public void rejoindrePartie(int port, String ip) throws IOException {
		if (this.networkManager != null) {
			this.networkManager.terminerPartieReseau();
		}
		this.networkManager = new NetworkManager(this, port, ip);
		this.networkManager.rejoindrePartie();
		this.networkManager.start();

	}

	@Override
	public NetworkManager getNetworkManager() {
		return this.networkManager;
	}

	@Override
	public Affichage getCurrentDisplay() {
		return this.affichage;
	}

	@Override
	public void envoyerCoupSurReseau(Coup coupJoue) {
		if (this.networkManager != null && !(this.getJoueurCourant() instanceof NetworkPlayer)) {
			System.out.println("envoye de " + coupJoue);
			this.networkManager.setCoupAEnvoyer(coupJoue);

		}
	}

	@Override
	public void envoyerChoixCaseSurReseau(Coordonnee coordonneeJouee) {
		if (this.networkManager != null && !(this.getJoueurCourant() instanceof NetworkPlayer)) {
			System.out.println("envoye de " + coordonneeJouee);
			this.networkManager.setCoordoneeAEnvoyer(coordonneeJouee);

		}
	}

	@Override
	public void quitter() {
		if (!this.affichage.demanderSauvegarde())
			return;

		if (getNetworkManager() != null)
			getNetworkManager().sendRequete(RequestType.Quitter);
		this.stopper();
		try {
			if (getNetworkManager() != null)
				getNetworkManager().terminerPartieReseau();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.flush();
		System.err.flush();
		System.out.println("~~~~~ Application terminee ~~~~~");
		System.exit(0);
	}

	@Override
	public void playOnlyOnce() {
		try {
			while (!gameInProgress)
				Thread.sleep(60);
			partieCourante.reprendre();
			partieCourante.commencer();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public UndoRedo<Game> getUndoRedo() {
		return this.undoRedo;
	}

	@Override
	public void deleteNetworkManager() {
		this.networkManager = null;

	}

	@Override
	public void recommencer(boolean notifReseau) {
		if (this.networkManager != null && notifReseau) {
			networkManager.demanderConfirmation(RequestType.DemanderConfirmationRecommencer);
			int r = networkManager.getConfirmation();
			while (r == -1)
				try {
					r = networkManager.getConfirmation();
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			if (r == 0)
				return;
			networkManager.sendRequete(RequestType.Recommencer);
		}
		Player p1 = Tools.createPlayer(this, Tools.getTypeOfPlayer((this.getJoueurBlanc())), this.getJoueurBlanc().name);
		Player p2 = Tools.createPlayer(this, Tools.getTypeOfPlayer((this.getJoueurNoir())), this.getJoueurNoir().name);
		nouvellePartie(p1, p2, getCurrentGame().premierJoueur ? 0 : 1, new Dimension(9, 5));
	}

}

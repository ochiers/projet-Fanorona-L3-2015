package engine;

import java.awt.Dimension;
import java.util.ArrayList;

import IHM.Affichage;


/**
 * Defini les services et les donnes que propose le moteur
 * @author soulierc
 *
 */
public interface Polling {


	/**
	 * Attache le module d'affichage au moteur
	 * @param display : Le module d'affichage
	 */
	public void setDisplay(Affichage display);
	
	/**
	 * Demarre le moteur et attend qu'une partie soit créée (appel à nouvellepartie())
	 */
	public void begin();
	
	/**
	 * Donne la partie en cour
	 * @return La partie courante
	 */
	public Game getCurrentGame();
	
	/**
	 * Donne le plateau du jeu
	 * @return Le plateau du jeu de la partie courante
	 */
	public Case[][] getPlateau();

	/**
	 * Donne le joueur blanc de la partie courante
	 * @return Le joueur blanc
	 */
	public Player getJoueurBlanc();
	
	/**
	 * Donne le joueur noir de la partie courante
	 * @return Le joueur noir
	 */
	public Player getJoueurNoir();
	
	/**
	 * Donne le joueur qui a gagné la partie
	 * @return Le joueur gagnant, null si pas de gagnant
	 */
	public Player getWinner();

	/**
	 * Donne le nombre de pions du joueur blanc qu'il reste sur le plateau
	 * @return	Le nombre de pions blancs
	 */
	public int getNombrePionsBlancs();
	
	/**
	 * Donne le nombre de pions du joueur noir qu'il reste sur le plateau
	 * @return	Le nombre de pions noirs
	 */
	public int getNombrePionsNoirs();
	
	/**
	 * Donne le pion qui est en train de faire un combo
	 * @return Le case du pion qui fait le combo
	 */
	public Case getPionCombo();
	
	/**
	 * Donne la liste des cases par où le pion du combo est passé
	 * @return Une liste de case deja traversées
	 */
	public ArrayList<Case>	getComboList();
	
	/**
	 * Indique si un combo est en cours
	 * @return
	 */
	public boolean enCombo();
	
	/**
	 * Indique si la partie est en pause
	 * @return
	 */
	public boolean isGamePaused();
	
	/**
	 * Indique si la partie est arretee
	 * @return
	 */
	public boolean isGameStopped();
	
	/**
	 * Indique si on peut annuler le demi-coup precedent
	 * @return
	 */
	public boolean peutAnnuler();
	
	/**
	 * Indique si on peut refaire le demi-coup precedement annulé
	 * @return
	 */
	public boolean peutRefaire();

	/**
	 * Termine le tour du joueur courant, uniquement possible pendant un combo
	 */
	public void finirSonTour();

	/**
	 * Met en pause la partie
	 */
	public void pause();
	
	/**
	 * Reprend la partie en pause
	 */
	public void reprendre();
	
	/**
	 * Annule un demi-coup, la partie est en pause apres cette action
	 */
	public void annuler();
	
	/**
	 * Refait un demi-coup deja annulé, la partie est en pause apres cette action
	 */
	public void refaire();
	
	/**
	 * Arrete la partie courante, le moteur sera en attente d'une nouvelle partie
	 */
	public void stopper();
	
	/**
	 * Cree une nouvelle partie
	 * @param p1 Le joueur blanc
	 * @param p2 Le joueur noir
	 * @param size La taille du plateau (9X5 ou 5X5)
	 */
	public void nouvellePartie(Player p1, Player p2, Dimension size);
	
	/**
	 * Sauvegarde la partie courante dans le fichier de chemin path
	 * @param path Le chemin du fichier de sauvegarde (créé s'il n'existe pas)
	 */
	public void sauvegarderPartie(String path);
	
	/**
	 * Charge la partie sauvegardée dans le fichier de chemin path
	 * La partie courante est detruite
	 * @param path Le chemin du fichier a charger
	 */
	public void chargerPartie(String path);
	
}

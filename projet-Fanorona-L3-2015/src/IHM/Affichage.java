package IHM;

import java.util.ArrayList;

import engine.Case;
import engine.Player;

public interface Affichage {

	/**
	 * Affiche l'etat courant du jeu
	 */
	void afficherJeu();
	
	/**
	 *	Affiche la victoire d'un des deux joueurs 
	 * @param p Le joueur victorieux
	 */
	void afficherVictoire(Player p);
	
	/**
	 * Affiche les pions qui peuvent jouer
	 * @param l
	 */
	void afficherPionsPossibles(ArrayList<Case> l);
}

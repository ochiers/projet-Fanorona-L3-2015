package IHM;

import java.util.ArrayList;

import engine.Case;
import engine.Player;

public interface Affichage {

	/**
	 * Affiche l'etat courant du jeu
	 */
	public void afficherJeu();

	/**
	 * Affiche la victoire d'un des deux joueurs
	 * 
	 * @param p
	 *            Le joueur victorieux
	 */
	public void afficherVictoire(Player p);

	/**
	 * Affiche les pions qui peuvent jouer
	 * 
	 * @param l
	 */
	public void afficherPionsPossibles(ArrayList<Case> l);

	/**
	 * Affiche le pions qui fait le combo
	 * 
	 * @param pionCourant
	 */
	public void afficherPionDuCombo(Case pionCourant);

	/**
	 * Affiche le choix que doit faire l'utilisateur pour manger
	 * 
	 * @param l1
	 * @param l2
	 */
	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2);

	/**
	 * Affiche le chemin qu'a fait le pion pendant son combo
	 * 
	 * @param combo
	 */
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo);
}

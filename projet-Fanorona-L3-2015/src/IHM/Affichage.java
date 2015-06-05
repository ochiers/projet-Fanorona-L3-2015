package IHM;

import java.util.ArrayList;

import engine.Case;
import engine.Coup;
import engine.Player;

/**
 * Interface definissant les services que doit fournir en affichage quelconque pour faire fonctionner le moteur 
 * @author soulierc
 *
 */
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

	/**
	 * Affiche un message a l'utilisateur qui indique si la sauvegarde de la partie a reussie ou non
	 * @param reussi : Indique si la sauvegarde a reussi
	 */
	public void sauvegardeReussie(boolean reussi);
	
	/**
	 * Affiche un message a l'utilisateur qui indique si le chargement de la partie a reussie ou non
	 * @param reussi : Indique si le chargement a reussi
	 */
	public void chargementReussi(boolean reussi);
	
	
	/**
	 * Affiche le coup qui vient d'etre joue et qui est valide
	 * @param c Le coup joue
	 */
	public void afficherCoupJoue(Coup c);
	
	/**
	 * Affiche les pions qui ont ete captures par le coup effectue
	 * @param list La liste des pions captures
	 */
	public void afficherPionsCaptures(ArrayList<Case> list);
	
	public boolean demanderConfirmation(String question);
	
	public void demanderSauvegarde();
}

package AI;

import engine.Case;
import engine.Game;

public class Noeud {
	public Case[][] plateau;
	public int nbPionsJoueur;
	public int nbPionsAdversaire;
	
	public Noeud(Noeud n){
		this.plateau = new Case[n.plateau.length][n.plateau[0].length];
		for(int i = 0; i < n.plateau[0].length; i++){
			for (int j = 0; j < n.plateau.length; j++){
				this.plateau [j][i] = n.plateau[j][i];
			}
		}
		this.nbPionsJoueur = n.nbPionsJoueur;
		this.nbPionsAdversaire = n.nbPionsAdversaire;
	}
	
	public Noeud(Game g) {
		/* ATTENTION : INVERSION i ET j */
		this.plateau = new Case[g.matricePlateau.length][g.matricePlateau[0].length];
		for(int i = 0; i < g.matricePlateau[0].length; i++) {
			for (int j = 0; j < g.matricePlateau.length; j++){
				this.plateau [j][i] = g.matricePlateau[j][i];
			}
		}
		if(g.joueurCourant == g.joueurBlanc) {
			this.nbPionsJoueur = g.nombrePionBlanc;
			this.nbPionsAdversaire = g.nombrePionNoir;
		}
		else {
			this.nbPionsJoueur = g.nombrePionNoir;
			this.nbPionsAdversaire = g.nombrePionBlanc;
		}
	}
}

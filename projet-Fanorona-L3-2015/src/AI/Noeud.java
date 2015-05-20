package AI;

import engine.Case;
import engine.Game;

public class Noeud {
	public Case[][] plateau;
	public int nbPionsJoueur;
	public int nbPionsAdversaire;
	
	public Noeud(Noeud n){
		this.plateau = new Case[n.plateau[0].length][n.plateau.length];
		for(int i = 0; i < n.plateau[0].length; i++){
			for (int j = 0; j < n.plateau.length; j++){
				this.plateau [i][j] = n.plateau[i][j];
			}
		}
		this.nbPionsJoueur = n.nbPionsJoueur;
		this.nbPionsAdversaire = n.nbPionsAdversaire;
	}
	
	public Noeud(Game g) {
		this.plateau = new Case[g.matricePlateau[0].length][g.matricePlateau.length];
		for(int i = 0; i < g.matricePlateau[0].length; i++){
			for (int j = 0; j < g.matricePlateau.length; j++){
				this.plateau [i][j] = g.matricePlateau[i][j];
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

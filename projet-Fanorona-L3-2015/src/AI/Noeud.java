package AI;

import engine.Case;
import engine.Game;

public class Noeud {
	public Case[][] plateau;
	public int nbPionsJoueur;
	public int nbPionsAdversaire;
	
	public Noeud(Noeud n){
		this.plateau = Game.copyMatrice(n.plateau);
		this.nbPionsJoueur = n.nbPionsJoueur;
		this.nbPionsAdversaire = n.nbPionsAdversaire;
	}
	
	public Noeud(Game g) {
		this.plateau = Game.copyMatrice(g.matricePlateau);
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

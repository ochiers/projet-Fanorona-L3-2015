package IHM;

import java.util.ArrayList;

import engine.Case;

public interface Affichage {

	void afficherJeu();
	void afficherVictoire();
	void afficherPionsPossibles(ArrayList<Case> l);
}

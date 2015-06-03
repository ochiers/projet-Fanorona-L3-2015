package AI;

import java.util.ArrayList;

import engine.Case;
import engine.Coup;
import engine.Player;
import IHM.Affichage;

/* 
 * Implémentation de l'interface Affichage ne faisant rien (utilisée pour lancer un grand nombre de parties afin d'obtenir des stats)
 */
public class AffichageVide implements Affichage {

	@Override
	public void afficherJeu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherVictoire(Player p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherPionsPossibles(ArrayList<Case> l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherPionDuCombo(Case pionCourant) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherMultiDirections(ArrayList<Case> l1, ArrayList<Case> l2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherCheminParcouruParleCombo(ArrayList<Case> combo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sauvegardeReussie(boolean reussi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chargementReussi(boolean reussi) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherCoupJoue(Coup c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afficherPionsCaptures(ArrayList<Case> list) {
		// TODO Auto-generated method stub
		
	}

}

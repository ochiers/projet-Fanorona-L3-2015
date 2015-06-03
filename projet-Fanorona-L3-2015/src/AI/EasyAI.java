package AI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import engine.*;

public class EasyAI extends Player implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Case> premieresCasesPrises; /* La première case prise par chaque coup stocké dans la liste des coups qui capturent */
	Case choix; /* La première case qu'on capture avec le coup choisi à la fin de la méthode play */

	public EasyAI(EngineServices moteur, boolean isAI, String name)
	{
		super(moteur, isAI, name);
		premieresCasesPrises = new ArrayList<Case>();
		choix = null;
	}
	
	public ArrayList<Case> voisins(Case c)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if(c.nord != null && c.nord.pion == null)
			res.add(c.nord);
		if(c.nordEst != null && c.nordEst.pion == null)
			res.add(c.nordEst);
		if(c.est != null && c.est.pion == null)
			res.add(c.est);
		if(c.sudEst != null && c.sudEst.pion == null)
			res.add(c.sudEst);
		if(c.sud != null && c.sud.pion == null)
			res.add(c.sud);
		if(c.sudOuest != null && c.sudOuest.pion == null)
			res.add(c.sudOuest);
		if(c.ouest != null && c.ouest.pion == null)
			res.add(c.ouest);
		if(c.nordOuest != null && c.nordOuest.pion == null)
			res.add(c.nordOuest);
		return res;
	}
	
	public static Direction determinerDirection(Coordonnee depart, Coordonnee arrivee)
	{
		int deplacementColonne = arrivee.colonne - depart.colonne;
		int deplacementLigne = arrivee.ligne - depart.ligne;
		switch (deplacementColonne)
		{
			case -1:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.NordOuest;
					case 0:
						return Direction.Ouest;
					case 1:
						return Direction.SudOuest;
				}
				break;
			case 0:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.Nord;
					case 1:
						return Direction.Sud;
				}
				break;
			case 1:
				switch (deplacementLigne)
				{
					case -1:
						return Direction.NordEst;
					case 0:
						return Direction.Est;
					case 1:
						return Direction.SudEst;
				}
				break;
		}
		System.err.println("GROS SOUCI ! IMPOSSIBLE DE DETERMINER LA DIRECTION DU COUP : Depart : " + depart + ", arrivee : " + arrivee);
		return null;
	}
	
	public ArrayList<Coup> creerCoups(Case[] listeCases){
		ArrayList<Coup> listeCoups = new ArrayList<Coup>();
		for(int i =0; i < listeCases.length; i++){
			ArrayList<Case> voisins = voisins(listeCases[i]);
			for(int j = 0; j < voisins.size(); j++){
				listeCoups.add(new Coup(listeCases[i].position, voisins.get(j).position));
			}
		}
		return listeCoups;
	}
	
	public Pion inversePion(Pion p){
		if(p==Pion.Noir)
			return Pion.Blanc;
		else return Pion.Noir;
	}
	
	public boolean coupPerdantPercussion(Case c, Pion couleurAdversaire){
		if((c.nord != null && c.nord.estVide() && c.nord.nord != null && c.nord.nord.pion == couleurAdversaire) ||
				(c.nordOuest != null && c.nordOuest.estVide() && c.nordOuest.nordOuest != null && c.nordOuest.nordOuest.pion == couleurAdversaire) ||
				(c.ouest != null && c.ouest.estVide() && c.ouest.ouest != null && c.ouest.ouest.pion == couleurAdversaire) ||
				(c.sudOuest != null && c.sudOuest.estVide() && c.sudOuest.sudOuest != null && c.sudOuest.sudOuest.pion == couleurAdversaire) ||
				(c.sud != null && c.sud.estVide() && c.sud.sud != null && c.sud.sud.pion == couleurAdversaire) ||
				(c.sudEst != null && c.sudEst.estVide() && c.sudEst.sudEst != null && c.sudEst.sudEst.pion == couleurAdversaire) ||
				(c.est != null && c.est.estVide() && c.est.est != null && c.est.est.pion == couleurAdversaire) ||
				(c.nordEst != null && c.nordEst.estVide() && c.nordEst.nordEst != null && c.nordEst.nordEst.pion == couleurAdversaire)
				){
			return true;
		}
		return false;
	}
	
	public boolean coupPerdantAspiration(Case c, Pion couleurAdversaire){
		if((c.nord != null && c.nord.pion == couleurAdversaire && c.nord.nord != null && c.nord.nord.estVide()) ||
				(c.nordOuest != null && c.nordOuest.pion == couleurAdversaire && c.nordOuest.nordOuest != null && c.nordOuest.nordOuest.estVide()) ||
				(c.ouest != null && c.ouest.pion == couleurAdversaire && c.ouest.ouest != null && c.ouest.ouest.estVide()) ||
				(c.sudOuest != null && c.sudOuest.pion == couleurAdversaire && c.sudOuest.sudOuest != null && c.sudOuest.sudOuest.estVide()) ||
				(c.sud != null && c.sud.pion == couleurAdversaire && c.sud.sud != null && c.sud.sud.estVide()) ||
				(c.sudEst != null && c.sudEst.pion == couleurAdversaire && c.sudEst.sudEst != null && c.sudEst.sudEst.estVide()) ||
				(c.est != null && c.est.pion == couleurAdversaire && c.est.est != null && c.est.est.estVide()) ||
				(c.nordEst != null && c.nordEst.pion == couleurAdversaire && c.nordEst.nordEst != null && c.nordEst.nordEst.estVide())
				){
			return true;
		}
		return false;
	}
	
	@Override
	public Coup play(Case[][] laMatrice, Case[] listeCases)
	{
		try { /* Sleep pour pouvoir visualiser les coups de l'IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		ArrayList<Coup> listeCaptures = new ArrayList<Coup>();
		ArrayList<Coup> listeCoupsNonPerdants = new ArrayList<Coup>();
		Pion couleurJoueur = (leMoteur.getCurrentGame().joueurCourant == leMoteur.getCurrentGame().joueurBlanc) ? Pion.Blanc : Pion.Noir;
		Pion couleurAdversaire = inversePion(couleurJoueur);
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < listeCoups.size(); i++){
			Coup coupCourant = listeCoups.get(i);
			Case depart = leMoteur.getCurrentGame().matricePlateau[coupCourant.depart.ligne][coupCourant.depart.colonne];
			Case arrivee = leMoteur.getCurrentGame().matricePlateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
			Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee);
			Direction opposeDirectionCoup = Direction.oppose(directionCoup);
			Case premiereCaseAspiration = depart.getCaseAt(opposeDirectionCoup);
			Case premiereCasePercussion = arrivee.getCaseAt(directionCoup);
			/* Si le coup permet une capture par aspiration, on l'ajoute à la liste des captures */
			if(premiereCaseAspiration != null && !premiereCaseAspiration.estVide() && premiereCaseAspiration.pion != couleurJoueur){
				listeCaptures.add(coupCourant);
				premieresCasesPrises.add(premiereCaseAspiration);
			}
			/* Si le coup permet une capture par percussion, on l'ajoute à la liste des captures */
			if(premiereCasePercussion != null && !premiereCasePercussion.estVide() && premiereCasePercussion.pion != couleurJoueur){
				listeCaptures.add(coupCourant);
				premieresCasesPrises.add(premiereCasePercussion);
			}
			/* Si le coup ne sacrifie aucun pion, on l'ajoute à la liste des coups dits "non perdants" */
			if(!(coupPerdantPercussion(arrivee, couleurAdversaire) || coupPerdantAspiration(arrivee, couleurAdversaire))){
				listeCoupsNonPerdants.add(coupCourant);
			}
		}
		if(listeCaptures.size() > 0){ 		/* Si il y a des coups qui réalisent une capture, on joue un de ces coups, choisi de façon aléatoire selon une loi uniforme */
			int coupChoisi = r.nextInt(listeCaptures.size());
			choix = premieresCasesPrises.get(coupChoisi);
//			System.out.println(choix);
			premieresCasesPrises.clear();
			return listeCaptures.get(coupChoisi);
		}
		else { 								/* Si il n'y a pas de coup qui réalise une capture, on joue un coup non perdant (s'il existe), choisi de façon aléatoire selon une loi uniforme */
			if(listeCoupsNonPerdants.size() > 0){
				return listeCoupsNonPerdants.get(r.nextInt(listeCoupsNonPerdants.size()));
			}
			else return listeCoups.get(r.nextInt(listeCoups.size()));
		}
	}

	@Override
	public String getNiveau() {
		return "IA Facile";
	}

	@Override
	public Case choisirDirectionAManger(ArrayList<Case> rapprochement,
			ArrayList<Case> eloignement) {
		System.out.println(choix);
		return choix;
	}

	@Override
	public Player clone()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}

package AI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Stack;

import engine.*;

public class HardAI extends Player implements Serializable {
	/**
	 * 
	 */
	private static final int profondeurBis = 8;
	private static final long serialVersionUID = 1L;
	private Case[][] matrice;
	Stack<DemiCoup> pileCoups;
	public int nbPionsJoueur;
	public int nbPionsAdversaire;
	public int profondeurCourante;
	
	ArrayList<Case> premieresCasesPrises; /* La première case prise par chaque coup stocké dans la liste des meilleurs coups */
	Case choix; /* La première case qu'on capture avec le coup choisi à la fin de la méthode play */

	public HardAI(EngineServices moteur, boolean isAI, String name)
	{
		super(moteur, isAI, name);
		premieresCasesPrises = new ArrayList<Case>();
		nbPionsAdversaire = 22;
		nbPionsJoueur = 22;
		pileCoups = new Stack<DemiCoup>();
		profondeurCourante = 5;
	}
	
	public int eval(int profondeur, boolean noeudMin, Pion couleurJoueur) {
//		Case caseCourante = this.matrice[0][0];
//		Case caseCourante2 = this.matrice[4][0];
//		int nbPionsBord = 0;
//		for(int i = 0; i<this.matrice[0].length; i++) {
//			if(caseCourante.pion == couleurJoueur)
//				nbPionsBord++;
//			if(caseCourante2.pion == couleurJoueur)
//				nbPionsBord++;
//			caseCourante2 = caseCourante2.est;
//			caseCourante = caseCourante.est;
//		}
//		caseCourante = this.matrice[1][0];
//		caseCourante2 = this.matrice[1][8];
//		for(int i = 0; i<3; i++) {
//			if(caseCourante.pion == couleurJoueur)
//				nbPionsBord++;
//			if(caseCourante2.pion == couleurJoueur)
//				nbPionsBord++;
//			caseCourante2 = caseCourante2.sud;
//			caseCourante = caseCourante.sud;
//		}
		
//		if(profondeurCourante == profondeurBis){
//			if(noeudMin)
//				return (nbPionsJoueur-nbPionsAdversaire)+ profondeur*-10 /*+ nbPionsBord/2*/;
//			else return (nbPionsJoueur-nbPionsAdversaire)+ profondeur*10 /*- nbPionsBord/2*/;
//		}
//		else 
			
			return (nbPionsJoueur-nbPionsAdversaire)*100;
	}
	
	public int alphaBeta(Case[] listeCases, int alpha, int beta, boolean noeudMin, int profondeur, Pion couleurJoueur, ArrayList<Case> combo, boolean comboEnCours) {
		int val = 0;
		ArrayList<Coup> listeCoups = creerCoups(listeCases, couleurJoueur);
		Pion couleurAdversaire = inversePion(couleurJoueur);
		if(profondeur == 0 || nbPionsAdversaire == 0 || nbPionsJoueur == 0) { /* Si on est sur une feuille ou qu'on a atteint la profondeur maximale */
			return eval(profondeur, noeudMin, couleurJoueur);
		}
		else if (noeudMin) { /* tour de l'adversaire */
			val = Integer.MAX_VALUE;
			Iterator<Coup> it = listeCoups.iterator();
			while(it.hasNext()) {
				Coup coupCourant = it.next();
				if(!coupImpossible(coupCourant, combo)) {
					/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
					Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */				
					ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur);
					ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, matrice[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur);
					int evaluationNbCapturesPercussion = pionsACapturerRapprochement.size();
					int evaluationNbCapturesAspiration = pionsACapturerEloignement.size();
					
					/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
					 * des deux captures est la meilleure */
					if(evaluationNbCapturesPercussion > 0 && evaluationNbCapturesAspiration > 0){						
						ArrayList<Case> comboPercussion = new ArrayList<Case>(combo);
						ArrayList<Case> comboAspiration = new ArrayList<Case>(combo);

						/* Modification plateau et appel récursif pour la capture par percussion */
						capturer(coupCourant, true, pionsACapturerRapprochement, couleurJoueur);
						comboPercussion.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
						Case[] listeCases2 = new Case[1];
						listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						int res1 = alphaBeta(listeCases2, alpha, beta, noeudMin, profondeur, couleurJoueur, comboPercussion, true);
						annuler(true, couleurJoueur);
						
						/* Modification plateau et appel récursif pour la capture par aspiration */
						capturer(coupCourant, true, pionsACapturerEloignement, couleurJoueur);
						comboAspiration.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
						Case[] listeCases3 = new Case[1];
						listeCases3[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						int res2 = alphaBeta(listeCases3, alpha, beta, noeudMin, profondeur, couleurJoueur, comboAspiration, true);
						annuler(true, couleurJoueur);
						
						val = java.lang.Math.min(val, java.lang.Math.min(res1, res2));
					}
					/* Sinon, on fait la seule capture réellement possible */
					else {
						ArrayList<Case> pionsJouables;
						ArrayList<Case> combo2 = new ArrayList<Case>(combo);
						if(evaluationNbCapturesPercussion > 0) {
							capturer(coupCourant, true, pionsACapturerRapprochement, couleurJoueur);
							Case[] listeCases2 = new Case[1];
							listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
							combo2.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
							val = java.lang.Math.min(val, alphaBeta(listeCases2, alpha, beta, noeudMin, profondeur, couleurJoueur, combo2, true));
							annuler(true, couleurJoueur);
						}
						else if (evaluationNbCapturesAspiration > 0) {
							capturer(coupCourant, true, pionsACapturerEloignement, couleurJoueur);
							Case[] listeCases2 = new Case[1];
							listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
							combo2.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
							val = java.lang.Math.min(val, alphaBeta(listeCases2, alpha, beta, noeudMin, profondeur, couleurJoueur, combo2, true));
							annuler(true, couleurJoueur);
						}
						else {
							if(!comboEnCours){
								capturer(coupCourant, true, new ArrayList<Case>(), couleurJoueur);
							}
							combo2.clear();
							pionsJouables = lesPionsJouables(couleurAdversaire);
							Case[] listeCases2 = new Case[pionsJouables.size()];
							val = java.lang.Math.min(val, alphaBeta(pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire, combo2, false));
							if(!comboEnCours){
								annuler(true, couleurJoueur);
							}	
						}
					}
				}
				else if(comboEnCours){
					ArrayList<Case> pionsJouables;
					ArrayList<Case> combo2 = new ArrayList<Case>();
					pionsJouables = lesPionsJouables(couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables.size()];
					val = java.lang.Math.min(val, alphaBeta(pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire, combo2, false));
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'IA */
			val = Integer.MIN_VALUE;
			Iterator<Coup> it = listeCoups.iterator();
			while(it.hasNext()) {
				Coup coupCourant = it.next();
				if(!coupImpossible(coupCourant, combo)) {
					/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
					Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
					ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur);
					ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, matrice[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur);
					int evaluationNbCapturesPercussion = pionsACapturerRapprochement.size();
					int evaluationNbCapturesAspiration = pionsACapturerEloignement.size();
					/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
					 * des deux captures est la meilleure */
					if(evaluationNbCapturesPercussion > 0 && evaluationNbCapturesAspiration > 0) {						
						ArrayList<Case> comboPercussion = new ArrayList<Case>(combo);
						ArrayList<Case> comboAspiration = new ArrayList<Case>(combo);
						
						/* Modification plateau et appel récursif pour la capture par percussion */
						capturer(coupCourant, false, pionsACapturerRapprochement, couleurJoueur);
						comboPercussion.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
						Case[] listeCases2 = new Case[1];
						listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						int res1 = alphaBeta(listeCases2, alpha, beta, noeudMin, profondeur, couleurJoueur, comboPercussion, true)+profondeur;
						annuler(false, couleurJoueur);
						
						/* Modification plateau et appel récursif pour la capture par aspiration */
						capturer(coupCourant, false, pionsACapturerEloignement, couleurJoueur);
						comboAspiration.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
						Case[] listeCases3 = new Case[1];
						listeCases3[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						int res2 = alphaBeta(listeCases3, alpha, beta, noeudMin, profondeur, couleurJoueur, comboAspiration, true)+profondeur;
						annuler(false, couleurJoueur);
						
						val = java.lang.Math.max(val, java.lang.Math.max(res1, res2));
						
					}
					/* Sinon, on fait la seule capture réellement possible */
					else {
						ArrayList<Case> pionsJouables;
						ArrayList<Case> combo2 = new ArrayList<Case>(combo);
						if(evaluationNbCapturesPercussion > 0) {
							capturer(coupCourant, false, pionsACapturerRapprochement, couleurJoueur);
							Case[] listeCases2 = new Case[1];
							listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
							combo2.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
							val = java.lang.Math.max(val, alphaBeta(listeCases2, alpha, beta, noeudMin, profondeur, couleurJoueur, combo2, true)+profondeur);
							annuler(false, couleurJoueur);
						}
						else if (evaluationNbCapturesAspiration > 0) {
							capturer(coupCourant, false, pionsACapturerEloignement, couleurJoueur);
							Case[] listeCases3 = new Case[1];
							listeCases3[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
							combo2.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
							val = java.lang.Math.max(val, alphaBeta(listeCases3, alpha, beta, noeudMin, profondeur, couleurJoueur, combo2, true)+profondeur);
							annuler(false, couleurJoueur);
						}
						else {
							if(!comboEnCours){
								capturer(coupCourant, false, new ArrayList<Case>(), couleurJoueur);
							}
							combo2.clear();
							pionsJouables = lesPionsJouables(couleurAdversaire);
							Case[] listeCases2 = new Case[pionsJouables.size()];
							val = java.lang.Math.max(val, alphaBeta(pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire, combo2, false));
							if(!comboEnCours){
								annuler(false, couleurJoueur);
							}
						}
					}
				}
				else if(comboEnCours){
					ArrayList<Case> pionsJouables;
					ArrayList<Case> combo2 = new ArrayList<Case>();
					pionsJouables = lesPionsJouables(couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables.size()];
					val = java.lang.Math.max(val, alphaBeta(pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire, combo2, false));
				}
				if(val >= beta) return val;
				alpha = java.lang.Math.max(alpha, val);
			}
		}
		return val;
	}
	
	public Pion inversePion(Pion p){
		if(p==Pion.Noir)
			return Pion.Blanc;
		else return Pion.Noir;
	}
	
	public Case[][] copiePlateau(Case[][] plateau) {
		Case[][] res = new Case[plateau[0].length][plateau.length];
		for(int i = 0; i < plateau[0].length; i++){
			for (int j = 0; j < plateau.length; j++){
				res[i][j] = plateau[i][j];
			}
		}
		return res;
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
	
	private int annuler(boolean tourAdversaire, Pion couleurJoueur)
	{
		DemiCoup coupAAnnuler = this.pileCoups.pop();
		Iterator<Case> it = coupAAnnuler.cases.iterator();
		Pion couleurAdversaire = inversePion(couleurJoueur);
		matrice[coupAAnnuler.deplacement.depart.ligne][coupAAnnuler.deplacement.depart.colonne].pion = couleurJoueur;
		matrice[coupAAnnuler.deplacement.arrivee.ligne][coupAAnnuler.deplacement.arrivee.colonne].pion = null;
		
		while (it.hasNext())
		{
			it.next().pion = couleurAdversaire;
			if (!tourAdversaire)
				nbPionsAdversaire++;
			else if(tourAdversaire)
				nbPionsJoueur++;
		}
		return coupAAnnuler.cases.size();
	}
	
	private int capturer(Coup coupCourant, boolean tourAdversaire, ArrayList<Case> l, Pion couleurJoueur)
	{
		Iterator<Case> it = l.iterator();
		matrice[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
		matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
		this.pileCoups.push(new DemiCoup(coupCourant, l));
		while (it.hasNext())
		{
			it.next().pion = null;
			if (!tourAdversaire)
				nbPionsAdversaire--;
			else if(tourAdversaire)
				nbPionsJoueur--;
		}
		return l.size();
	}
	
	private ArrayList<Case> determinerPionsACapturerRapprochement(Direction d, Case depart, Pion p)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;

		while (courante != null)
		{
			courante = courante.getCaseAt(d);
			if (courante != null && !courante.estVide() & courante.pion != p)
				res.add(courante);
			else
				break;
		}

		return res;
	}

	private ArrayList<Case> determinerPionsACapturerEloignement(Direction d, Case depart, Pion p)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;

		while (courante != null)
		{
			courante = courante.getCaseAt(Direction.oppose(d));

			if (courante != null && !courante.estVide() && courante.pion != p)
				res.add(courante);
			else
				break;
		}
		return res;
	}
	
	public ArrayList<Case> lesPionsJouables(Pion p)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		for (int i = 0; i < matrice[0].length; i++)
			for (int j = 0; j < matrice.length; j++)
			{
				if (matrice[j][i].pion == p)
				{
					for (Case case1 : matrice[j][i].voisins())
					{
						if (case1.estVide())
						{
							res.add(matrice[j][i]);
							break;
						}
					}
				}
			}
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
		System.err.println("GROS SOUCIS ! IMPOSSIBLE DE DETERMINEE LA DIRECTION DU COUP : Depart : " + depart + ", arrivee : " + arrivee);
		return null;
	}
	
	public ArrayList<Case> voisins(Case c)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if(c.nord != null && c.nord.estVide())
			res.add(c.nord);
		if(c.nordEst != null && c.nordEst.estVide())
			res.add(c.nordEst);
		if(c.est != null && c.est.estVide())
			res.add(c.est);
		if(c.sudEst != null && c.sudEst.estVide())
			res.add(c.sudEst);
		if(c.sud != null && c.sud.estVide())
			res.add(c.sud);
		if(c.sudOuest != null && c.sudOuest.estVide())
			res.add(c.sudOuest);
		if(c.ouest != null && c.ouest.estVide())
			res.add(c.ouest);
		if(c.nordOuest != null && c.nordOuest.estVide())
			res.add(c.nordOuest);
		return res;
	}
	
	public ArrayList<Coup> creerCoups(Case[] listeCases, Pion p){
		ArrayList<Coup> listeCoups = new ArrayList<Coup>();
		ArrayList<Coup> listeCaptures = new ArrayList<Coup>();
		for(int i =0; i < listeCases.length; i++){
			ArrayList<Case> voisins = voisins(listeCases[i]);
			Iterator<Case> it = voisins.iterator();
			while(it.hasNext()) {
				Coup c = new Coup(listeCases[i].position, it.next().position);
				listeCoups.add(c);
				Direction d = determinerDirection(c.depart, c.arrivee);
				Case prisePercussion = matrice[c.arrivee.ligne][c.arrivee.colonne].getCaseAt(d);
				Case priseAspiration = matrice[c.depart.ligne][c.depart.colonne].getCaseAt(Direction.oppose(d));
				if((prisePercussion != null && !prisePercussion.estVide() && prisePercussion.pion != p) || (priseAspiration != null && !priseAspiration.estVide() && priseAspiration.pion != p)){
					listeCaptures.add(c);
				}
			}
		}
		if (listeCaptures.size() > 0) return listeCaptures;
		return listeCoups;
	}
	
	public boolean coupImpossible(Coup c, ArrayList<Case> listeCasesInterdites){
		Iterator<Case> it = listeCasesInterdites.iterator();
		while(it.hasNext()){
			if(c.arrivee.equals(it.next().position)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Coup play(Case[][] laMatrice, Case[] listeCases)
	{		
		this.matrice = laMatrice;
		long tempsAvant = System.nanoTime();
		Game partieCourante = leMoteur.getCurrentGame();
		ArrayList<Coup> meilleurCoups = new ArrayList<Coup>();
		ArrayList<Coup> listeCoupsNonPerdants = new ArrayList<Coup>();
		ArrayList<Case> premieresCasesPrisesNonPerdants = new ArrayList<Case>();
		Pion couleurJoueur = (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Blanc : Pion.Noir;
		this.nbPionsJoueur = (couleurJoueur == Pion.Blanc) ? partieCourante.nombrePionBlanc : partieCourante.nombrePionNoir;
		this.nbPionsAdversaire = (couleurJoueur == Pion.Blanc) ? partieCourante.nombrePionNoir : partieCourante.nombrePionBlanc;
		if(this.nbPionsAdversaire < 8 || this.nbPionsJoueur < 8)
			profondeurCourante = profondeurBis;
		Pion couleurAdversaire = inversePion(couleurJoueur);
		ArrayList<Coup> listeCoups = creerCoups(listeCases, couleurJoueur);
		int meilleurRes = Integer.MIN_VALUE;
		int res = 0;
		
		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Iterator<Coup> it = listeCoups.iterator();
		while(it.hasNext()) {
			Coup coupCourant = it.next();
			Case premiereCasePrise = null;
			if(!coupImpossible(coupCourant, partieCourante.combo)) {
				Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee);
				Case arrivee = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
				Case depart = matrice[coupCourant.depart.ligne][coupCourant.depart.colonne];
				ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, arrivee, couleurJoueur);
				ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, depart, couleurJoueur);
				int evaluationNbCapturesPercussion = pionsACapturerRapprochement.size();
				int evaluationNbCapturesAspiration = pionsACapturerEloignement.size();
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */								
				if(evaluationNbCapturesPercussion > 0 && evaluationNbCapturesAspiration > 0) {
					ArrayList<Case> comboPercussion = new ArrayList<Case>(partieCourante.combo);
					ArrayList<Case> comboAspiration = new ArrayList<Case>(partieCourante.combo);
					
					/* Modification plateau et appel récursif pour la capture par percussion */
					capturer(coupCourant, false, pionsACapturerRapprochement, couleurJoueur);					
					comboPercussion.add(matrice[coupCourant.depart.ligne][coupCourant.depart.colonne]);
					comboPercussion.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
					Case[] listeCases2 = new Case[1];
					listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
					int res1 = alphaBeta(listeCases2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, profondeurCourante, couleurJoueur, comboPercussion, true);
					annuler(false, couleurJoueur);
					
					/* Modification plateau et appel récursif pour la capture par aspiration */
					capturer(coupCourant, false, pionsACapturerEloignement, couleurJoueur);
					comboAspiration.add(matrice[coupCourant.depart.ligne][coupCourant.depart.colonne]);
					comboAspiration.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
					Case[] listeCases3 = new Case[1];
					listeCases3[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
					int res2 = alphaBeta(listeCases3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, profondeurCourante, couleurJoueur, comboAspiration, true);
					annuler(false, couleurJoueur);
					
					if(res1>res2){
						premiereCasePrise = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].getCaseAt(directionCoup);
						res = res1;
					}
					else {
						premiereCasePrise = matrice[coupCourant.depart.ligne][coupCourant.depart.colonne].getCaseAt(Direction.oppose(directionCoup));
						res = res2;
					}
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					ArrayList<Case> pionsJouables;
					ArrayList<Case> combo = new ArrayList<Case>(partieCourante.combo);
					combo.add(matrice[coupCourant.depart.ligne][coupCourant.depart.colonne]);
					combo.add(matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne]);
					if(evaluationNbCapturesPercussion > 0) {
						capturer(coupCourant, false, pionsACapturerRapprochement, couleurJoueur);
						Case[] listeCases2 = new Case[1];
						listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						premiereCasePrise = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].getCaseAt(directionCoup);
						res = alphaBeta(listeCases2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, profondeurCourante, couleurJoueur, combo, true);
						annuler(false, couleurJoueur);
					}
					else if(evaluationNbCapturesAspiration > 0) {
						capturer(coupCourant, false, pionsACapturerEloignement, couleurJoueur);
						Case[] listeCases2 = new Case[1];
						listeCases2[0] = matrice[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne];
						premiereCasePrise = matrice[coupCourant.depart.ligne][coupCourant.depart.colonne].getCaseAt(Direction.oppose(directionCoup));
						res = alphaBeta(listeCases2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, profondeurCourante, couleurJoueur, combo, true);
						annuler(false, couleurJoueur);
					} 
					else {
						if(partieCourante.combo.isEmpty()){
							capturer(coupCourant, false, new ArrayList<Case>(), couleurJoueur);
						}
						pionsJouables = lesPionsJouables(couleurAdversaire);
						Case[] listeCases2 = new Case[pionsJouables.size()];
						combo.clear();
						res = alphaBeta(pionsJouables.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeurCourante-1, couleurAdversaire, combo, false);
						if(partieCourante.combo.isEmpty()){
							annuler(false, couleurJoueur);
						}
					}
				}
				// PRINT
				Coup p = coupCourant;
				System.out.println("(" + p.depart.ligne + "," + p.depart.colonne + ")" + "(" + p.arrivee.ligne + "," + p.arrivee.colonne + ")" + " -> " + res);
				if(res > meilleurRes) {
					premieresCasesPrisesNonPerdants.clear();
					listeCoupsNonPerdants.clear();
					if(!(coupPerdantPercussion(arrivee, couleurAdversaire) || coupPerdantAspiration(arrivee, couleurAdversaire)) && profondeurCourante == profondeurBis) {
						premieresCasesPrisesNonPerdants.add(premiereCasePrise);
						listeCoupsNonPerdants.add(coupCourant);
					}
					meilleurRes = res;
					meilleurCoups.clear();
					premieresCasesPrises.clear();
					meilleurCoups.add(coupCourant);
					premieresCasesPrises.add(premiereCasePrise);
				}
				else if(res == meilleurRes) {
					if(!(coupPerdantPercussion(arrivee, couleurAdversaire) || coupPerdantAspiration(arrivee, couleurAdversaire)) && profondeurCourante == profondeurBis) {
						premieresCasesPrisesNonPerdants.add(premiereCasePrise);
						listeCoupsNonPerdants.add(coupCourant);
					}
					meilleurCoups.add(coupCourant);
					premieresCasesPrises.add(premiereCasePrise);
				}
			}
		}
//		System.out.println("Taille liste coups non perdants : " + listeCoupsNonPerdants.size());
//		for(int i = 0; i < listeCoupsNonPerdants.size(); i++){
//			System.out.println(listeCoupsNonPerdants.get(i));
//		}
		if(listeCoupsNonPerdants.size() > 0) {
			premieresCasesPrises = premieresCasesPrisesNonPerdants;
			meilleurCoups = listeCoupsNonPerdants;
		}
		Random r = new Random(System.currentTimeMillis());
		int rand = r.nextInt(meilleurCoups.size());
		choix = premieresCasesPrises.get(rand);
		//System.out.println("Coup renvoyé " + meilleurCoups.get(rand));
		//System.out.println("Temps mis : " + (System.nanoTime()-tempsAvant));
		return meilleurCoups.get(rand);
	}

	@Override
	public String getNiveau() {
		return "IA Difficile";
	}

	@Override
	public Case choisirDirectionAManger(ArrayList<Case> rapprochement,
			ArrayList<Case> eloignement) {
		return choix;
	}

	@Override
	public Player clone()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
}

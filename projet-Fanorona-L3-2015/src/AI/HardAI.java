package AI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import engine.*;

public class HardAI extends Player {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Case> premieresCasesPrises; /* La première case prise par chaque coup stocké dans la liste des meilleurs coups */
	Case choix; /* La première case qu'on capture avec le coup choisi à la fin de la méthode play */

	public HardAI(Engine moteur, boolean isAI, String name)
	{
		super(moteur, isAI, name);
		premieresCasesPrises = new ArrayList<Case>();
	}
	
	public int alphaBeta(Noeud n, Case[] listeCases, int alpha, int beta, boolean noeudMin, int profondeur, Pion couleurJoueur) {
		int val = 0;
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		Pion couleurAdversaire = inversePion(couleurJoueur);
		if(profondeur == 0 || n.nbPionsAdversaire == 0 || n.nbPionsJoueur == 0) { /* Si on est sur une feuille ou qu'on a atteint la profondeur maximale */
			return n.nbPionsJoueur-n.nbPionsAdversaire;
		}
		else if (noeudMin) { /* tour de l'adversaire */
			val = Integer.MAX_VALUE;
			for(int i  = 0; i < listeCoups.size(); i++){ /* On joue chaque coup possible */
				/*Noeud noeudPercussion = new Noeud(n);
				Noeud noeudAspiration = new Noeud(n);*/
				Coup coupCourant = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
				//int nbCapturésPercussion = capturer(noeudPercussion, noeudMin, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur)); 	/* nbCapturésPercussion = nb pions capturés par percussion */
				//int nbCapturésAspiration = capturer(noeudAspiration, noeudMin, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur));	/* nbCapturésAbsorption = nb pions capturés par aspiration */
				
				ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, n.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur);
				ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, n.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur);
				int evaluationNbCapturésPercussion = pionsACapturerRapprochement.size();
				int evaluationNbCapturésAspiration = pionsACapturerEloignement.size();
				
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				
				/*noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;*/
				
				/*ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];*/
				if(evaluationNbCapturésPercussion > 0 && evaluationNbCapturésAspiration > 0){
					Noeud noeudPercussion = new Noeud(n);
					Noeud noeudAspiration = new Noeud(n);
					
					noeudPercussion.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					noeudAspiration.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					
					ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
					ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
					
					int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					val = java.lang.Math.min(val, java.lang.Math.min(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					Noeud nouveauNoeud = new Noeud(n);
					ArrayList<Case> pionsJouables;
					if(evaluationNbCapturésPercussion > evaluationNbCapturésAspiration) {
						/* Capture et mise a jour du pion bougé */
						capturer(nouveauNoeud, false, pionsACapturerRapprochement);
						nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
						nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
						/* On détermine les nouveaux pions jouables */
						pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
						Case[] listeCases2 = new Case[pionsJouables.size()];
						val = java.lang.Math.min(val, alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					}
					else {
						/* Capture et mise a jour du pion bougé */
						capturer(nouveauNoeud, false, pionsACapturerEloignement);
						nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
						nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
						/* On détermine les nouveaux pions jouables */
						pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
						Case[] listeCases2 = new Case[pionsJouables.size()];
						val = java.lang.Math.min(val, alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					}
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'IA */
			val = Integer.MIN_VALUE;
			for(int i  = 0; i < listeCoups.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				/*Noeud noeudPercussion = new Noeud(n);
				Noeud noeudAspiration = new Noeud(n);*/
				Coup coupCourant = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
				//int nbCapturésPercussion = capturer(noeudPercussion, false, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne], couleurJoueur));
				//int nbCapturésAspiration = capturer(noeudAspiration, false, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne], couleurJoueur));			
				ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, n.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur);
				ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, n.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur);
				int evaluationNbCapturésPercussion = pionsACapturerRapprochement.size();
				int evaluationNbCapturésAspiration = pionsACapturerEloignement.size();
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				
				/*noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;*/
				
				/*ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];*/
				if(evaluationNbCapturésPercussion > 0 && evaluationNbCapturésAspiration > 0) {
					Noeud noeudPercussion = new Noeud(n);
					Noeud noeudAspiration = new Noeud(n);
					
					noeudPercussion.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					noeudAspiration.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					
					ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
					ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
					
					int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					val = java.lang.Math.max(val, java.lang.Math.max(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					Noeud nouveauNoeud = new Noeud(n);
					ArrayList<Case> pionsJouables;
					if(evaluationNbCapturésPercussion > evaluationNbCapturésAspiration) {
						/* Capture et mise a jour du pion bougé */
						capturer(nouveauNoeud, false, pionsACapturerRapprochement);
						nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
						nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
						/* On détermine les nouveaux pions jouables */
						pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
						Case[] listeCases2 = new Case[pionsJouables.size()];
						val = java.lang.Math.max(val, alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					}
					else {
						/* Capture et mise a jour du pion bougé */
						capturer(nouveauNoeud, false, pionsACapturerEloignement);
						nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
						nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
						/* On détermine les nouveaux pions jouables */
						pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
						Case[] listeCases2 = new Case[pionsJouables.size()];
						val = java.lang.Math.max(val, alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					}
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
	
	/* Méthodes récupérées dans Game */
	
	private int capturer(Noeud n, boolean tourAdversaire, ArrayList<Case> l)
	{
		Iterator<Case> it = l.iterator();
		while (it.hasNext())
		{
			it.next().pion = null;
			if (!tourAdversaire)
				n.nbPionsAdversaire--;
			else
				n.nbPionsJoueur--;
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
	
	public ArrayList<Case> lesPionsJouables(Noeud n, Pion p)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		for (int i = 0; i < n.plateau[0].length; i++)
			for (int j = 0; j < n.plateau.length; j++)
			{
				if (n.plateau[j][i].pion == p)
				{
					for (Case case1 : n.plateau[j][i].voisins())
					{
						if (case1.estVide())
						{
							res.add(n.plateau[j][i]);
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
	
	private boolean coupValide(Noeud n, Coup c, Case[] pionsPossibles, boolean doitManger, Pion p)
	{
		if (c == null || c.arrivee == null || c.depart == null)
			return false;

		ArrayList<Coordonnee> l = new ArrayList<Coordonnee>();
		for (int i = 0; i < pionsPossibles.length; i++)
			l.add(pionsPossibles[i].position);
		Direction d = determinerDirection(c.depart, c.arrivee);
		Case arrivee = n.plateau[c.arrivee.ligne][c.arrivee.colonne];
		Case depart = n.plateau[c.depart.ligne][c.depart.colonne];
		boolean res = (c != null && l.contains(c.depart) && n.plateau[c.arrivee.ligne][c.arrivee.colonne].estVide() && c.depart != c.arrivee);
		if (!doitManger)
			return res;
		else
			res = res && (determinerPionsACapturerRapprochement(d, arrivee, p).size() > 0 || determinerPionsACapturerEloignement(d, depart,p).size() > 0);
		return res;
	}
	
	
	
	/* Fin méthodes récupérées dans Game */
	
	public ArrayList<Coup> creerCoups(Case[] listeCases, Noeud n, boolean doitManger, Pion p){
		ArrayList<Coup> listeCoups = new ArrayList<Coup>();
		for(int i =0; i < listeCases.length; i++){
			ArrayList<Case> voisins = voisins(listeCases[i]);
			for(int j = 0; j < voisins.size(); j++){
				Coup c = new Coup(listeCases[i].position, voisins.get(j).position);
				if(coupValide(n, c, listeCases, doitManger, p))
					listeCoups.add(new Coup(listeCases[i].position, voisins.get(j).position));
			}
		}
		return listeCoups;
	}
	
	@Override
	public Coup play(Case[] listeCases)
	{
		int profondeur = 8;
		long tempsAvant = System.nanoTime();
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		Noeud n = new Noeud(leMoteur.partieCourante);
		ArrayList<Coup> meilleurCoups = new ArrayList<Coup>();
		Pion couleurJoueur = (leMoteur.partieCourante.joueurCourant == leMoteur.partieCourante.joueurBlanc) ? Pion.Blanc : Pion.Noir;
		Pion couleurAdversaire = inversePion(couleurJoueur);
		int meilleurRes = Integer.MIN_VALUE;
		int res = 0;
//		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		for(int i = 0; i < listeCoups.size(); i++){
			Coup coupCourant = listeCoups.get(i);
			/*Noeud noeudPercussion = new Noeud(n);
			Noeud noeudAspiration = new Noeud(n);*/
			/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
			Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
			//int nbCapturésPercussion = capturer(noeudPercussion, false, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne], couleurJoueur));
			//int nbCapturésAspiration = capturer(noeudAspiration, false, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne], couleurJoueur));			
			Case premiereCasePrise;
			ArrayList<Case> pionsACapturerRapprochement = determinerPionsACapturerRapprochement(directionCoup, n.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur);
			ArrayList<Case> pionsACapturerEloignement = determinerPionsACapturerEloignement(directionCoup, n.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur);
			int evaluationNbCapturésPercussion = pionsACapturerRapprochement.size();
			int evaluationNbCapturésAspiration = pionsACapturerEloignement.size();
			
			/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
			 * des deux captures est la meilleure */
			/*noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
			noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
			noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
			noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
			ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
			ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
			Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
			Case premiereCasePrise;*/
			
			if(evaluationNbCapturésPercussion > 0 && evaluationNbCapturésAspiration > 0) { // Capture avec un choix
				Noeud noeudPercussion = new Noeud(n);
				Noeud noeudAspiration = new Noeud(n);
				capturer(noeudPercussion, false, pionsACapturerRapprochement);
				capturer(noeudAspiration, false, pionsACapturerEloignement);
				
				noeudPercussion.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
				noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
				noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
				noeudAspiration.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
				ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
				
				int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				if(res1>res2){
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].getCaseAt(directionCoup);
					res = res1;
				}
				else {
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[coupCourant.depart.ligne][coupCourant.depart.colonne].getCaseAt(Direction.oppose(directionCoup));
					res = res2;
				}
			}
			/* Sinon, on fait la seule capture réellement possible */
			else {
				Noeud nouveauNoeud = new Noeud(n);
				ArrayList<Case> pionsJouables;
				if(evaluationNbCapturésPercussion > evaluationNbCapturésAspiration) {
					capturer(nouveauNoeud, false, pionsACapturerRapprochement);
					nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables.size()];
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].getCaseAt(directionCoup);
					res = alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				}
				else {
					capturer(nouveauNoeud, false, pionsACapturerEloignement);
					nouveauNoeud.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne].pion = null;
					nouveauNoeud.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne].pion = couleurJoueur;
					pionsJouables = lesPionsJouables(nouveauNoeud, couleurAdversaire);
					Case[] listeCases2 = new Case[pionsJouables.size()];
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[coupCourant.depart.ligne][coupCourant.depart.colonne].getCaseAt(Direction.oppose(directionCoup));
					res = alphaBeta(nouveauNoeud, pionsJouables.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				}
			}
			// PRINT
			Coup p = coupCourant;
			//System.out.println("(" + p.depart.ligne + "," + p.depart.colonne + ")" + "(" + p.arrivee.ligne + "," + p.arrivee.colonne + ")" + " -> " + res);
			
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoups.clear();
				premieresCasesPrises.clear();
				meilleurCoups.add(coupCourant);
				premieresCasesPrises.add(premiereCasePrise);
			}
			if(res == meilleurRes){
				meilleurCoups.add(coupCourant);
				premieresCasesPrises.add(premiereCasePrise);
			}
		}
		Random r = new Random();
		int rand = r.nextInt(meilleurCoups.size());
		choix = premieresCasesPrises.get(rand);
		//System.out.println(meilleurCoups.get(rand));
		System.out.println("Temps mis : " + (System.nanoTime()-tempsAvant));
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

package AI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import engine.*;

public class EasyAI extends Player {
	ArrayList<Case> premieresCasesPrises; /* La première case prise par chaque coup stocké dans la liste des meilleurs coups */
	Case choix; /* La première case qu'on capture avec le coup choisi à la fin de la méthode play */

	public EasyAI(Engine moteur, boolean isAI, String name)
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
				Noeud noeudPercussion = new Noeud(n);
				Noeud noeudAspiration = new Noeud(n);
				Coup coupCourant = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
				int nbCapturésPercussion = capturer(noeudPercussion, noeudMin, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur)); 	/* nbCapturésPercussion = nb pions capturés par percussion */
				int nbCapturésAspiration = capturer(noeudAspiration, noeudMin, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur));	/* nbCapturésAbsorption = nb pions capturés par aspiration */
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
				if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
					int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					val = java.lang.Math.min(val, java.lang.Math.min(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAspiration)
						val = java.lang.Math.min(val, alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					else val = java.lang.Math.min(val, alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'IA */
			val = Integer.MIN_VALUE;
			for(int i  = 0; i < listeCoups.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				Noeud noeudPercussion = new Noeud(n);
				Noeud noeudAspiration = new Noeud(n);
				Coup coupCourant = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction directionCoup = determinerDirection(coupCourant.depart, coupCourant.arrivee); 	/* d = direction correspondant au coup */
				int nbCapturésPercussion = capturer(noeudPercussion, noeudMin, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[coupCourant.arrivee.ligne][coupCourant.arrivee.colonne], couleurJoueur));	/* nbCapturésPercussion = nb pions capturés par percussion */
				int nbCapturésAspiration = capturer(noeudAspiration, noeudMin, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[coupCourant.depart.ligne][coupCourant.depart.colonne], couleurJoueur));	/* nbCapturésAbsorption = nb pions capturés par aspiration */
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
				noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
				ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
				if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
					int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire);
					val = java.lang.Math.max(val, java.lang.Math.max(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAspiration)
						val = java.lang.Math.max(val, alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
					else val = java.lang.Math.max(val, alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, couleurAdversaire));
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
	
	/* Fin méthodes récupérées dans Game */
	
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
	
	@Override
	public Coup play(Case[] listeCases)
	{
		int profondeur = 8;
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
		System.out.println();
		for(int i = 0; i < listeCoups.size(); i++){				
			Noeud noeudPercussion = new Noeud(n);
			Noeud noeudAspiration = new Noeud(n);
			/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
			Direction directionCoup = determinerDirection(listeCoups.get(i).depart, listeCoups.get(i).arrivee); 	/* d = direction correspondant au coup */
			int nbCapturésPercussion = capturer(noeudPercussion, false, determinerPionsACapturerRapprochement(directionCoup, noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne], couleurJoueur));
			int nbCapturésAspiration = capturer(noeudAspiration, false, determinerPionsACapturerEloignement(directionCoup, noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne], couleurJoueur));			
			/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
			 * des deux captures est la meilleure */
			noeudPercussion.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
			noeudPercussion.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
			noeudAspiration.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].pion = null;
			noeudAspiration.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].pion = couleurJoueur;
			ArrayList<Case> pionsJouables2 = lesPionsJouables(noeudPercussion, couleurAdversaire);
			ArrayList<Case> pionsJouables3 = lesPionsJouables(noeudAspiration, couleurAdversaire);
			Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
			Case premiereCasePrise;
			if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
				int res1 = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				int res2 = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				if(res1>res2){
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].getCaseAt(directionCoup);
					res = res1;
				}
				else {
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].getCaseAt(Direction.oppose(directionCoup));
					res = res2;
				}
			}
			/* Sinon, on fait la seule capture réellement possible */
			else {
				if(nbCapturésPercussion > nbCapturésAspiration){
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne].getCaseAt(directionCoup);
					res = alphaBeta(noeudPercussion, pionsJouables2.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				}
				else {
					premiereCasePrise = leMoteur.partieCourante.matricePlateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne].getCaseAt(Direction.oppose(directionCoup));
					res = alphaBeta(noeudAspiration, pionsJouables3.toArray(listeCases3), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur-1, couleurAdversaire);
				}
			}
			// PRINT
			Coup p = listeCoups.get(i);
			System.out.println("(" + p.depart.ligne + "," + p.depart.colonne + ")" + "(" + p.arrivee.ligne + "," + p.arrivee.colonne + ")" + " -> " + res);
			
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoups.clear();
				premieresCasesPrises.clear();
				meilleurCoups.add(listeCoups.get(i));
				premieresCasesPrises.add(premiereCasePrise);
			}
			if(res == meilleurRes){
				meilleurCoups.add(listeCoups.get(i));
				premieresCasesPrises.add(premiereCasePrise);
			}
		}
		Random r = new Random();
		int rand = r.nextInt(meilleurCoups.size());
		choix = premieresCasesPrises.get(rand);
		return meilleurCoups.get(rand);
	}

	@Override
	public String getNiveau() {
		return "IA Facile";
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

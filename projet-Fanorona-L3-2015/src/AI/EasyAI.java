package AI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import engine.*;

public class EasyAI extends Player {
	Direction choix; // ATTENTION RENVOYER PREMIER PION A MANGER AU LIEU DE DIRECTION

	public EasyAI(Engine moteur, boolean isAI, String name)
	{
		super(moteur, isAI, name);
		choix = null;
	}
	
	public int alphaBeta(Noeud n, Case[] listeCases, int alpha, int beta, boolean noeudMin, int profondeur, Pion p) {
		/* VOIR A QUI C'EST DE JOUER (noir ou blanc et joueur ia ou adversaire -> le passer en parametre ?) ET APPELER lesPionsJouables (modifié) 
		 * 
		 * */
		int val = 0;
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		Pion p2 = inversePion(p);
		if(profondeur == 0 || n.nbPionsAdversaire == 0 || n.nbPionsJoueur == 0) { /* Si on est sur une feuille ou qu'on a atteint la profondeur maximale */
			System.out.println("IA " + n.nbPionsJoueur);
			System.out.println("Adversaire " + n.nbPionsAdversaire);
			return n.nbPionsJoueur-n.nbPionsAdversaire; /* OU différence entre nbPionsAdversaire et nbPionsJoueur */
		}
		else if (noeudMin) { /* tour de l'adversaire */
			val = Integer.MAX_VALUE;
			for(int i  = 0; i < listeCoups.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				Noeud n2 = new Noeud(n);
				Noeud n3 = new Noeud(n);
				Coup c1 = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction d = determinerDirection(c1.depart, c1.arrivee); 	/* d = direction correspondant au coup */
				int nbCapturésPercussion = capturer(n2, noeudMin, determinerPionsACapturerRapprochement(d, n2.plateau[c1.arrivee.ligne][c1.arrivee.colonne], p)); 	/* nbCapturésPercussion = nb pions capturés par percussion */
				int nbCapturésAspiration = capturer(n3, noeudMin, determinerPionsACapturerEloignement(d, n3.plateau[c1.depart.ligne][c1.depart.colonne], p));	/* nbCapturésAbsorption = nb pions capturés par aspiration */
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				ArrayList<Case> pionsJouables2 = lesPionsJouables(n2, p2);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(n3, p2);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
				if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
					int res1 = alphaBeta(n2, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, p2);
					int res2 = alphaBeta(n3, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, p2);
					val = java.lang.Math.min(val, java.lang.Math.min(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAspiration)
						val = java.lang.Math.min(val, alphaBeta(n2, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, inversePion(p)));
					else val = java.lang.Math.min(val, alphaBeta(n3, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, inversePion(p)));
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'IA */
			val = Integer.MIN_VALUE;
			for(int i  = 0; i < listeCoups.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				Noeud n2 = new Noeud(n);
				Noeud n3 = new Noeud(n);
				Coup c1 = listeCoups.get(i);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction d = determinerDirection(c1.depart, c1.arrivee); 	/* d = direction correspondant au coup */
				int nbCapturésPercussion = capturer(n2, noeudMin, determinerPionsACapturerRapprochement(d, n2.plateau[c1.arrivee.ligne][c1.arrivee.colonne], p));	/* nbCapturésPercussion = nb pions capturés par percussion */
				// PRINT -----------------------------------
				System.out.println(nbCapturésPercussion);
				int nbCapturésAspiration = capturer(n3, noeudMin, determinerPionsACapturerEloignement(d, n3.plateau[c1.depart.ligne][c1.depart.colonne], p));	/* nbCapturésAbsorption = nb pions capturés par aspiration */
				// PRINT -----------------------------------
				System.out.println(nbCapturésAspiration);
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				ArrayList<Case> pionsJouables2 = lesPionsJouables(n2, p2);
				ArrayList<Case> pionsJouables3 = lesPionsJouables(n3, p2);
				Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
				if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
					int res1 = alphaBeta(n2, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, p2);
					int res2 = alphaBeta(n3, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, p2);
					val = java.lang.Math.max(val, java.lang.Math.max(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAspiration)
						val = java.lang.Math.max(val, alphaBeta(n2, pionsJouables2.toArray(listeCases2), alpha, beta, !noeudMin, profondeur-1, inversePion(p)));
					else val = java.lang.Math.max(val, alphaBeta(n3, pionsJouables3.toArray(listeCases3), alpha, beta, !noeudMin, profondeur-1, inversePion(p)));
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

		if (courante.getCaseAt(d) != null && courante.getCaseAt(d).estVide())
		{
			while (courante != null)
			{

				courante = courante.getCaseAt(Direction.oppose(d));

				if (courante != null && !courante.estVide() & courante.pion != p)
					res.add(courante);
				else
					break;
			}
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
		int profondeur = 0;
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		Noeud n = new Noeud(leMoteur.partieCourante);
		ArrayList<Coup> meilleurCoups = new ArrayList<Coup>();
		Pion couleur = (leMoteur.partieCourante.joueurCourant == leMoteur.partieCourante.joueurBlanc) ? Pion.Blanc : Pion.Noir;
		Pion p2 = inversePion(couleur);
		int meilleurRes = 0;
		int res = 0;
		
		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		for(int i = 0; i < listeCoups.size(); i++){				
			Noeud n2 = new Noeud(n);
			Noeud n3 = new Noeud(n);
			/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
			Direction d = determinerDirection(listeCoups.get(i).depart, listeCoups.get(i).arrivee); 	/* d = direction correspondant au coup */
			int nbCapturésPercussion = capturer(n2, false, determinerPionsACapturerRapprochement(d, n2.plateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne], couleur));
			int nbCapturésAspiration = capturer(n3, false, determinerPionsACapturerEloignement(d, n3.plateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne], couleur));
			
			/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
			 * des deux captures est la meilleure */
			ArrayList<Case> pionsJouables2 = lesPionsJouables(n2, p2);
			ArrayList<Case> pionsJouables3 = lesPionsJouables(n3, p2);
			Case[] listeCases2 = new Case[pionsJouables2.size()], listeCases3 = new Case[pionsJouables3.size()];
			if(nbCapturésPercussion > 0 && nbCapturésAspiration > 0){
				int res1 = alphaBeta(n2, pionsJouables2.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur, p2);
				int res2 = alphaBeta(n3, pionsJouables3.toArray(listeCases3), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur, p2);
				res = java.lang.Math.max(res1, res2);
			}
			/* Sinon, on fait la seule capture réellement possible */
			else {
				if(nbCapturésPercussion > nbCapturésAspiration)
					res = alphaBeta(n2, pionsJouables2.toArray(listeCases2), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur, p2);
				else res = alphaBeta(n3, pionsJouables3.toArray(listeCases3), Integer.MIN_VALUE, Integer.MAX_VALUE, true, profondeur, p2);
			}
			// PRINT
			Coup p = listeCoups.get(i);
			System.out.println("(" + p.depart.ligne + "," + p.depart.colonne + ")" + "(" + p.arrivee.ligne + "," + p.arrivee.colonne + ")" + " -> " + res);
			
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoups.clear();
				meilleurCoups.add(listeCoups.get(i));
			}
			if(res == meilleurRes){
				meilleurCoups.add(listeCoups.get(i));
			}
		}
		Random r = new Random();
		return meilleurCoups.get(r.nextInt(meilleurCoups.size()));
	}

	@Override
	public String getNiveau() {
		return "IA Facile";
	}

	@Override
	public Case choisirDirectionAManger(ArrayList<Case> rapprochement,
			ArrayList<Case> eloignement) {
		// TODO Auto-generated method stub
		return null;
	}
	
}

package AI;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import engine.*;

public class EasyAI extends Player {
	Direction choix;

	public EasyAI(Engine moteur, boolean isAI, String name)
	{
		super(moteur, isAI, name);
		choix = null;
	}
	
	public int alphaBeta(Noeud n, Case c, int alpha, int beta, boolean noeudMin, int profondeur) {
		int val = 0;
		ArrayList<Case> voisins = c.voisins();
		if(profondeur == 0 || n.nbPionsAdversaire == 0 || n.nbPionsJoueur == 0){ /* Si on est sur une feuille ou qu'on a atteint la profondeur maximale */
			return n.nbPionsJoueur; /* OU différence entre nbPionsAdversaire et nbPionsJoueur */
		}
		else if (noeudMin) { /* tour de l'adversaire */
			val = Integer.MAX_VALUE;
			for(int i  = 0; i < voisins.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				Noeud n2 = new Noeud(n);
				Noeud n3 = new Noeud(n);
				Coup c1 = new Coup(c.position, voisins.get(i).position);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction d2 = determinerDirection(c1.depart, c1.arrivee);	/* d2 = direction correspondant à la capture par percussion */
				Direction d3 = Direction.oppose(d2);						/* d3 = direction correspondant à la capture par absorption */
				int nbCapturésPercussion = capturer(determinerPionsACapturer(d2, n2.plateau[c1.arrivee.y][c1.arrivee.x])); 	/* nbCapturésPercussion = nb pions capturés par percussion */
				int nbCapturésAbsorption = capturer(determinerPionsACapturer(d3, n3.plateau[c1.depart.y][c1.depart.x]));	/* nbCapturésAbsorption = nb pions capturés par absorption */
				n2.nbPionsJoueur -= nbCapturésPercussion;
				n3.nbPionsJoueur -= nbCapturésAbsorption;
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				if(nbCapturésPercussion > 0 && nbCapturésAbsorption > 0){
					int res1 = alphaBeta(n2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1);
					int res2 = alphaBeta(n3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1);
					val = java.lang.Math.min(val, java.lang.Math.min(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAbsorption)
						val = java.lang.Math.min(val, alphaBeta(n2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
					else val = java.lang.Math.min(val, alphaBeta(n3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'IA */
			val = Integer.MIN_VALUE;
			for(int i  = 0; i < voisins.size(); i++){ /* Pour chaque voisin de la case c, on va jouer le coup c -> voisin */
				Noeud n2 = new Noeud(n);
				Noeud n3 = new Noeud(n);
				Coup c1 = new Coup(c.position, voisins.get(i).position);
				/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
				Direction d2 = determinerDirection(c1.depart, c1.arrivee); 	/* d2 = direction correspondant à la capture par percussion */
				Direction d3 = Direction.oppose(d2);						/* d3 = direction correspondant à la capture par absorption */
				int nbCapturésPercussion = capturer(determinerPionsACapturer(d2, n2.plateau[c1.arrivee.y][c1.arrivee.x]));	/* nbCapturésPercussion = nb pions capturés par percussion */
				int nbCapturésAbsorption = capturer(determinerPionsACapturer(d3, n3.plateau[c1.depart.y][c1.depart.x]));	/* nbCapturésAbsorption = nb pions capturés par absorption */
				n2.nbPionsAdversaire -= nbCapturésPercussion;
				n3.nbPionsAdversaire -= nbCapturésAbsorption;
				/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
				 * des deux captures est la meilleure */
				if(nbCapturésPercussion > 0 && nbCapturésAbsorption > 0){
					int res1 = alphaBeta(n2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1);
					int res2 = alphaBeta(n3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1);
					val = java.lang.Math.max(val, java.lang.Math.max(res1, res2));
				}
				/* Sinon, on fait la seule capture réellement possible */
				else {
					if(nbCapturésPercussion > nbCapturésAbsorption)
						val = java.lang.Math.max(val, alphaBeta(n2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
					else val = java.lang.Math.max(val, alphaBeta(n3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
				}
				if(val >= beta) return val;
				alpha = java.lang.Math.max(alpha, val);
			}
		}
		return val;
	}
	
	@Override
	public Direction choisirDirectionAManger() {
		return choix;
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
	
	private int capturer(ArrayList<Case> l)
	{
		Iterator<Case> it = l.iterator();
		while (it.hasNext())
		{
			it.next().pion = null;
			if (leMoteur.partieCourante.joueurCourant == leMoteur.partieCourante.joueurBlanc)
				leMoteur.partieCourante.nombrePionNoir--;
			else
				leMoteur.partieCourante.nombrePionBlanc--;
		}
		return l.size();
	}

	private ArrayList<Case> determinerPionsACapturer(Direction d, Case depart)
	{
		ArrayList<Case> res = new ArrayList<Case>();
		Case courante = depart;
		Pion p = (leMoteur.partieCourante.joueurCourant == leMoteur.partieCourante.joueurBlanc) ? Pion.Blanc : Pion.Noir;

		while (courante != null)
		{

			switch (d)
			{
				case Nord:
					courante = courante.nord;
					break;
				case NordEst:
					courante = courante.nordEst;
					break;
				case Est:
					courante = courante.est;
					break;
				case SudEst:
					courante = courante.sudEst;
					break;
				case Sud:
					courante = courante.sud;
					break;
				case SudOuest:
					courante = courante.sudOuest;
					break;
				case Ouest:
					courante = courante.ouest;
					break;
				case NordOuest:
					courante = courante.nordOuest;
					break;
				default:
					break;
			}
			if (courante != null && !courante.estVide() & courante.pion != p)
				res.add(courante);
			else
				break;
		}

		return res;
	}
	
	public static Direction determinerDirection(Point depart, Point arrivee)
	{
		int deplacementX = arrivee.x - depart.x;
		int deplacementY = arrivee.y - depart.y;
		switch (deplacementX)
		{
			case -1:
				switch (deplacementY)
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
				switch (deplacementY)
				{
					case -1:
						return Direction.Nord;
					case 1:
						return Direction.Sud;
				}
				break;
			case 1:
				switch (deplacementY)
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
		return null;
	}
	
	/* Fin méthodes récupérées dans Game */
	
	@Override
	public Coup play(Case[] listeCases)
	{
		ArrayList<Coup> listeCoups = new ArrayList<Coup>();
		Noeud n = new Noeud(leMoteur.partieCourante);
		Coup meilleurCoup = null;
		int meilleurRes = 0;
		int res = 0;
		/* Construction de la liste des coups possibles */
		for(int i =0; i<listeCases.length; i++){
			ArrayList<Case> voisins = listeCases[i].voisins();
			for(int j = 0; j<voisins.size(); j++){
				listeCoups.add(new Coup(new Point(listeCases[i].position), voisins.get(j).position));
			}
		}
		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		for(int i = 0; i < listeCoups.size(); i++){				
			Noeud n2 = new Noeud(n);
			Noeud n3 = new Noeud(n);
			/* On joue le coup avec les deux types de capture (percussion et absorption) sur des copies du plateau de jeu pour ne pas modifier l'état de la partie */
			Direction d2 = determinerDirection(listeCoups.get(i).depart, listeCoups.get(i).arrivee);
			Direction d3 = Direction.oppose(d2);
			int nbCapturésPercussion = capturer(determinerPionsACapturer(d2, n2.plateau[listeCoups.get(i).arrivee.y][listeCoups.get(i).arrivee.x]));
			int nbCapturésAbsorption = capturer(determinerPionsACapturer(d3, n3.plateau[listeCoups.get(i).depart.y][listeCoups.get(i).depart.x]));
			Case c2 = n2.plateau[listeCoups.get(i).arrivee.y][listeCoups.get(i).arrivee.x];
			Case c3 = n3.plateau[listeCoups.get(i).arrivee.y][listeCoups.get(i).arrivee.x];
			
			/* Si les deux types de capture sont réellement possibles (i.e. capturent réellement des pions), on appelle l'algorithme sur les deux copies du plateau pour déterminer laquelle
			 * des deux captures est la meilleure */
			if(nbCapturésPercussion > 0 && nbCapturésAbsorption > 0){
				int res1 = alphaBeta(n2, c2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5);
				int res2 = alphaBeta(n3, c3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5);
				res = java.lang.Math.max(res1, res2);
			}
			/* Sinon, on fait la seule capture réellement possible */
			else {
				if(nbCapturésPercussion > nbCapturésAbsorption)
					res = alphaBeta(n2, c2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5);
				else res = alphaBeta(n3, c3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5);
			}
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoup = listeCoups.get(i);
			}
		}
		return meilleurCoup;
	}

	@Override
	public String getNiveau() {
		return "IA Facile";
	}
	
}

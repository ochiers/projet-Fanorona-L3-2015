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
	
	public int alphaBeta(Case[][] plateau, Case c, int alpha, int beta, boolean noeudMin, int profondeur){
		int val = 0;
		ArrayList<Case> voisins = c.voisins();
		if(profondeur == 0){ /* OU la partie finit */
			/* retourner eval(c), i.e. le nombre de cases mangées par ce coup */
		}
		else if (noeudMin) { /* tour de l'IA */
			val = Integer.MAX_VALUE;
			for(int i  = 0; i < voisins.size(); i++){
				Case[][] plateau2 = copiePlateau(plateau);
				Case[][] plateau3 = copiePlateau(plateau);
				
				/* En cas de choix, on fait deux appels récursifs, un pour chaque choix possible, et on prend le meilleur des deux */	
				Coup c1 = new Coup(c.position, voisins.get(i).position);
				Direction d2 = determinerDirection(c1.depart, c1.arrivee);
				Direction d3 = Direction.oppose(d2);
				int capt1 = capturer(determinerPionsACapturer(d2, plateau2[c1.arrivee.y][c1.arrivee.x]));
				int capt2 = capturer(determinerPionsACapturer(d3, plateau3[c1.depart.y][c1.depart.x]));
				if(capt1 > 0 && capt2 > 0){
					int res1 = alphaBeta(plateau2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1) + capt1;
					int res2 = alphaBeta(plateau3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1) + capt2;
					val = java.lang.Math.min(val, java.lang.Math.max(res1, res2));
				}
				/* Si il n'y a pas de choix à faire, on fait l'appel récursif classique avec la direction pour laquelle on capture */
				else {
					if(capt1 > capt2)
						val = java.lang.Math.min(val, alphaBeta(plateau2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1)) + capt1;
					else val = java.lang.Math.min(val, alphaBeta(plateau3, voisins.get(i), alpha, beta, !noeudMin, profondeur-1)) + capt2;
				}
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'adversaire */
			val = Integer.MIN_VALUE;
			for(int i  = 0; i < voisins.size(); i++){
				Case[][] plateau2 = copiePlateau(plateau);
				/* copier la méthode qui joue concrètement le coup (équivalent de mangerGaufre) dans ce fichier et l'appeler ici sur listeCoups[i]*/
				val = java.lang.Math.max(val, alphaBeta(plateau2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
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
	public Coup play(Coup[] listeCoups)
	{
		Case[][] plateau = leMoteur.partieCourante.matricePlateau;
		Coup meilleurCoup;
		int meilleurRes;
		int res = 0;
		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		for(int i = 0; i < listeCoups.length; i++){
			Case[][] plateau2 = copiePlateau(plateau);		
			Case[][] plateau3 = copiePlateau(plateau);
			
			/* En cas de choix, on fait deux appels récursifs, un pour chaque choix possible, et on prend le meilleur des deux */	
			Direction d2 = determinerDirection(listeCoups[i].depart, listeCoups[i].arrivee);
			Direction d3 = Direction.oppose(d2);
			int capt1 = capturer(determinerPionsACapturer(d2, plateau2[listeCoups[i].arrivee.y][listeCoups[i].arrivee.x]));
			int capt2 = capturer(determinerPionsACapturer(d3, plateau3[listeCoups[i].depart.y][listeCoups[i].depart.x]));
			Case c2 = plateau2[listeCoups[i].arrivee.y][listeCoups[i].arrivee.x];
			Case c3 = plateau2[listeCoups[i].arrivee.y][listeCoups[i].arrivee.x];
			if(capt1 > 0 && capt2 > 0){
				int res1 = alphaBeta(plateau2, c2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5) + capt1;
				int res2 = alphaBeta(plateau3, c3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5) + capt2;
				res = java.lang.Math.max(res1, res2);
			}
			/* Si il n'y a pas de choix à faire, on fait l'appel récursif classique avec la direction pour laquelle on capture */
			else {
				if(capt1 > capt2)
					res = alphaBeta(plateau2, c2, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5) + capt1;
				else res = alphaBeta(plateau3, c3, Integer.MIN_VALUE, Integer.MAX_VALUE, false, 5) + capt2;
			}
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoup = listeCoups[i];
			}
		}
		return meilleurCoup;
	}
	
}

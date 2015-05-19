package AI;

import java.awt.Point;
import java.util.ArrayList;

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
			val = 100000;
			for(int i  = 0; i < voisins.size(); i++){
				Case[][] plateau2 = copiePlateau(plateau);
				/* copier la méthode qui joue concrètement le coup (équivalent de mangerGaufre) dans ce fichier et l'appeler ici sur le coup c -> voisins.get(i)*/
				val = java.lang.Math.min(val, alphaBeta(plateau2, voisins.get(i), alpha, beta, !noeudMin, profondeur-1));
				if(alpha >= val) return val;
				beta = java.lang.Math.min(beta, val);
			}
		}
		else { /* tour de l'adversaire */
			val = -1000000;
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
	public Direction choisirDirectionAManger(Direction d1, Direction d2) {
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
			/* copier la méthode qui joue concrètement le coup (équivalent de mangerGaufre) dans ce fichier et l'appeler ici sur listeCoups[i]*/
			Case c = plateau2[listeCoups[i].arrivee.x][listeCoups[i].arrivee.y];
			res = alphaBeta(plateau2, c, -1000000, +1000000, false, 5); /* Appel différent selon si on peut enchaîner un autre coup (true) ou pas (false) */
			if(res > meilleurRes){
				meilleurRes = res;
				meilleurCoup = listeCoups[i];
			}
		}
		return meilleurCoup;
	}
	
}

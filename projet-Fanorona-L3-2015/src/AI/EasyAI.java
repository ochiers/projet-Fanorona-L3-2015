package AI;

import java.awt.Point;

import engine.*;

public class EasyAI extends Player {

	public EasyAI(Engine gaufre, boolean isAI, String name)
	{
		super(gaufre, isAI, name);
	}
	
	public int alphaBeta(Gaufre g, int alpha, int beta, Point[] pts){
		if (aucuneCaseLibre(g)) return 0;
		else {
			int meilleur = -1000000000;
			for(int i = 0; i < pts.length; i++){
				int val = -alphaBeta(pts[i], -beta, -alpha);
				if(val > meilleur){
					meilleur = val;
					if(meilleur > alpha){
						alpha = meilleur;
						if(alpha >= beta) return meilleur;
					}
				}
			}
			return meilleur;
		}
	}

	public EnchainementCoups alphaBeta2(Gaufre g, int alpha, int beta, Point[] pts, boolean noeudMin){
		EnchainementCoups ec = new EnchainementCoups();
		if (aucuneCaseLibre(g)) return 0;
		else if (noeudMin) { /* tour de l'IA */
			ec.val = 100000;
			for(int i  = 0; i < g.largeur; i++){
				for (int j = 0; j < g.hauteur; j++){
					ec.val = java.lang.Math.min(ec.val, alphaBeta2(Pi, alpha, beta, pts, !noeudMin));
					if(alpha >= ec.val) return ec;
					beta = java.lang.Math.min(beta, ec.val);
				}
			}
		}
		else { /* tour de l'adversaire */
			ec.val = -1000000;
			for(int i  = 0; i < g.largeur; i++){
				for (int j = 0; j < g.hauteur; j++){
					ec.val = java.lang.Math.max(ec.val, alphaBeta2(Pi, alpha, beta, pts, !noeudMin));
					if(ec.val >= beta) return ec;
					alpha = java.lang.Math.max(alpha, ec.val);
				}
			}
		}
		return ec;
	}
	
	@Override
	public Point play()
	{
		try { /* Sleep pour pouvoir visualiser les coups lors d'une partie entre deux IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Gaufre g = leMoteur.partieCourante.map;
		for(int i = 0; i < g.largeur; i++){
			for (int j = 0; j < g.hauteur; j++){
				//System.out.println("case : " + i + " " + j + " valeur : " + g.grille[i][j]);
				if(g.grille[i][j] == Gaufre.LIBRE){
					Gaufre g2 = new Gaufre(g);
					Point p = new Point(i,j);
					mangerGaufre(g2, p);
					if(evaluation(g2, false)) return p;
				}
			}
		}
	}

}

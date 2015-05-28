package AI;

import java.util.ArrayList;
import java.util.Random;

import engine.*;

public class EasyAI extends Player {
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
	
	@Override
	public Coup play(Case[] listeCases)
	{
		try { /* Sleep pour pouvoir visualiser les coups de l'IA */
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Game partieCourante = leMoteur.getCurrentGame();
		ArrayList<Coup> listeCoups = creerCoups(listeCases);
		ArrayList<Coup> listeCaptures = new ArrayList<Coup>();
		Pion couleurJoueur = (partieCourante.joueurCourant == partieCourante.joueurBlanc) ? Pion.Blanc : Pion.Noir;
		Random r = new Random(System.currentTimeMillis());
		for (int i = 0; i < listeCoups.size(); i++){ 
			Case depart = partieCourante.matricePlateau[listeCoups.get(i).depart.ligne][listeCoups.get(i).depart.colonne];
			Case arrivee = partieCourante.matricePlateau[listeCoups.get(i).arrivee.ligne][listeCoups.get(i).arrivee.colonne];
			Direction directionCoup = determinerDirection(listeCoups.get(i).depart, listeCoups.get(i).arrivee);
			Direction opposeDirectionCoup = Direction.oppose(directionCoup);
			Case premiereCaseAspiration = depart.getCaseAt(opposeDirectionCoup);
			Case premiereCasePercussion = arrivee.getCaseAt(directionCoup);
			/* Si le coup permet une capture par aspiration, on l'ajoute à la liste des captures */
			if(premiereCaseAspiration != null && !premiereCaseAspiration.estVide() && premiereCaseAspiration.pion != couleurJoueur){
				listeCaptures.add(listeCoups.get(i));
				premieresCasesPrises.add(premiereCaseAspiration);
			}
			/* Si le coup permet une capture par percussion, on l'ajoute à la liste des captures */
			if(premiereCasePercussion != null && !premiereCasePercussion.estVide() && premiereCasePercussion.pion != couleurJoueur){
				listeCaptures.add(listeCoups.get(i));
				premieresCasesPrises.add(premiereCasePercussion);
			}
		}
		if(listeCaptures.size() > 0){ 		/* Si il y a des coups qui réalisent une capture, on joue un de ces coups, choisi de façon aléatoire */
			int coupChoisi = r.nextInt(listeCaptures.size());
			choix = premieresCasesPrises.get(coupChoisi);
			System.out.println(choix);
			premieresCasesPrises.clear();
			return listeCaptures.get(coupChoisi);
		}
		else { 								/* Si il n'y a pas de coup qui réalise une capture, on joue un coup, choisi de façon aléatoire */
			return listeCoups.get(r.nextInt(listeCoups.size()));
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

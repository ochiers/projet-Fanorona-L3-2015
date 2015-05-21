package engine;

public class Coup {

	public Coordonnee	depart;
	public Coordonnee	arrivee;

	/**
	 * Represente un coup joué par un joueur
	 * @param depart Case d'origine du mouvement
	 * @param arrivee Case d'arrivee du pion
	 */
	public Coup(Coordonnee depart, Coordonnee arrivee)
	{
		this.depart = depart;
		this.arrivee = arrivee;
	}

}

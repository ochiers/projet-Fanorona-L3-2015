package engine;

import java.io.Serializable;

public class Coup implements Serializable{

	public Coordonnee	depart;
	public Coordonnee	arrivee;

	/**
	 * Represente un coup joué par un joueur
	 * 
	 * @param depart
	 *            Case d'origine du mouvement
	 * @param arrivee
	 *            Case d'arrivee du pion
	 */
	public Coup(Coordonnee depart, Coordonnee arrivee)
	{
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public String toString()
	{
		return "Coup " + depart + ", " + arrivee;
	}

}

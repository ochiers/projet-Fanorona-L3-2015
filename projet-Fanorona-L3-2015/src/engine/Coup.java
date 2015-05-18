package engine;

import java.awt.Point;

public class Coup {

	public Point	depart;
	public Point	arrivee;

	/**
	 * Represente un coup joué par un joueur
	 * @param depart Case d'origine du mouvement
	 * @param arrivee Case d'arrivee du pion
	 */
	public Coup(Point depart, Point arrivee)
	{
		this.depart = depart;
		this.arrivee = arrivee;
	}

}

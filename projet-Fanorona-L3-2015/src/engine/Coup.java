package engine;

import java.io.Serializable;

/**
 * Represente un coup joué par un joueur sous forme d'une point de depart et
 * d'arrivee
 * 
 * @author soulierc
 *
 */
public class Coup implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3522894575528978575L;
	/**
	 * La case de depart du coup joué
	 */
	public Coordonnee			depart;
	/**
	 * La case d'arrivee du coup joué
	 */
	public Coordonnee			arrivee;

	/**
	 * Represente un coup joué par un joueur
	 * 
	 * @param depart
	 *            Case d'origine du mouvement
	 * @param arrivee
	 *            Case d'arrivee du pion
	 */
	public Coup(Coordonnee depart, Coordonnee arrivee) {
		this.depart = depart;
		this.arrivee = arrivee;
	}

	public String toString() {
		return "Coup " + depart + ", " + arrivee;
	}

}

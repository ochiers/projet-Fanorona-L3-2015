package engine;

import java.io.Serializable;

/**
 * Represente des coordonnees matricielles
 * 
 * @author soulierc
 *
 */
public class Coordonnee implements Serializable {

	private static final long	serialVersionUID	= -2185563985578894357L;
	/**
	 * La composante ligne de la coordonnee
	 */
	public int					ligne;
	/**
	 * La composante colonne de la coordonnee
	 */
	public int					colonne;

	/**
	 * Cree une coordonnee [ligne][colonne]
	 * 
	 * @param ligne
	 * @param colonne
	 */
	public Coordonnee(int ligne, int colonne) {
		this.ligne = ligne;
		this.colonne = colonne;
	}

	/**
	 * Renvoie la composante ligne
	 * 
	 * @return un entier etant la ligne
	 */
	public int getLigne() {
		return this.ligne;
	}

	/**
	 * Renvoie la composante colonne
	 * 
	 * @return un entier etant la colonne
	 */
	public int getColonne() {
		return this.colonne;
	}

	public boolean equals(Object o) {
		if (o != null && o instanceof Coordonnee) {
			Coordonnee c = (Coordonnee) o;
			return c.ligne == this.ligne && c.colonne == this.colonne;
		}
		return false;
	}

	public String toString() {
		return "[Ligne = " + this.ligne + ", Colonne = " + this.colonne + "]";
	}

}

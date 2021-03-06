package engine;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represente une case du plateau
 * 
 * @author soulierc
 *
 */
public class Case implements Serializable {

	private static final long	serialVersionUID	= -438749539936809334L;

	/* Les voisins de cette case */
	public Case					nord;
	public Case					nordEst;
	public Case					est;
	public Case					sudEst;
	public Case					sud;
	public Case					sudOuest;
	public Case					ouest;
	public Case					nordOuest;

	/**
	 * La position de la case dans la matrice du plateau
	 */
	public Coordonnee			position;

	/**
	 * Le pion placé sur la case Si null -> case vide
	 */
	public Pion					pion;

	/**
	 * Cree une nouvelle case
	 * 
	 * @param position
	 *            La position de la case dans la matrice
	 */
	public Case(Coordonnee position) {
		this.position = position;
	}

	/**
	 * Donne les voisins de cette case
	 * 
	 * @return
	 */
	public ArrayList<Case> voisins() {
		ArrayList<Case> res = new ArrayList<Case>();
		if (nord != null)
			res.add(nord);
		if (nordEst != null)
			res.add(nordEst);
		if (est != null)
			res.add(est);
		if (sudEst != null)
			res.add(sudEst);
		if (sud != null)
			res.add(sud);
		if (sudOuest != null)
			res.add(sudOuest);
		if (ouest != null)
			res.add(ouest);
		if (nordOuest != null)
			res.add(nordOuest);
		return res;
	}

	/**
	 * Indique si la case est vide ou non
	 * 
	 * @return
	 */
	public boolean estVide() {
		return pion == null;
	}

	/**
	 * Retourne la case voisine de celle-ci a une direction donnée
	 * 
	 * @param d
	 *            La direction de la case voisine voulue
	 * @return La case voisin de celle si
	 */
	public Case getCaseAt(Direction d) {
		switch (d) {
			case Nord:
				return this.nord;
			case NordEst:
				return this.nordEst;
			case Est:
				return this.est;
			case SudEst:
				return this.sudEst;
			case Sud:
				return this.sud;
			case SudOuest:
				return this.sudOuest;
			case Ouest:
				return this.ouest;
			case NordOuest:
				return this.nordOuest;
			default:
				return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o instanceof Case) {
			Case c = (Case) o;
			return c.position.colonne == this.position.colonne && this.position.ligne == c.position.ligne;
		}
		if (o instanceof Point) {
			Coordonnee c = (Coordonnee) o;
			return c.colonne == this.position.colonne && this.position.ligne == c.ligne;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "Case (" + position.ligne + ", " + position.colonne + ") : " + pion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Case clone() {
		Case c = new Case(new Coordonnee(this.position.ligne, this.position.colonne));
		if (this.pion == Pion.Blanc)
			c.pion = Pion.Blanc;
		else if (this.pion == Pion.Noir)
			c.pion = Pion.Noir;
		else
			c.pion = null;
		return c;
	}

}

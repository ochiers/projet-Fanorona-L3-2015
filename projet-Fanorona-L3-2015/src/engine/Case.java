package engine;

import java.awt.Point;
import java.util.ArrayList;

public class Case {

	public int			nbVoisins;
	public Case			nord;
	public Case			nordEst;
	public Case			est;
	public Case			sudEst;
	public Case			sud;
	public Case			sudOuest;
	public Case			ouest;
	public Case			nordOuest;
	public Coordonnee	position;
	public Pion			pion;

	public Case(Coordonnee position)
	{
		this.position = position;
	}

	public ArrayList<Case> voisins()
	{
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

	public boolean estVide()
	{
		return pion == null;
	}

	public Case getCaseAt(Direction d)
	{

		switch (d)
		{

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

	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (o instanceof Case)
		{
			Case c = (Case) o;
			return c.position.colonne == this.position.colonne && this.position.ligne == c.position.ligne;
		}
		if (o instanceof Point)
		{
			Coordonnee c = (Coordonnee) o;
			return c.colonne == this.position.colonne && this.position.ligne == c.ligne;
		}
		return false;
	}

	public String toString()
	{

		return "Case (" + position.ligne + ", " + position.colonne + ") : " + pion;
	}

	public Case clone()
	{
		Case c = new Case(new Coordonnee(this.position.ligne, this.position.colonne));
		c.nbVoisins = this.nbVoisins;
		if (this.pion == Pion.Blanc)
			c.pion = Pion.Blanc;
		else if (this.pion == Pion.Noir)
			c.pion = Pion.Noir;
		else
			c.pion = null;
		return c;

	}

}

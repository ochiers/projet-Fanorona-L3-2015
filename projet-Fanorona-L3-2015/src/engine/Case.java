package engine;

import java.awt.Point;
import java.util.ArrayList;

public class Case {

	public int nbVoisins;
	public Case nord;
	public Case nordEst;
	public Case est;
	public Case sudEst;
	public Case sud;
	public Case sudOuest;
	public Case ouest;
	public Case nordOuest;
	public Point position;
	public Pion pion;
	
	public Case(Point position){
		this.position = position;
	}
	
	public ArrayList<Case> voisins()
	{
		ArrayList<Case> res = new ArrayList<Case>();
		if(nord != null)
			res.add(nord);
		if(nordEst != null)
			res.add(nordEst);
		if(est != null)
			res.add(est);
		if(sudEst != null)
			res.add(sudEst);
		if(sud != null)
			res.add(sud);
		if(sudOuest != null)
			res.add(sudOuest);
		if(ouest != null)
			res.add(ouest);
		if(nordOuest != null)
			res.add(nordOuest);
		return res;
	}
	
	public boolean estVide(){
		return pion == null;
	}
	
	public Case getCaseAt(Direction d){
		
		switch(d){
			
			case Nord : return this.nord;
			case NordEst : return this.nordEst;
			case Est : return this.est;
			case SudEst : return this.sudEst;
			case Sud : return this.sud;
			case SudOuest : return this.sudOuest;
			case Ouest : return this.ouest;
			case NordOuest : return this.nordOuest;
			default : return null;
		}
		
		
	}
	
	public boolean equals(Object o)
	{
		if(o==null)
			return false;
		if(!(o instanceof Case))
			return false;
		Case c = (Case)o;
		return c.position.x == this.position.x && this.position.y == c.position.y; 
	}
	
}

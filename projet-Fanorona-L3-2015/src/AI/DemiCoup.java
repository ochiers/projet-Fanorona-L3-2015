package AI;

import java.util.ArrayList;

import engine.Case;
import engine.Coup;

public class DemiCoup {

	public Coup deplacement;
	public ArrayList<Case> cases;
	
	public DemiCoup(Coup c, ArrayList<Case> cases){
		this.deplacement = c;
		this.cases = cases;
	}
}

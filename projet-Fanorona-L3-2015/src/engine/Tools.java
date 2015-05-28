package engine;

import AI.*;

public class Tools {
	
	public static Player createPlayer(EngineServices e, PlayerType p, String nom){
		Player res;
		switch(p)
		{
			case Humain : res = new HumanPlayer(e, false, nom);
				break;
			case IAFacile : res = new EasyAI(e, true, nom);
				break;
			case IAMoyenne : res = new MediumAI(e, true, nom);
				break;
			case IADifficile : res = new HardAI(e, true, nom);
				break;
			default : throw new RuntimeException();
		}
		return res;
	}
	
	
	
}

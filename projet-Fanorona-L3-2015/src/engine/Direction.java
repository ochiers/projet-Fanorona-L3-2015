package engine;

public enum Direction
{
	Nord,
	NordEst,
	Est,
	SudEst,
	Sud,
	SudOuest,
	Ouest,
	NordOuest;
	
	
	public static Direction oppose(Direction d)
	{
		Direction res = null;
		switch (d)
		{
			case Nord:
				res = Sud;
				break;
			case NordEst:
				res = SudOuest;
				break;
			case Est:
				res = Ouest;
				break;
			case SudEst:
				res = NordOuest;
				break;
			case Sud:
				res = Nord;
				break;
			case SudOuest:
				res = NordEst;
				break;
			case Ouest:
				res = Est;
				break;
			case NordOuest:
				res = SudEst;
				break;
		}
		return res;
	}
}

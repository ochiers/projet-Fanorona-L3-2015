package engine;

/**
 * Classe definissant une direction
 * @author soulierc
 *
 */
public enum Direction
{
	Nord, NordEst, Est, SudEst, Sud, SudOuest, Ouest, NordOuest;

	/**
	 * Donne l'opposee de la direction opposée
	 * @param direction La direction dont on veut l'opposé
	 * @return Une direction qui est l'opposee de d
	 */
	public static Direction oppose(Direction direction)
	{
		Direction res = null;
		switch (direction)
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

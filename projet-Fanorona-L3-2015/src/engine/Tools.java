package engine;

import AI.*;

public class Tools {

	/**
	 * Cree un nouveau joueur
	 * @param e Le moteur associé
	 * @param p Le type de joueur
	 * @param nom Le nom du joueur
	 * @return
	 */
	public static Player createPlayer(EngineServices e, PlayerType p, String nom)
	{
		Player res;
		switch (p)
		{
			case Humain:
				res = new HumanPlayer(e, false, nom);
				break;
			case IAFacile:
				res = new EasyAI(e, true, nom);
				break;
			case IAMoyenne:
				res = new MediumAI(e, true, nom);
				break;
			case IADifficile:
				res = new HardAI(e, true, nom);
				break;
			default:
				throw new RuntimeException();
		}
		return res;
	}

	/**
	 * Donne le type d'un joueur
	 * @param p Le joueur
	 * @return Le type de p (appartien a PlayerType)
	 */
	public static PlayerType getTypeOfPlayer(Player p)
	{

		if (p instanceof HumanPlayer)
			return PlayerType.Humain;
		if (p instanceof EasyAI)
			return PlayerType.IAFacile;
		if (p instanceof MediumAI)
			return PlayerType.IAMoyenne;
		if (p instanceof HardAI)
			return PlayerType.IADifficile;

		throw new RuntimeException();
	}

	/**
	 * Donne la configuration d'une partie donnée
	 * @param g
	 * @return
	 */
	public static Configuration getTypePartie(Game g)
	{
		if (getTypeOfPlayer(g.joueurBlanc) == PlayerType.Humain && getTypeOfPlayer(g.joueurNoir) == PlayerType.Humain)
			return Configuration.HumainVSHumain;
		if (g.joueurBlanc.aiPlayer && g.joueurNoir.aiPlayer)
			return Configuration.IAvsIA;
		if (g.joueurBlanc.aiPlayer || g.joueurNoir.aiPlayer)
			return Configuration.HumainVSIA;

		throw new RuntimeException();
	}

}

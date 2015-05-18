package engine;

import java.awt.Point;

public abstract class Player {
	
	public boolean aiPlayer;
	public String name;
	public Engine leMoteur;
	public Player(Engine gaufre, boolean isAI, String name)
	{
		this.aiPlayer = isAI;
		this.name = name;
		this.leMoteur = gaufre;
	}

	public abstract Point play();
	
	public String toString()
	{
		
		return "" + aiPlayer + "#" + name;
	}
	
	
}

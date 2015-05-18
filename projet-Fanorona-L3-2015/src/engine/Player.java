package engine;

public abstract class Player {
	
	public boolean aiPlayer;
	public String name;
	public Engine leMoteur;
	private boolean stopped;
	public Player(Engine leMoteur, boolean isAI, String name)
	{
		this.aiPlayer = isAI;
		this.name = name;
		this.leMoteur = leMoteur;
	}

	public abstract Coup play();
	
	public abstract Direction choisirDirectionAManger(Direction d1, Direction d2);
	
	
	public String toString()
	{
		
		return "" + aiPlayer + "#" + name;
	}

	public boolean isStopped()
	{
		return stopped;
	}

	/**
	 * Permet d'arreter un joueur de jouer (dans le cas d'une mise en pause du jeu par exemple)
	 * @param stopped True -> arrete le joueur, False -> retour a l'etat initial
	 */
	public void setStopped(boolean bool)
	{
		this.stopped = bool;
	}
	
	
}

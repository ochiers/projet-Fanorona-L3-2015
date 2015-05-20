package engine;

public abstract class Player {

	public boolean	aiPlayer;
	public String	name;
	public Engine	leMoteur;
	private boolean	stopped;

	public Player(Engine leMoteur, boolean isAI, String name)
	{
		this.aiPlayer = isAI;
		this.name = name;
		this.leMoteur = leMoteur;
	}

	public String toString()
	{

		return "" + aiPlayer + "#" + name;
	}

	public boolean isStopped()
	{
		return stopped;
	}

	/**
	 * Permet d'arreter un joueur de jouer (dans le cas d'une mise en pause du
	 * jeu par exemple)
	 * 
	 * @param stopped
	 *            True -> arrete le joueur, False -> retour a l'etat initial
	 */
	public void setStopped(boolean bool)
	{
		this.stopped = bool;
	}

	/**
	 * Fonction demandant au joueur de jouer un coup
	 * 
	 * @return
	 */
	public abstract Coup play(Case[] listeCoups);

	/**
	 * Fonction demandant au joueur de choisir de quel coté il veut capturer les
	 * pions
	 * 
	 * @return La direction choisie
	 */
	public abstract Direction choisirDirectionAManger();
	
	/**
	 * Renseigne le niveau du joueur (Humain, IA Facile, IA Moyenne, IA Difficle)
	 */
	public abstract String getNiveau();
	
}

package engine;

import java.util.ArrayList;

public abstract class Player extends Thread {

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

	public Player(Player p)
	{
		this.aiPlayer = p.aiPlayer;
		this.name = p.name;
		this.leMoteur = p.leMoteur;
		this.stopped = p.stopped;
	}
	
	public String toString()
	{

		return "ID:"+this.hashCode()  + aiPlayer + " " + name;
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
	 * @param eloignement 
	 * @param rapprochement 
	 * 
	 * @return La direction choisie
	 */
	public abstract Case choisirDirectionAManger(ArrayList<Case> rapprochement, ArrayList<Case> eloignement);
	
	/**
	 * Renseigne le niveau du joueur (Humain, IA Facile, IA Moyenne, IA Difficle)
	 */
	public abstract String getNiveau();
	
	public abstract Player clone();
}

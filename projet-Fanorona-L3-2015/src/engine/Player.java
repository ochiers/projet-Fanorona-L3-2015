package engine;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe abstraite represantant un joueur
 * @author soulierc
 *
 */
public abstract class Player extends Thread implements Serializable {

	private static final long		serialVersionUID	= -745601135784423811L;
	
	/**
	 * Indique si le joueur est un humain ou un ordinateur
	 */
	public final boolean			aiPlayer;
	
	/**
	 * Nom du joueur
	 */
	public String					name;
	
	/**
	 * Le moteur associé au joueur
	 */
	public transient EngineServices	leMoteur;
	
	/**
	 * Indique si le joueur a été arreté
	 */
	private boolean					stopped;
	
	/**
	 * Identifiant du joueur, sert a derminer si c'est a son tour de jouer
	 */
	private long idJoueur;
	
	/**
	 * Avatar du joueur, sous oformae de chemin de fichier
	 */
	public String					avatar;

	/**
	 * Cree un joueur
	 * @param leMoteur Le moteur relié au joueur
	 * @param isAI Indique si c'est une ia
	 * @param name Le nom du joueur
	 */
	public Player(EngineServices leMoteur, boolean isAI, String name)
	{
		this.aiPlayer = isAI;
		this.name = name;
		this.leMoteur = leMoteur;
		this.idJoueur = Tools.rand.nextLong();
	}

	public Player(Player p)
	{
		this.aiPlayer = p.aiPlayer;
		this.name = p.name;
		this.leMoteur = p.leMoteur;
		this.stopped = p.stopped;
		this.idJoueur = p.idJoueur;
	}

	public String toString()
	{
		return Tools.getTypeOfPlayer(this) + " : " + aiPlayer + " " + name;
	}

	public boolean isStopped()
	{
		return stopped;
	}

	/**
	 * Permet d'arreter un joueur de jouer (dans le cas d'une mise en pause du jeu par exemple)
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
	public abstract Coup play(Case[][] laMatrice, Case[] listeCoups);

	/**
	 * Fonction demandant au joueur de choisir de quel coté il veut capturer les pions
	 * 
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

	@Override
	public void run()
	{
		while (!isStopped())
		{
			try
			{
				leMoteur.getCurrentGame().jouer(getIdJoueur());
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public long getIdJoueur()
	{
		return idJoueur;
	}

	public void setIdJoueur(long idJoueur)
	{
		this.idJoueur = idJoueur;
	}

}

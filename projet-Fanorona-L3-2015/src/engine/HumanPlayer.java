package engine;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe representant un joueur humain
 * @author soulierc
 *
 */
public class HumanPlayer extends Player implements Serializable {

	private static final long	serialVersionUID	= -8423735410841988573L;
	private boolean hasPlayed;
	private Coup coupJoue;
	private Coordonnee pointDirection;
	public HumanPlayer(EngineServices leMoteur, boolean isAI, String name)
	{
		super(leMoteur, isAI, name);
	}
	
	public HumanPlayer(Player p)
	{
		super(p);
	}

	public void setCoup(Coordonnee depart, Coordonnee arrivee)
	{
		this.coupJoue = new Coup(depart,arrivee);
		this.hasPlayed = true;
	}
	
	public void setDirectionMultiPrise(Coordonnee choixDirection)
	{
		this.pointDirection = choixDirection;
		this.hasPlayed = true;
	}	
	
	@Override
	public String getNiveau()
	{
		return "Humain";
	}

	@Override
	public Coup play(Case[][] laMatrice, Case[] listeCoups)
	{
		hasPlayed = false;
		coupJoue = null;
		pointDirection = null;
		while(!isStopped() && !hasPlayed && coupJoue == null)
		{
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(name + " a fait un coup");
		return coupJoue;
	}

	@Override
	public Case choisirDirectionAManger(ArrayList<Case> rapprochement, ArrayList<Case> eloignement)
	{
		hasPlayed = false;
		pointDirection = null;
		while(!hasPlayed){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if(pointDirection != null)
			return leMoteur.getPlateau()[this.pointDirection.ligne][this.pointDirection.colonne];
		return null;
	}

	@Override
	public Player clone()
	{
		return new HumanPlayer(this);
	}
}

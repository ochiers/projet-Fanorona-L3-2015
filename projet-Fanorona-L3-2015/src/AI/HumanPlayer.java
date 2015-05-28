package AI;

import java.util.ArrayList;

import engine.*;

public class HumanPlayer extends Player {

	private boolean hasPlayed;
	private Coup coupJoue;
	private Coordonnee pointDirection;
	public HumanPlayer(EngineServices leMoteur, boolean isAI, String name)
	{
		super(leMoteur, isAI, name);
		// TODO Auto-generated constructor stub
	}
	
	public HumanPlayer(Player p)
	{
		super(p);
		// TODO Auto-generated constructor stub
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
	public Coup play(Case[] listeCoups)
	{
		hasPlayed = false;
		coupJoue = null;
		pointDirection = null;
		while(!isStopped() && !hasPlayed && coupJoue == null)
		{
			//System.out.println(name + " attend " + this);
			try {
				Thread.sleep(500);
				//System.out.println(name + " attend");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
				//System.out.println(name + " attend");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return leMoteur.partieCourante.matricePlateau[this.pointDirection.ligne][this.pointDirection.colonne];
	}
	



	@Override
	public Player clone()
	{
		
		return new HumanPlayer(this);
	}
}

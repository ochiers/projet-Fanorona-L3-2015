package AI;

import java.awt.Point;

import engine.*;

public class HumanPlayer extends Player {

	private boolean hasPlayed;
	private Coup coupJoue;
	private Point pointDirection;
	public HumanPlayer(Engine leMoteur, boolean isAI, String name)
	{
		super(leMoteur, isAI, name);
		// TODO Auto-generated constructor stub
	}


	public void setCoup(Point depart, Point arrivee)
	{
		this.coupJoue = new Coup(depart, arrivee);
		this.hasPlayed = true;
	}
	
	public void setDirectionMultiPrise(Point choixDirection)
	{
		this.pointDirection = choixDirection;
		this.hasPlayed = true;
	}	
	
	public Direction choisirDirectionAManger()
	{
		hasPlayed = false;
		pointDirection = null;
		while(!hasPlayed && pointDirection == null)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return Game.determinerDirection(coupJoue.arrivee, pointDirection);
	}

	@Override
	public String getNiveau()
	{
		return "Humain";
		
	}

	@Override
	public Coup play(Coup[] listeCoups)
	{
		hasPlayed = false;
		coupJoue = null;
		pointDirection = null;
		while(!hasPlayed && coupJoue == null)
		{
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return coupJoue;
	}	
}

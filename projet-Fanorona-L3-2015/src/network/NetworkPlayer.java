package network;

import java.util.ArrayList;

import engine.Case;
import engine.Coordonnee;
import engine.Coup;
import engine.EngineServices;
import engine.Player;

/**
 * Represente un joueur sur le reseau
 * @author soulierc
 *
 */
public class NetworkPlayer extends Player {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	public NetworkPlayer(EngineServices leMoteur, boolean isAI, String name)
	{
		super(leMoteur, isAI, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Coup play(Case[][] laMatrice, Case[] listeCoups)
	{
		Coup c = null;
		while(c == null)
			try
			{
				c = leMoteur.getNetworkManager().getCoupRecu();
				Thread.sleep(50);
				//System.out.print("jattend");
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return c;
	}

	@Override
	public Case choisirDirectionAManger(ArrayList<Case> rapprochement, ArrayList<Case> eloignement)
	{
		Coordonnee c = null;
		while((c = leMoteur.getNetworkManager().getCoordonnee()) == null)
			try
			{
				Thread.sleep(50);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return leMoteur.getPlateau()[c.ligne][c.colonne];
	}

	@Override
	public String getNiveau()
	{
		return "Reseau";
	}

	@Override
	public Player clone()
	{
		// TODO Auto-generated method stub
		return null;
	}

}

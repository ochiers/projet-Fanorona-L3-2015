package AI;

import engine.*;

public class HumanPlayerConsole extends Player {

	public HumanPlayerConsole(Engine gaufre, boolean isAI, String name)
	{
		super(gaufre, isAI, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Coup play(Case[] listeCoups)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Direction choisirDirectionAManger()
	{
		
		return null;
	}

	@Override
	public String getNiveau()
	{
		return "Humain Console";
	}


}

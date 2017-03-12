package com.Akoot.daemons;

import java.util.ArrayList;
import java.util.List;

import com.Akoot.daemons.commands.DaemonsCommand;

public class Daemons
{
	List<DaemonsCommand> commands = new ArrayList<DaemonsCommand>();
	
	public void onEnable()
	{
		checkUpdate();
		
		for(DaemonsCommand cmd: commands)
		{
			cmd.onCommand();
		}
	}
	
	private void checkUpdate()
	{
		if(1 < 2)
		{
			update();
		}
	}
	
	private void update()
	{
		System.out.println("xoxo");
	}
}

package com.Akoot.daemons.commands;

import org.bukkit.ChatColor;

public class CommandDaemons extends DaemonsCommand implements Command
{
	public CommandDaemons()
	{
		this.name = "daemons";
		this.color = ChatColor.LIGHT_PURPLE;
	}

	@Override
	public void onCommand()
	{
		sendMessage("Yes");
		if(args.length == 1 && args[0].equals("gc"))
		{
			System.gc();
			sendMessage("Good work, citizen");
		}
	}
}

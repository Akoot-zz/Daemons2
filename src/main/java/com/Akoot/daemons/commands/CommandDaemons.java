package com.Akoot.daemons.commands;

import org.bukkit.ChatColor;

@Cmd(
		name = "daemons",
		color = ChatColor.LIGHT_PURPLE,
		usage = {"v", "version", "-v"}
		)
public class CommandDaemons extends DaemonsCommand implements Command
{
	@Override
	public void onCommand()
	{
	}
}

package com.Akoot.daemons.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.Akoot.daemons.Daemons;

public abstract class DaemonsCommand implements Command
{
	protected String[] args;
	protected Daemons plugin;
	protected CommandSender sender;
	protected String name;
	public String permission;
	protected String[] usage;
	protected ChatColor color;
	protected boolean playerOnly;
	
	protected void sendUsage()
	{
	}
	
	protected void sendMessage(String msg)
	{
		sender.sendMessage(msg);
	}
}

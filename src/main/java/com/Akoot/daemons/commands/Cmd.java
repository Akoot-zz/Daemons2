package com.Akoot.daemons.commands;

import org.bukkit.ChatColor;

public @interface Cmd
{
	String name();
	ChatColor color();
	String[] usage();
}

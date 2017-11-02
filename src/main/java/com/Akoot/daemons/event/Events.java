package com.Akoot.daemons.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Akoot.daemons.Daemons;
import com.Akoot.daemons.PluginPart;
import com.Akoot.daemons.User;

public class Events extends PluginPart implements Listener
{
	public Events(Daemons plugin)
	{
		super(plugin);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		plugin.registerUser(new User(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event)
	{
		plugin.unregisterUser(new User(event.getPlayer()));
	}
	
	@EventHandler
	public void onPlayerBanned(PlayerKickEvent event)
	{
		if(event.getPlayer().isBanned()) plugin.getUser(event.getPlayer()).deleteConfig();
	}
}

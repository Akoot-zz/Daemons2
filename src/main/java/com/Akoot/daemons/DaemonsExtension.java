package com.Akoot.daemons;

import java.util.logging.Logger;

import lombok.Getter;

public class DaemonsExtension
{
	public Daemons plugin;
	public @Getter Logger logger;
	
	public DaemonsExtension() {}
	
	public void onLoad() {}
	
	public void init() {}
	
	public void onUnload() {}
	
	public void register()
	{
		plugin = Daemons.getInstance();
		logger = plugin.getLogger();
		onLoad();
	}
	
	public void registerCommands() {}
}

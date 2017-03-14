package com.Akoot.daemons;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class Daemons extends JavaPlugin
{
	private static Daemons instance;
	private HashSet<User> onlineUsers;
	private Logger logger;
	private File userDir;

	@Override
	public void onEnable()
	{
		instance = new Daemons();
		logger = this.getLogger();
		userDir = new File(getDataFolder(), "users");
		//TODO: checkUpdate();
	}
	
	public File getUsersFolder()
	{
		return userDir;
	}
	
	public File dataFolder()
	{
		return new File(this.getDataFolder().getParentFile(), "Daemons");
	}
	
	public void addOnlineUser(User user)
	{
		onlineUsers.add(user);
		logger.log(Level.FINE, "Added a new User instance for: " + user);
	}
	
	public void removeOnlineUser(User user)
	{
		onlineUsers.remove(user);
	}

	//@Deprecated
	public static Daemons getInstance()
	{
		return instance;
	}

	@Override
	public void onDisable()
	{

	}
}

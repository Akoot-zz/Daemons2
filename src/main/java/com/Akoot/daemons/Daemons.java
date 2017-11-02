package com.Akoot.daemons;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.Akoot.daemons.commands.Commands;
import com.Akoot.daemons.event.Events;
import com.Akoot.util.CthFile;
import com.massivecraft.factions.entity.MPlayer;

import lombok.Getter;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Daemons extends JavaPlugin
{
	private static @Getter Daemons instance;
	private @Getter Set<User> onlineUsers;
	private @Getter File userDir;
	private @Getter Chat chat;
	private @Getter Economy economy;
	private @Getter Permission permission;
	private @Getter Commands commands;
	private Vault vault;
	private Set<DaemonsExtension> extensions;

	@Override
	public void onEnable()
	{
		instance = this;
		registerHooks();
		userDir = new File(getDataFolder(), "users");

		new Events(instance);
		commands = new Commands(instance);

		userDir.mkdirs();
		//TODO: checkUpdate();

		extensions = new HashSet<DaemonsExtension>();

		registerExtensions();
		handleExtensions();
	}

	public void registerHooks()
	{
		vault = (Vault) getPlugin("Vault");
		if(vault != null)
		{
			chat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();
			economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
			permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
		}
		else
		{
			getLogger().log(Level.SEVERE, "instance plugin requires Vault!");
			//getServer().getPluginManager().disablePlugin(instance);
		}
	}

	private void handleExtensions()
	{
		for(DaemonsExtension extension: extensions)
		{
			extension.onLoad();
			extension.registerCommands();
		}
	}

	@SuppressWarnings("resource")
	private void registerExtensions()
	{
		File extensionsDir = new File(dataFolder(), "extensions");
		for(File f: extensionsDir.listFiles())
		{
			if(f.getName().endsWith(".jar"))
			{
				try
				{
					String path = new CthFile(new File(new URL("jar:file:/" + f.getAbsolutePath() + "!/.extension").toURI()).getAbsolutePath()).read()[0];
					DaemonsExtension extension = ((DaemonsExtension) new URLClassLoader(new URL[]{new URL("jar:file:/" + f.getAbsolutePath())}).loadClass(path).newInstance());
					extension.register();
					extensions.add(extension);
				}
				catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException | URISyntaxException e)
				{
					getLogger().log(Level.SEVERE, "Could not load " + f.getName(), e);
				}
			}
		}
	}

	private void unregisterExtensions()
	{
		for(DaemonsExtension extension: extensions) extension.onUnload();
		extensions = new HashSet<DaemonsExtension>();
	}

	public Plugin getPlugin(String plugin)
	{
		return instance.getServer().getPluginManager().getPlugin(plugin);
	}

	public boolean hasPlugin(String plugin)
	{
		return instance.getServer().getPluginManager().getPlugin(plugin) != null;
	}

	public File dataFolder()
	{
		return new File(instance.getDataFolder().getParentFile(), "Daemons");
	}

	public void registerUser(User user)
	{
		onlineUsers.add(user);
		user.loadConfig();
	}

	public void unregisterUser(User user)
	{
		onlineUsers.remove(user);
		user.saveConfig();
	}

	public Set<OfflineUser> getOfflineUsers()
	{
		Set<OfflineUser> offlineUsers = new HashSet<OfflineUser>();
		for(File f: userDir.listFiles())
			offlineUsers.add(new OfflineUser(UUID.fromString(f.getName().substring(f.getName().indexOf(".")))));
		return offlineUsers;
	}

	public User getUser(MPlayer mplayer)
	{
		for(User user: onlineUsers)
			if(user.getMplayer() == mplayer) return user;
		return null;
	}

	public User getUser(String search)
	{
		for(User user: onlineUsers)
			if(user.getName().startsWith(search) || user.getDisplayName().startsWith(search) || user.getUniqueID().equals(UUID.fromString(search))) return user;
		return null;
	}

	public User getUser(Player player)
	{
		for(User user: onlineUsers)
			if(user.getPlayer() == player) return user;
		return null;
	}

	public void log(String msg)
	{
		instance.getLogger().log(Level.INFO, msg);
	}

	@Override
	public void onDisable()
	{
		unregisterExtensions();
	}
}

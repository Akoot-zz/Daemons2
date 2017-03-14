package com.Akoot.daemons;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.MPlayer;

public class User extends OfflineUser
{
	private Player player;
	private MPlayer mplayer;
	private YamlConfiguration config;
	private File configFile;
	private int playtime;

	public User(Player player)
	{
		this.player = player;
		this.configFile = new File(Daemons.getInstance().getUsersFolder(), player.getUniqueId() + ".yml");
	}

	public User(MPlayer mplayer)
	{
		this(mplayer.getPlayer());
		this.mplayer = mplayer;
	}
	
	public MPlayer getMPlayer()
	{
		return mplayer;
	}

	public UUID getUniqueID()
	{
		return player.getUniqueId();
	}

	public String getDisplayName()
	{
		return player.getDisplayName();
	}

	public Configuration getConfig()
	{
		return config;
	}

	public void setDisplayName(String newName)
	{
		this.player.setDisplayName(newName);
	}
	
	public void loadConfig()
	{
		if(!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void saveConfig()
	{
		this.config.set("displayname", this.player.getDisplayName());
		this.config.set("playtime", this.playtime);
		try
		{
			this.config.save(this.configFile);
		}
		catch (IOException e)
		{
			Daemons.getInstance().getLogger().log(Level.WARNING, "Could not save to " + this.configFile.getAbsolutePath(), e);
		}
	}
}

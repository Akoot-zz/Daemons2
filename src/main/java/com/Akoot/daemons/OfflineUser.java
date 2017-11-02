package com.Akoot.daemons;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;

public class OfflineUser
{
	protected File configFile;
	protected @Getter YamlConfiguration config;

	public OfflineUser(UUID uuid)
	{
		this.configFile = new File(Daemons.getInstance().getUserDir(), uuid + ".yml");
	}

	public void deleteConfig()
	{
		configFile.delete();
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
}

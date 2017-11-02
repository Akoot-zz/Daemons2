package com.Akoot.daemons;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.MPlayer;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

public class User extends OfflineUser
{
	private @Getter Player player;
	private @Getter MPlayer mplayer;
	private @Getter @Setter int playtime;

	public User(Player player)
	{
		super(player.getUniqueId());
		this.player = player;
	}

	public User(MPlayer mplayer)
	{
		this(mplayer.getPlayer());
		this.mplayer = mplayer;
	}

	public String getName()
	{
		return player.getName();
	}

	public UUID getUniqueID()
	{
		return player.getUniqueId();
	}

	public String getStrippedDisplayName()
	{
		return ChatColor.stripColor(player.getDisplayName());
	}

	public String getDisplayName()
	{
		return player.getDisplayName();
	}

	public void setDisplayName(String newName)
	{
		this.player.setDisplayName(newName);
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

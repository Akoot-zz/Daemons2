package com.Akoot.daemons.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Akoot.daemons.Daemons;
import com.Akoot.daemons.PluginPart;
import com.Akoot.util.StringUtil;

public class Commands extends PluginPart implements CommandExecutor
{
	private Set<DaemonsCommand> commands;

	public Commands(Daemons plugin)
	{
		super(plugin);
		commands = new HashSet<DaemonsCommand>();

		commands.add(new CommandDaemons());
	}
	
	public void registerCommand(DaemonsCommand command)
	{
		commands.add(command);
	}
	
	public void unregisterCommand(DaemonsCommand command)
	{
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args)
	{
		for(DaemonsCommand command: commands)
		{
			if(command.name.equalsIgnoreCase(cmd.getName()))
			{
				if(command.playerOnly && !(sender instanceof Player))
				{
					sender.sendMessage("Sorry, this is a player-only command");
					return false;
				}
				command.name = cmd.getName();
				command.sender = sender;
				command.permission = "daemons.command." + cmd.getName();
				command.args = args;
				command.onCommand();
				if(sender instanceof BlockCommandSender)
				{
					BlockCommandSender block  = (BlockCommandSender) sender;
					plugin.getLogger().log(Level.INFO, String.format("Command block at %s,%s,%s (%s) issued server command: /%s", block.getBlock().getX(), block.getBlock().getY(), block.getBlock().getZ(), block.getBlock().getWorld().getName(), alias + " " + StringUtil.toString(args)));
				}
			}
		}
		return false;
	}
}

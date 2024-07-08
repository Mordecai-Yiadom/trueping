package me.krousant.trueping.command;

import me.krousant.trueping.plugin.TruePingPlugin;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        switch(args.length)
        {
            case 0:
                if(sender instanceof Player)
                {
                    String pingMessage = String.format("Your ping is %d ms.", ((Player) sender).getPing());
                    sender.sendMessage(TruePingPlugin.getPlayerListColor() + pingMessage);

                    TruePingPlugin.updatePing((Player) sender);
                }
                else
                    sender.sendMessage(ChatColor.RED + "Invalid number of arguments. Please use '/ping <player>'.");
                break;

            case 1:
                Player player = Bukkit.getPlayer(args[0]);

                if(player != null)
                {
                    String pingMessage = String.format("%s's ping is %d ms.", player.getName(), player.getPing());
                    sender.sendMessage(TruePingPlugin.getPlayerListColor() + pingMessage);

                    TruePingPlugin.updatePing(player);
                }
                else
                    sender.sendMessage(ChatColor.RED + args[0] + " not found!");
                break;

            default:
                sender.sendMessage(ChatColor.RED + "Invalid number of arguments. Please use '/ping <player>'.");
        }

        return true;
    }
}

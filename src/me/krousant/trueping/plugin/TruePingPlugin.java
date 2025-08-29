package me.krousant.trueping.plugin;

import me.krousant.trueping.command.Ping;
import me.krousant.trueping.event.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.java.*;
import org.bukkit.entity.Player;
import java.util.Collection;

public class TruePingPlugin extends JavaPlugin
{
    private static ChatColor playerListColor;
    private static Runnable updatePings;

    //Measured in seconds
    private static int pingUpdateInterval;
    private static boolean sendConsoleUpdates, showPlayerListPing;
    private static PingConfig.PositionStyle positionStyle;

    @Override
    public void onEnable()
    {
        PingConfig.initConfig(this);

        this.getCommand("ping").setExecutor(new Ping());

        playerListColor = PingConfig.getPlayerListColor();
        pingUpdateInterval = PingConfig.getPingUpdateInterval();
        sendConsoleUpdates = PingConfig.getSendConsoleUpdateMessages();
        showPlayerListPing = PingConfig.getShowPlayerListPing();
        positionStyle = PingConfig.getPositionStyle();

        if(showPlayerListPing)
        {
            updatePings = () ->
            {
                Collection<Player> onlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();
                int numPlayersUpdate = 0;

                if (!onlinePlayers.isEmpty()) {
                    for (Player player : onlinePlayers)
                    {
                        updatePing(player);
                        numPlayersUpdate++;
                    }
                }

                if (sendConsoleUpdates) {
                    String consoleMessage = String.format("[TruePing] Updated %d player(s) ping on player list.", numPlayersUpdate);
                    Bukkit.getServer().getConsoleSender().sendMessage(playerListColor + consoleMessage);
                }
            };

            initPingUpdates();
        }

        //Register Event Handler(s)
        PlayerJoinEvent.getHandlerList().register(new RegisteredListener(new PlayerJoinEventExecutor(),
                new PlayerJoinEventExecutor(), EventPriority.NORMAL, this, true));

        PlayerQuitEvent.getHandlerList().register(new RegisteredListener(new PlayerQuitEventExecutor(),
                new PlayerQuitEventExecutor(), EventPriority.NORMAL, this, true));
    }

    @Override
    public void onDisable()
    {
        Collection<Player> onlinePlayers = (Collection<Player>) Bukkit.getOnlinePlayers();

        for(Player player : onlinePlayers)
            player.setPlayerListName(player.getName());

        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        return true;
    }

    public static ChatColor getPlayerListColor()
    {
        return playerListColor;
    }

    public static int getPingUpdateInterval()
    {
        return pingUpdateInterval;
    }

    private void initPingUpdates()
    {
        Bukkit.getScheduler().runTaskTimer(this, updatePings, 20, pingUpdateInterval * 20);
    }

    public static void updatePing(Player player)
    {
        if(showPlayerListPing)
        {
            String playerName;

            switch(positionStyle)
            {
                case PREFIX:
                    String prefix = String.format(playerListColor + "[%dms] ", player.getPing());
                    playerName = String.format(ChatColor.RESET + player.getName());
                    player.setPlayerListName(prefix + playerName);
                    break;

                case POSTFIX:
                    String postfix = String.format(playerListColor + " [%dms]", player.getPing());
                    playerName = player.getName();
                    player.setPlayerListName(playerName + postfix);
                    break;
            }

        }
    }
}

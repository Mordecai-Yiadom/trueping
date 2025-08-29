package me.krousant.trueping.plugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PingConfig
{
    private static Plugin plugin;
    private static FileConfiguration config;

    protected static void initConfig(Plugin plugin)
    {
        PingConfig.plugin = plugin;

        config = plugin.getConfig();
        config.options().copyDefaults(true);
        plugin.saveConfig();
    }

    protected static ChatColor getPlayerListColor()
    {
        //Init Default Color
        ChatColor playerListColor = ChatColor.GREEN;

        String colorAsString = (String) config.get("player-list-color");

        try
        {
            ChatColor tempColor = ChatColor.valueOf(colorAsString);

            if(tempColor.isColor())
                playerListColor = tempColor;
        }
        catch(Exception ex)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[TruePing] Could not initialize color from 'config.yml'! Now using default color.");
        }

        return playerListColor;
    }

    protected static boolean getSendConsoleUpdateMessages()
    {
        return config.getBoolean("send-console-update-messages");
    }
    protected static boolean getShowPlayerListPing()
    {
        return config.getBoolean("show-player-list-ping");
    }

    protected static int getPingUpdateInterval()
    {
        return config.getInt("ping-update-interval");
    }

    protected static PositionStyle getPositionStyle()
    {
        String configOption = config.getString("player-list-position-style");
        if(configOption == null) return PositionStyle.POSTFIX;

        for(PositionStyle style : PositionStyle.values())
            if(style.alias.equalsIgnoreCase(configOption)) return style;

       return PositionStyle.POSTFIX;
    }

    public enum PositionStyle
    {
        PREFIX("prefix"),
        POSTFIX("postfix");

        private String alias;

        PositionStyle(String alias)
        {
            this.alias = alias;
        }

        public String getAlias(){return this.alias;}
    }
}

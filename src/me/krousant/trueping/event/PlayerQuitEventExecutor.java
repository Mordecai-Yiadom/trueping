package me.krousant.trueping.event;

import me.krousant.trueping.plugin.TruePingPlugin;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.*;
import org.bukkit.entity.Player;
public class PlayerQuitEventExecutor implements EventExecutor, Listener
{
    @Override
    public void execute(Listener listener, Event e)
    {
        PlayerQuitEvent event = (PlayerQuitEvent) e;

        Player player = event.getPlayer();

        if(player != null)
            player.setPlayerListName(player.getName());
    }
}

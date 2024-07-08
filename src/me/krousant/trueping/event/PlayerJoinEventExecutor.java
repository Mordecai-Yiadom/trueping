package me.krousant.trueping.event;

import me.krousant.trueping.plugin.TruePingPlugin;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.*;

public class PlayerJoinEventExecutor implements EventExecutor, Listener
{

    @Override
    public void execute(Listener listener, Event e)
    {
        PlayerJoinEvent event = (PlayerJoinEvent) e;

        if(event.getPlayer() != null)
            TruePingPlugin.updatePing(event.getPlayer());
    }
}

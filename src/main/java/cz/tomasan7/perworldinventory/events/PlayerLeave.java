package cz.tomasan7.perworldinventory.events;

import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener
{

    @EventHandler
    public void onPlayerLeave (PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        Group.getGroupByWorld(player.getWorld().getName(), true).savePlayerData(player);
    }
}
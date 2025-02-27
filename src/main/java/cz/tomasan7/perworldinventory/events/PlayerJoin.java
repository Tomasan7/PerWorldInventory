package cz.tomasan7.perworldinventory.events;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.playerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener
{

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        Group group = Group.getGroupByWorld(player.getWorld().getName(), true);

        PlayerData playerData = group.getPlayerData(player);

        if (playerData != null)
            PlayerData.setPlayerData(player, playerData, group.getSavedData());
        else
            PlayerData.clearPlayerData(player);
    }
}
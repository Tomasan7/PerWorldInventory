package cz.tomasan7.perworldinventory.Events;

import cz.tomasan7.perworldinventory.other.groups.Group;
import cz.tomasan7.perworldinventory.other.playerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class WorldSwitch implements Listener
{

    @EventHandler
    public void onWorldSwitch (PlayerTeleportEvent event)
    {
        Player player = event.getPlayer();

        Group fromGroup = Group.getGroupByWorld(event.getFrom().getWorld().getName(), true);
        Group toGroup = Group.getGroupByWorld(event.getTo().getWorld().getName(), true);

        if (fromGroup.equals(toGroup))
            return;

        fromGroup.savePlayerData(player);

        PlayerData playerData = toGroup.getPlayerData(player);

        if (playerData != null)
            PlayerData.setPlayerData(player, playerData, toGroup.getSavedData());
        else
            PlayerData.clearPlayerData(player);
    }
}
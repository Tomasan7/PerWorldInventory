package cz.tomasan7.perworldinventory.events;

import cz.tomasan7.perworldinventory.other.PlayerData;
import cz.tomasan7.perworldinventory.other.Group;
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

        if (!fromGroup.savePlayerData(player).successful)
            event.setCancelled(true);

        PlayerData playerData = toGroup.getPlayerData(player);

        if (playerData != null)
            PlayerData.setPlayerData(player, playerData, toGroup.getSavedData());
        else
            PlayerData.clearPlayerData(player);
    }
}
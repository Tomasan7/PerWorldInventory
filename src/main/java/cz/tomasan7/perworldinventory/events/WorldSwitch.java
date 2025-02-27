package cz.tomasan7.perworldinventory.events;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.playerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldSwitch implements Listener
{
    @EventHandler
    public void onWorldSwitch (PlayerChangedWorldEvent event)
    {
        Player player = event.getPlayer();

        Group fromGroup = Group.getGroupByWorld(event.getFrom().getName(), true);
        Group toGroup = Group.getGroupByWorld(player.getWorld().getName(), true);

        if (fromGroup.equals(toGroup))
            return;

        fromGroup.savePlayerDataAsync(player);

        PlayerData playerData = toGroup.getPlayerData(player);

        if (playerData != null)
            PlayerData.setPlayerData(player, playerData, toGroup.getSavedData());
        else
            PlayerData.clearPlayerData(player);
    }
}
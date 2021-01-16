package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.Menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class OpenMenuQueueRunnable extends BukkitRunnable
{
    private final Map<Player, Menu> openMenuQueue;

    public OpenMenuQueueRunnable (Map<Player, Menu> openMenuQueue)
    {
        this.openMenuQueue = openMenuQueue;
    }

    @Override
    public void run ()
    {
        for (Map.Entry<Player, Menu> entry : openMenuQueue.entrySet())
        {
            entry.getValue().openMenu(entry.getKey());
            openMenuQueue.remove(entry.getKey());
        }

        if (openMenuQueue.isEmpty())
        {
            cancel();
            return;
        }
    }
}

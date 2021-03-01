package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ResponseManager implements Listener
{
    private static final Map<Player, ResponseWaiter> waiters;
    private static ResponseWaiterRunnable waitersRunnable;

    static
    {
        waiters = new HashMap<>();
        waitersRunnable = null;
    }

    public static void AddWaiter (Player player, ResponseWaiter waiter)
    {
        waiters.put(player, waiter);

        if (!isRunnableRunning(waitersRunnable))
        {
            waitersRunnable = new ResponseWaiterRunnable(waiters);
            waitersRunnable.runTaskTimerAsynchronously(PerWorldInventory.getInstance(), 0L, 20L);
        }
    }

    public static boolean processResponse (Player player, String response)
    {
        ResponseWaiter responseWaiter = waiters.get(player);

        if (responseWaiter == null)
            return false;

        if (response.equals("cancel"))
        {
            Messages.send(player, responseWaiter.getCancelMessage());
            waiters.remove(player);
            return true;
        }

        player.sendMessage(responseWaiter.getAction().apply(response));

        waiters.remove(player);

        Menu menuToOpen = responseWaiter.getMenuToOpen();

        if (menuToOpen != null)
        {
            //if (menuToOpen instanceof PaginatedMenu)
            menuToOpen.Setup();
            menuToOpen.openMenu(player);
        }

        return true;
    }

    private static boolean isWaited (Player player)
    {
        return waiters.containsKey(player);
    }

    private static boolean isRunnableRunning (BukkitRunnable runnable)
    {
        if (runnable == null)
            return false;

        try
        {
            return !runnable.isCancelled();
        }
        catch (IllegalStateException exception)
        {
            return false;
        }
    }

    @EventHandler
    public void onResponse (AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();
        String message = event.getMessage();

        event.setCancelled(ResponseManager.processResponse(player, message));
    }
}

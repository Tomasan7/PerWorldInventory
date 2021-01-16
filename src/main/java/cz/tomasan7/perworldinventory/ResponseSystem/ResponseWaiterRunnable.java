package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class ResponseWaiterRunnable extends BukkitRunnable
{
    private final Map<Player, ResponseWaiter> waiters;

    public ResponseWaiterRunnable (Map<Player, ResponseWaiter> waiters)
    {
        this.waiters = waiters;
    }

    @Override
    public void run ()
    {
        if (waiters.isEmpty())
        {
            cancel();
            return;
        }

        for (Map.Entry<Player, ResponseWaiter> entry : waiters.entrySet())
        {
            int time = entry.getValue().getTime();

            if (time <= 0)
            {
                entry.getKey().sendMessage(entry.getValue().getTimeoutMessage());
                Messages.send(entry.getKey(), entry.getValue().getTimeoutMessage());
                waiters.remove(entry.getKey());
            }
            else
                time--;

            entry.getValue().setTime(time);
        }
    }
}
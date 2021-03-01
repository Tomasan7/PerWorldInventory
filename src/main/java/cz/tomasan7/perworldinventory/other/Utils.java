package cz.tomasan7.perworldinventory.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils
{
    public static void kickAllPlayers (String kickMessage)
    {
        for (Player player : Bukkit.getOnlinePlayers())
            player.kickPlayer(Messages.server_shutdown_kick);
    }

    public static String formatText (String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}

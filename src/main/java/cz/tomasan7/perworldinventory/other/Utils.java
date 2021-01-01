package cz.tomasan7.perworldinventory.other;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils
{
    public static String formatText (String text)
    {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String escapeChars (String text)
    {
        String finalText = text.replace("'", "\\'");
        finalText = finalText.replace("\"", "\\\"");

        return finalText;
    }

    public static void serverCrash ()
    {
        for (Player player : Bukkit.getOnlinePlayers())
        {
            //player.kickPlayer(Messages.mysqlFailKick());
        }

        Bukkit.shutdown();
    }
}

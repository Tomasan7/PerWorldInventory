package cz.tomasan7.perworldinventory.other;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;


public class Messages
{
    public static String prefix;
    public static String no_permission;
    public static String server_shutdown_kick;
    public static String mysql_not_connected;
    public static String mysql_fail_kick;

    private static final String path = "Messages.";

    public static void loadMessages ()
    {
        prefix = Utils.formatText(Config.getConfig().getString(path + "prefix"));
        no_permission = Utils.formatText(Config.getConfig().getString(path + "no-permission"));
        server_shutdown_kick = Utils.formatText(Config.getConfig().getString(path + "server-shutdown-kick"));
        mysql_not_connected = Utils.formatText(Config.getConfig().getString(path + "mysql-not-connected"));
        mysql_fail_kick = Utils.formatText(Config.getConfig().getString(path + "mysql-fail-kick"));
    }

    public static void send (CommandSender sender, String message)
    {
        sender.sendMessage(prefix + message);
    }

    public static void send (CommandSender sender, TextComponent textComponent)
    {
        TextComponent result = new TextComponent(prefix);
        result.addExtra(textComponent);
        sender.spigot().sendMessage(result);
    }
}

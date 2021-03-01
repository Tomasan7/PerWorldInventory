package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
    public static boolean useMysql;
    public static long connectionTimeout;

    private static final PerWorldInventory plugin;
    private static FileConfiguration config;

    static
    {
        plugin = PerWorldInventory.getInstance();
    }

    public static FileConfiguration getConfig ()
    {
        return config;
    }

    public static void loadConfig ()
    {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        useMysql = config.getBoolean("useMySQL");
        connectionTimeout = config.getLong("MySQL.connectionTimeout");

    }

    public static void reloadConfig ()
    {
        plugin.reloadConfig();

        loadConfig();
    }

    public static void saveConfig ()
    {
        plugin.saveConfig();
    }
}

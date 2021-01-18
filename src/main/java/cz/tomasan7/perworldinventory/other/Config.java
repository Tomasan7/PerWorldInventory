package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.configuration.file.FileConfiguration;

public class Config
{
    public static boolean useMysql;

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

package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config
{
    public static boolean useMysql;

    public static FileConfiguration getConfig ()
    {
        return PerWorldInventory.getInstance().getConfig();
    }

    public static void loadConfig ()
    {
        useMysql = getConfig().getBoolean("useMySQL");
    }

    public static void reloadConfig ()
    {
        Plugin plugin = PerWorldInventory.getInstance();

        plugin.saveDefaultConfig();
        plugin.reloadConfig();

        loadConfig();
    }

    public static void saveConfig ()
    {
        Plugin plugin = PerWorldInventory.getInstance();

        plugin.saveConfig();
    }
}

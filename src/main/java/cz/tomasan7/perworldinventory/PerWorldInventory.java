package cz.tomasan7.perworldinventory;

import cz.tomasan7.perworldinventory.Commands.PerWorldInventoryCmd;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.database.Database;
import cz.tomasan7.perworldinventory.database.MySQL;
import cz.tomasan7.perworldinventory.database.SQLite;
import cz.tomasan7.perworldinventory.events.PlayerJoin;
import cz.tomasan7.perworldinventory.events.PlayerLeave;
import cz.tomasan7.perworldinventory.events.WorldSwitch;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.MenuEvents;
import cz.tomasan7.perworldinventory.other.Config;
import cz.tomasan7.perworldinventory.other.Messages;
import cz.tomasan7.perworldinventory.other.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class PerWorldInventory extends JavaPlugin
{
    private static PerWorldInventory instance;
    private static Database mainDatabase;
    private static Database tempDatabase;
    public static final String tempDatabaseFileName = "TempDatabase";

    private static Economy economy;

    public PerWorldInventory ()
    {
        instance = this;
    }

    @Override
    public void onEnable ()
    {
        // TODO: Delete group command confirmation.

        RegisterCommandsAndEvents();

        Config.loadConfig();
        Messages.loadMessages();

        if (Config.getConfig().getBoolean("useMySQL"))
            mainDatabase = new MySQL(Config.getConfig().getConfigurationSection("MySQL"));
        else
            mainDatabase = new SQLite("Database", true);

        mainDatabase.connect();
        Group.loadGroups();
        if (mainDatabase instanceof MySQL)
            Group.checkForTempDatabase();

        if (!setupEconomy())
        {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable ()
    {
        mainDatabase.disconnect();
        Utils.kickAllPlayers(Messages.server_shutdown_kick);
        Group.saveGroups();
    }

    private void RegisterCommandsAndEvents ()
    {
        getServer().getPluginManager().registerEvents(new WorldSwitch(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);
        getServer().getPluginManager().registerEvents(new ResponseManager(), this);
        //#region PerWorldInventory
        getCommand("perworldinventory").setExecutor(new PerWorldInventoryCmd());
        getCommand("perworldinventory").setTabCompleter(new PerWorldInventoryCmd());
        //#endregion
    }

    public static void DatabaseConnected (boolean isMysql)
    {
        Group.loadGroups();

        if (isMysql)
            Group.checkForTempDatabase();
    }

    public static Database getMainDatabase ()
    {
        return mainDatabase;
    }

    public static void setMainDatabase (Database mainDatabase)
    {
        PerWorldInventory.mainDatabase = mainDatabase;
    }

    public static Database getTempDatabase ()
    {
        if (tempDatabase != null)
            return tempDatabase;

        tempDatabase = new SQLite(tempDatabaseFileName, false);
        tempDatabase.connect();

        for (String group : Group.getGroupsNames(true))
        {
            try
            {
                PreparedStatement statement = tempDatabase.getConnection().prepareStatement(tempDatabase.create_table("pwi_" + group));
                statement.executeUpdate();
                statement.close();
            }
            catch (SQLException exception)
            {
                exception.printStackTrace();
            }
        }

        return tempDatabase;
    }

    private boolean setupEconomy ()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
        {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy ()
    {
        return economy;
    }

    public static PerWorldInventory getInstance ()
    {
        return instance;
    }
}

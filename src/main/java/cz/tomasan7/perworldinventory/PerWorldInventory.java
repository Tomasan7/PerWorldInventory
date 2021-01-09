package cz.tomasan7.perworldinventory;

import cz.tomasan7.perworldinventory.commands.PerWorldInventoryCmd;
import cz.tomasan7.perworldinventory.events.PlayerJoin;
import cz.tomasan7.perworldinventory.events.PlayerLeave;
import cz.tomasan7.perworldinventory.events.WorldSwitch;
import cz.tomasan7.perworldinventory.menus.MenuEvents;
import cz.tomasan7.perworldinventory.other.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class PerWorldInventory extends JavaPlugin
{
    private static PerWorldInventory instance;
    public static Database mainDatabase;
    private static Database tempDatabase;

    public PerWorldInventory ()
    {
        instance = this;
    }

    @Override
    public void onEnable ()
    {
        // TODO: Delete group command confirmation.

        RegisterCommandsAndEvents();

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        Messages.loadMessages();
        Config.loadConfig();

        if (Config.getConfig().getBoolean("useMySQL"))
            mainDatabase = new MySQL(Config.getConfig().getConfigurationSection("MySQL"));
        else
            mainDatabase = new SQLite("Database", true);

        mainDatabase.Connect(15);
    }

    @Override
    public void onDisable ()
    {
        //Groups.kickAndSaveAllPlayers(Messages.serverShutdownKick());
        mainDatabase.Disconnect();
        Group.saveGroups();
    }

    private void RegisterCommandsAndEvents ()
    {
        getServer().getPluginManager().registerEvents(new WorldSwitch(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        //getServer().getPluginManager().registerEvents(new GroupsGUI(), this);
        getServer().getPluginManager().registerEvents(new MenuEvents(), this);
        //#region PerWorldInventory
        getCommand("perworldinventory").setExecutor(new PerWorldInventoryCmd());
        getCommand("perworldinventory").setTabCompleter(new PerWorldInventoryCmd());
        //#endregion
    }

    public static void DatabaseConnected(boolean isMysql)
    {
        Group.loadGroups();

        if (isMysql)
            Group.checkForTempDatabase();
    }

    public static Database getTempDatabase ()
    {
        if (tempDatabase != null && tempDatabase.isConnected())
            return tempDatabase;

        tempDatabase = new SQLite("TempDatabase", false);
        tempDatabase.Connect(0);

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

    public static PerWorldInventory getInstance ()
    {
        return instance;
    }
}

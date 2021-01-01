package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQL extends Database
{
    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    public MySQL (ConfigurationSection authSection)
    {
        host = authSection.getString("host");
        port = authSection.getInt("port");
        database = authSection.getString("database");
        username = authSection.getString("username");
        password = authSection.getString("password");
    }

    @Override
    public void Connect (int reconnectAttemptDelay)
    {
        if (isConnected())
            return;

        Logger logger = PerWorldInventory.getInstance().getLogger();

        BukkitRunnable runnable = new BukkitRunnable()
        {
            @Override
            public void run ()
            {
                if (isConnected())
                    return;

                if (!Config.useMysql)
                {
                    cancel();
                    return;
                }

                try
                {
                    connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
                    logger.info("§2§lConnected to MySQL database.");
                    PerWorldInventory.DatabaseConnected(true);
                }
                catch (SQLException e)
                {
                    logger.severe("§c§lCould not connect to mysql.");
                    Bukkit.getScheduler().runTask(PerWorldInventory.getInstance(), Group::databaseFail);
                }
            }
        };
        runnable.runTaskTimerAsynchronously(PerWorldInventory.getInstance(), 0, reconnectAttemptDelay*20);
    }

    @Override
    public String create_table (String tableName)
    {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "username VARCHAR(16) NOT NULL," +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                "data MEDIUMBLOB)";
    }

    @Override
    public String rename_table (String tableName, String toName)
    {
        return "RENAME TABLE " + tableName + " TO " + toName;
    }

    @Override
    public String insert_player_data (String tableName)
    {
        return "INSERT INTO " + tableName + " (username,uuid,data) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE username = VALUES(username),data = VALUES(data)";
    }

    @Override
    public String select_player_data_by_uuid (String tableName)
    {
        return "SELECT data FROM " + tableName + " WHERE uuid=?";
    }

    @Override
    public String select_player_data_by_name (String tableName)
    {
        return "SELECT data FROM " + tableName + " WHERE username=?";
    }
}
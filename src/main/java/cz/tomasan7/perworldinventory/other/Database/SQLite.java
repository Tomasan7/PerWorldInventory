package cz.tomasan7.perworldinventory.other.Database;

import cz.tomasan7.perworldinventory.PerWorldInventory;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLite implements Database
{
    private Connection connection;

    private final String fileName;
    private final boolean isMain;

    public SQLite (String fileName, boolean isMain)
    {
        this.fileName = fileName;
        this.isMain = isMain;
    }

    @Override
    public void Connect (int reconnectAttemptDelay)
    {
        if (isConnected())
            return;

        File file = new File(PerWorldInventory.getInstance().getDataFolder(), fileName + ".db");

        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException exception)
            {
                exception.printStackTrace();
                PerWorldInventory.getInstance().getLogger().log(Level.SEVERE, "File write error: " + fileName + ".db");
            }
        }
        try
        {
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
            if (isMain)
            {
                PerWorldInventory.getInstance().getLogger().info("§2§lConnected to SQLite database.");
                PerWorldInventory.DatabaseConnected(false);
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public boolean isConnected ()
    {
        try
        {
            if (connection != null && !connection.isClosed())
                return true;
        }
        catch (SQLException ignored)
        {
        }

        return false;
    }

    @Override
    public void Disconnect ()
    {
        if (!isConnected())
            return;

        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection ()
    {
        return connection;
    }

    @Override
    public String create_table (String tableName)
    {
        return "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "username VARCHAR(16) NOT NULL," +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                "data MEDIUMBLOB)";
    }

    @Override
    public String rename_table (String tableName, String toName)
    {
        return "ALTER TABLE " + tableName + " RENAME TO " + toName;
    }

    @Override
    public String insert_player_data (String tableName)
    {
        return "INSERT OR REPLACE INTO " + tableName + " (username,uuid,data) VALUES (?, ?, ?)";
    }

    @Override
    public String select_player_data_by_uuid (String tableName)
    {
        return "SELECT data FROM " + tableName + " WHERE uuid = ?";
    }

    @Override
    public String select_player_data_by_name (String tableName)
    {
        return "SELECT data FROM " + tableName + " WHERE username = ?";
    }
}
package cz.tomasan7.perworldinventory.database;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.other.Error;
import cz.tomasan7.perworldinventory.other.Logger;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite implements Database
{
    private final String fileName;
    private final File file;
    private final boolean isMain;

    public SQLite (String fileName, boolean isMain)
    {
        super();
        this.fileName = fileName;
        this.file = new File(PerWorldInventory.getInstance().getDataFolder(), fileName + ".db");
        this.isMain = isMain;
    }

    @Override
    public void connect ()
    {
        try (Connection connection = getConnection())
        {
            connection.isValid(5);
            Logger.success("Connected to SQLite database.");
        }
        catch (SQLException exception)
        {
            Logger.error(Error.SQLITE_DATABASE_CONNECT_FAIL, exception.getMessage());
        }

        /*try
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
        }*/
    }

    @Override
    public void disconnect ()
    {
    }

    public boolean isMain ()
    {
        return isMain;
    }

    @Override
    public Connection getConnection () throws SQLException
    {
        String url = "jdbc:sqlite:" + file.getPath();
        return DriverManager.getConnection(url);
    }

    @Override
    public String create_table (String tableName)
    {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_PREFIX + tableName + " (" +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "username VARCHAR(16) NOT NULL," +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                "data MEDIUMBLOB)";
    }

    @Override
    public String rename_table (String tableName, String toName)
    {
        return "ALTER TABLE " + TABLE_PREFIX + tableName + " RENAME TO " + toName;
    }

    @Override
    public String delete_table (String tableName)
    {
        return "DROP TABLE " + TABLE_PREFIX + tableName;
    }

    @Override
    public String insert_player_data (String tableName)
    {
        return "INSERT OR REPLACE INTO " + TABLE_PREFIX + tableName + " (username,uuid,data) VALUES (?, ?, ?)";
    }

    @Override
    public String select_player_data_by_uuid (String tableName)
    {
        return "SELECT data FROM " + TABLE_PREFIX + tableName + " WHERE uuid = ?";
    }

    @Override
    public String select_player_data_by_name (String tableName)
    {
        return "SELECT data FROM " + TABLE_PREFIX + tableName + " WHERE username = ?";
    }
}
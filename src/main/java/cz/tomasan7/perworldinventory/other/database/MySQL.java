package cz.tomasan7.perworldinventory.other.database;

import com.zaxxer.hikari.HikariDataSource;
import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.other.groups.Group;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MySQL implements Database
{
    private HikariDataSource hikari;

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

        ConfigurationSetup();
    }

    @Override
    public Connection getConnection () throws SQLException
    {
        return hikari.getConnection();
    }

    @Override
    public void Connect (int reconnectAttemptDelay)
    {
        if (isConnected())
            return;

        Logger logger = PerWorldInventory.getInstance().getLogger();

        try
        {
            hikari.getConnection().isValid(10);
            logger.info("§2§lConnected to MySQL database.");
            PerWorldInventory.DatabaseConnected(true);
        }
        catch (SQLException exception)
        {
            logger.severe("§c§lCould not connect to mysql.");
            Bukkit.getScheduler().runTask(PerWorldInventory.getInstance(), Group::databaseFail);
        }
    }

    public void Disconnect ()
    {
        if (!isConnected())
            return;

        hikari.close();
    }

    public boolean isConnected ()
    {
        return hikari != null && hikari.isRunning();
    }

    private void ConfigurationSetup ()
    {
        hikari = new HikariDataSource();

        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");

        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        hikari.addDataSourceProperty("useSSL", false);
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikari.addDataSourceProperty("useServerPrepStmts", true);
        hikari.addDataSourceProperty("useLocalSessionState", true);
        hikari.addDataSourceProperty("rewriteBatchedStatements", true);
        hikari.addDataSourceProperty("cacheResultSetMetadata", true);
        hikari.addDataSourceProperty("cacheServerConfiguration", true);
        hikari.addDataSourceProperty("elideSetAutoCommits", true);
        hikari.addDataSourceProperty("maintainTimeStats", true);
        hikari.setAutoCommit(true);
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
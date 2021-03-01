package cz.tomasan7.perworldinventory.database;

import com.zaxxer.hikari.HikariDataSource;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.other.Config;
import cz.tomasan7.perworldinventory.other.Error;
import cz.tomasan7.perworldinventory.other.Logger;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQL implements Database
{
    private final HikariDataSource hikari;

    private final String host;
    private final int port;
    private final String database;
    private final String username;
    private final String password;

    {
        hikari = new HikariDataSource();
    }

    public MySQL (ConfigurationSection authSection)
    {
        host = authSection.getString("host");
        port = authSection.getInt("port");
        database = authSection.getString("database");
        username = authSection.getString("username");
        password = authSection.getString("password");

        setup();
    }

    @Override
    public Connection getConnection () throws SQLException
    {
        return hikari.getConnection();
    }

    @Override
    public void connect ()
    {
        try (Connection connection = getConnection())
        {
            connection.isValid(5);
            Logger.success("Connected to MySQL database.");
        }
        catch (SQLException exception)
        {
            Logger.error(Error.MYSQL_DATABASE_CONNECT_FAIL, exception.getMessage());
            Group.databaseFail();
        }
    }

    public void disconnect ()
    {
        hikari.close();
    }

    protected void setup ()
    {
        //hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);

        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        //hikari.addDataSourceProperty("databaseName", database);
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
        hikari.setConnectionTimeout(Config.connectionTimeout);
        hikari.setAutoCommit(true);
    }

    @Override
    public String create_table (String tableName)
    {
        return "CREATE TABLE IF NOT EXISTS " + TABLE_PREFIX + tableName + " (" +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP," +
                "username VARCHAR(16) NOT NULL," +
                "uuid VARCHAR(36) NOT NULL PRIMARY KEY," +
                "data MEDIUMBLOB)";
    }

    @Override
    public String rename_table (String tableName, String toName)
    {
        return "RENAME TABLE " + TABLE_PREFIX + tableName + " TO " + toName;
    }

    @Override
    public String delete_table (String tableName)
    {
        return "DROP TABLE " + TABLE_PREFIX + tableName;
    }

    @Override
    public String insert_player_data (String tableName)
    {
        return "INSERT INTO " + TABLE_PREFIX + tableName + " (username,uuid,data) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE username = VALUES(username),data = VALUES(data)";
    }

    @Override
    public String select_player_data_by_uuid (String tableName)
    {
        return "SELECT data FROM " + TABLE_PREFIX + tableName + " WHERE uuid=?";
    }

    @Override
    public String select_player_data_by_name (String tableName)
    {
        return "SELECT data FROM " + TABLE_PREFIX + tableName + " WHERE username=?";
    }
}
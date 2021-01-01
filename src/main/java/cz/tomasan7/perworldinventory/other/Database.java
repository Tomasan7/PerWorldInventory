package cz.tomasan7.perworldinventory.other;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database
{
    protected Connection connection;

    public Connection getConnection ()
    {
        return connection;
    }

    public abstract void Connect (int reconnectAttemptDelay);

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

    public abstract String create_table (String tableName);

    public abstract String rename_table (String tableName, String toName);

    public abstract String insert_player_data (String tableName);

    public abstract String select_player_data_by_uuid (String tableName);

    public abstract String select_player_data_by_name (String tableName);
}

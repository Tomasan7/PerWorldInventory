package cz.tomasan7.perworldinventory.other.Database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database
{
    Connection getConnection () throws SQLException;

    void Connect (int reconnectAttemptDelay);

    boolean isConnected ();

    void Disconnect ();

    String create_table (String tableName);

    String rename_table (String tableName, String toName);

    String insert_player_data (String tableName);

    String select_player_data_by_uuid (String tableName);

    String select_player_data_by_name (String tableName);
}

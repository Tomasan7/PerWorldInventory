package cz.tomasan7.perworldinventory.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database
{
    String TABLE_PREFIX = "pwi_";

    Connection getConnection () throws SQLException;

    void connect ();

    void disconnect ();

    String create_table (String tableName);

    String rename_table (String tableName, String toName);

    String delete_table (String tableName);

    String insert_player_data (String tableName);

    String select_player_data_by_uuid (String tableName);

    String select_player_data_by_name (String tableName);
}

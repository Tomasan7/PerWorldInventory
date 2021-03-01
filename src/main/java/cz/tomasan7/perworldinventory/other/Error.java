package cz.tomasan7.perworldinventory.other;

public enum Error
{
    MYSQL_DATABASE_CONNECT_FAIL("Could not connect to SQLite database, please check your authentication."),
    SQLITE_DATABASE_CONNECT_FAIL("Could not connect to MySQL database, please check your authentication."),
    TEMP_DATABASE_FILE_DELETE_FAIL("Could not delete TempDatabase.db file.");

    public final String message;

    Error (String message)
    {
        this.message = message;
    }
}

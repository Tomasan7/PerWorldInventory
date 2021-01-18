package cz.tomasan7.perworldinventory.other;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Group
{
    static
    {
        groups = new ArrayList<Group>();
        groupsFile = new YamlConfiguration();
    }

    private static List<Group> groups;
    private static final String DEFAULT_GROUP_NAME = "default";

    private static YamlConfiguration groupsFile;

    // Group properties
    private String name;
    private List<String> worlds;
    private SavedData savedData;

    /**
     * Main group constructor.
     *
     * @param groupName Name of the group.
     */
    private Group (String groupName)
    {
        this.name = groupName;
        this.worlds = new ArrayList<String>();
        this.savedData = new SavedData();
    }

    /**
     * Constructor creating group from group in configurationSection.
     *
     * @param section ConfigurationSection to create group from.
     */
    private Group (ConfigurationSection section)
    {
        this.name = section.getName();
        this.worlds = section.getStringList("worlds");
        this.savedData = new SavedData(section.getConfigurationSection("savedData"));
    }

    /**
     * @return Name of this group.
     */
    public String getName ()
    {
        return this.name;
    }

    /**
     * Sets the name of this group in config and mysql.
     *
     * @param newName The name you want to set it to.
     */
    public GroupActionResult setName (String newName)
    {
        if (!this.name.matches("\\w{0,20}"))
            return new GroupActionResult(false, "§cInvalid group name. ([A-Za-z_] length: 0-20)");

        if (Group.getGroup(newName) != null)
            return new GroupActionResult(false, "§cGroup §l" + newName + " §calready exists.");

        try
        {
            Database database = PerWorldInventory.mainDatabase;

            PreparedStatement statement = database.getConnection().prepareStatement(database.rename_table("pwi_" + this.name, "pwi_" + newName));
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return new GroupActionResult(false, Messages.mysql_not_connected);
        }

        String oldName = this.name;

        this.name = newName;

        return new GroupActionResult(true, "§2Group §l" + oldName + " §2was renamed to §l " + this.name + "§2.");
    }

    /**
     * Adds world to this group.
     *
     * @param worldName Name of the world to add.
     * @return GroupActionResult.
     */
    public GroupActionResult addWorld (String worldName)
    {
        if (Bukkit.getWorld(worldName) == null)
            return new GroupActionResult(false, "§cWorld §l" + worldName + " §cdoesn't exist.");

        if (this.equals(getDefaultGroup()))
            return new GroupActionResult(false, "§cCan't add worlds to default group.");

        if (worlds.contains(worldName))
            return new GroupActionResult(false, "§cGroup §l" + this.name + " §calready contains world §l" + worldName + "§c.");

        Group groupWithWorld = getGroupByWorld(worldName, false);

        if (groupWithWorld != null)
            return new GroupActionResult(false, "§cWorld §l" + worldName + " §cis contained in group §l" + groupWithWorld.getName() + "§c.");

        worlds.add(worldName);

        return new GroupActionResult(true, "§2World §2§l" + worldName + " §2was added to group §2§l" + this.name + "§2.");
    }

    /**
     * @param worldName Name of the world to remove from this group.
     * @return GroupActionResult.
     */
    public GroupActionResult removeWorld (String worldName)
    {
        if (this.equals(getDefaultGroup()))
            return new GroupActionResult(false, "§2You can't remove worlds from default group.");

        if (worlds.remove(worldName))
            return new GroupActionResult(true, "§2World §l" + worldName + " §2was deleted from group §l" + this.name + "§2.");
        else
            return new GroupActionResult(false, "§cGroup §l" + this.name + " §cdoesn't contain world §l" + worldName + "§c.");
    }

    /**
     * Checks regex of the group name and existence of group with same and if it passes it creates group witch group creating constructor.
     *
     * @param groupName Name of the group to create.
     * @return GroupActionResult.
     */
    public static GroupActionResult createGroup (String groupName)
    {
        if (!groupName.matches("\\w{0,20}"))
            return new GroupActionResult(false, "§cInvalid group name. ([A-Za-z_] length: 0-20)");

        if (Group.getGroup(groupName) != null)
            return new GroupActionResult(false, "§cGroup §l" + groupName + " §calready exists.");

        try
        {
            Database database = PerWorldInventory.mainDatabase;

            PreparedStatement statement = database.getConnection().prepareStatement(database.create_table("pwi_" + groupName));
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        Group newGroup = new Group(groupName);

        groups.add(newGroup);

        return new GroupActionResult(true, newGroup, "§2Group §l" + groupName + " §2was created.");
    }

    /**
     * Deletes group from groups config as well as from MySQL.
     *
     * @return GroupActionResult.
     */
    public GroupActionResult Delete ()
    {
        if (this.equals(getDefaultGroup()))
            return new GroupActionResult(false, "§cCan't delete default group.");

        try
        {
            PreparedStatement statement = PerWorldInventory.mainDatabase.getConnection().prepareStatement("DROP TABLE pwi_" + this.name);
            statement.executeUpdate();
            statement.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        groups.remove(this);

        return new GroupActionResult(true, "§2Group §l" + this.name + " §2was deleted.");
    }

    /**
     * Kicks all players, saves their data to TempDatabase and shutdowns server.
     */
    public static void databaseFail ()
    {
        PerWorldInventory.mainDatabase = PerWorldInventory.getTempDatabase();

        for (Player player : Bukkit.getOnlinePlayers())
            player.kickPlayer(Messages.mysql_fail_kick);

        Bukkit.shutdown();
    }

    /**
     * Checks if TempDatabase does exist. If it does, it transfers all the data from it to main database.
     */
    public static void checkForTempDatabase ()
    {
        File tempDatabaseFile = new File(PerWorldInventory.getInstance().getDataFolder(), "TempDatabase.db");

        if (!tempDatabaseFile.exists())
            return;

        Database mysql = PerWorldInventory.mainDatabase;
        Database sqlite = new SQLite("TempDatabase", false);
        sqlite.Connect(0);

        try
        {
            PreparedStatement sqliteTablesStmt = sqlite.getConnection().prepareStatement("SELECT name FROM main.sqlite_master WHERE type='table'");
            ResultSet sqliteTablesRs = sqliteTablesStmt.executeQuery();

            while (sqliteTablesRs.next())
            {
                String table = sqliteTablesRs.getString(1);

                PreparedStatement sqliteRecordsStmt = sqlite.getConnection().prepareStatement("SELECT * FROM " + table);
                ResultSet sqliteRecordsRs = sqliteRecordsStmt.executeQuery();

                while (sqliteRecordsRs.next())
                {
                    PreparedStatement mysqlRecordsInsertStmt = mysql.getConnection().prepareStatement(mysql.insert_player_data(table));
                    mysqlRecordsInsertStmt.setString(1, sqliteRecordsRs.getString("username"));
                    mysqlRecordsInsertStmt.setString(2, sqliteRecordsRs.getString("uuid"));
                    mysqlRecordsInsertStmt.setString(3, sqliteRecordsRs.getString("data"));

                    mysqlRecordsInsertStmt.executeUpdate();
                    mysqlRecordsInsertStmt.close();
                }
            }

            sqliteTablesRs.close();
            sqliteTablesStmt.close();
            PerWorldInventory.getInstance().getLogger().info("§2Successfully saved tempdatabase to MySQL.");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        sqlite.Disconnect();
        tempDatabaseFile.delete();
    }

    /**
     * Returns group which contains defined world.
     *
     * @param worldName     Name of the world to search for.
     * @param returnDefault Whether to return default group or null.
     * @return Group which contains defined world. If there is not any, returns default group or null.
     */
    public static Group getGroupByWorld (String worldName, boolean returnDefault)
    {
        for (Group group : groups)
        {
            for (String world : group.worlds)
            {
                if (world.equals(worldName))
                    return group;
            }
        }

        if (returnDefault)
            return getDefaultGroup();
        else
            return null;
    }

    /**
     * Creates default group in config if doesn't already exist and returns it.
     *
     * @return default group.
     */
    private static Group getDefaultGroup ()
    {
        for (Group group : groups)
        {
            if (group.getName().equals(DEFAULT_GROUP_NAME))
                return group;
        }

        return (Group) createGroup(DEFAULT_GROUP_NAME).value;
    }

    /**
     * Returns group by its name.
     * Returns null if there is not any.
     *
     * @param groupName Name of the group to search for.
     * @return The group with defined name.
     */
    public static Group getGroup (String groupName)
    {
        for (Group group : groups)
        {
            if (group.getName().equals(groupName))
                return group;
        }

        return null;
    }

    /**
     * Returns all worlds, which aren't assigned to any group.
     * If there are not any, returns null.
     *
     * @return ArrayList of worlds, which aren't assigned to any group.
     */
    public static List<String> getFreeWorlds ()
    {
        List<String> result = new ArrayList<String>();

        for (World word : Bukkit.getWorlds())
            result.add(word.getName());

        for (Group group : groups)
        {
            for (String world : group.worlds)
                result.remove(world);
        }

        if (result.isEmpty())
            return null;

        return result;
    }

    /**
     * Returns ArrayList of all groups.
     *
     * @return ArrayList with all groups.
     */
    public static List<Group> getGroups ()
    {
        return groups;
    }

    /**
     * Returns list with names of all groups.
     *
     * @param includeDefaultGroup Whether to return default group as well or not.
     * @return List of all groups names.
     */
    public static List<String> getGroupsNames (boolean includeDefaultGroup)
    {
        List<String> groupsNames = new ArrayList<String>();

        for (Group group : groups)
        {
            if (group.equals(getDefaultGroup()) && !includeDefaultGroup)
                continue;

            groupsNames.add(group.getName());
        }

        return groupsNames;
    }

    /**
     * @return List of worlds, this group contains.
     */
    public List<String> getWorlds ()
    {
        return worlds;
    }

    /**
     * @return SavedData property of this group.
     */
    public SavedData getSavedData ()
    {
        return savedData;
    }

    /**
     * Loads all groups to groups list from groups.yml file.
     */
    public static void loadGroups ()
    {
        File groupsFile = new File(PerWorldInventory.getInstance().getDataFolder(), "groups.yml");
        Group.groupsFile = new YamlConfiguration();

        try
        {
            if (!groupsFile.exists())
                groupsFile.createNewFile();

            Group.groupsFile.load(groupsFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

        groups = new ArrayList<Group>();

        for (String groupPath : Group.groupsFile.getKeys(false))
        {
            groups.add(new Group(Group.groupsFile.getConfigurationSection(groupPath)));
        }

        getDefaultGroup();
    }

    /**
     * Saves all groups to groups.yml file.
     */
    public static void saveGroups ()
    {
        groupsFile = new YamlConfiguration();

        for (Group group : groups)
        {
            ConfigurationSection section = group.Serialize();

            groupsFile.set(section.getName(), section);
        }

        File file = new File(PerWorldInventory.getInstance().getDataFolder(), "groups.yml");

        try
        {
            groupsFile.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Serializes the group to ConfigurationSection
     * @return Serialized ConfigurationSection.
     */
    public ConfigurationSection Serialize ()
    {
        //TODO this method
        ConfigurationSection section = new YamlConfiguration().createSection(this.name);

        section.set("savedData", this.savedData.Serialize());
        section.set("worlds", this.worlds);

        return section;
    }

    /**
     * Saves player's data to this group.
     *
     * @param player Player, whose data to save.
     */
    public GroupActionResult savePlayerData (Player player)
    {
        GroupActionResult groupActionResult = null;

        try
        {
            String data = PlayerData.getPlayerData(player).Serialize();

            Database database = PerWorldInventory.mainDatabase;

            PreparedStatement statement = database.getConnection().prepareStatement(database.insert_player_data("pwi_" + this.name));
            statement.setString(1, player.getName());
            statement.setString(2, player.getUniqueId().toString());
            statement.setString(3, data);

            statement.executeUpdate();
            statement.close();

            groupActionResult = new GroupActionResult(true, "Nice");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            groupActionResult = new GroupActionResult(false, exception.getMessage());
            databaseFail();
        }

        return groupActionResult;
    }

    /**
     * Get data of player, saved in this group.
     *
     * @param player Player, whose data to get.
     * @return PlayerData of the player.
     */
    public PlayerData getPlayerData (Player player)
    {
        try
        {
            Database database = PerWorldInventory.mainDatabase;

            PreparedStatement statement = database.getConnection().prepareStatement(database.select_player_data_by_uuid("pwi_" + this.name));
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return new PlayerData(resultSet.getString("data"));

            resultSet.close();
            statement.close();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            databaseFail();
        }

        return null;
    }

    /**
     * Get data of player, saved in this group.
     *
     * @param playerName Name of player, whose data to get.
     * @return PlayerData of the player.
     */
    public PlayerData getPlayerData (String playerName)
    {
        try
        {
            Database database = PerWorldInventory.mainDatabase;

            PreparedStatement statement = database.getConnection().prepareStatement(database.select_player_data_by_name("pwi_" + this.name));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return new PlayerData(resultSet.getString("data"));

            resultSet.close();
            statement.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            databaseFail();
        }

        return null;
    }
}
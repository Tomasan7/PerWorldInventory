package cz.tomasan7.perworldinventory.groups;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.database.Database;
import cz.tomasan7.perworldinventory.database.SQLite;
import cz.tomasan7.perworldinventory.other.Error;
import cz.tomasan7.perworldinventory.other.*;
import cz.tomasan7.perworldinventory.playerData.PlayerData;
import cz.tomasan7.perworldinventory.savedData.SavedData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Group
{
    private static final String GROUPS_FILE_NAME = "groups";

    static
    {
        groups = new HashSet<>();
        groupsFile = new YamlConfiguration();
    }

    private static Set<Group> groups;
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

        Database database = PerWorldInventory.getMainDatabase();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(database.rename_table(this.name, newName)))
        {
            statement.executeUpdate();
        }
        catch (SQLException exception)
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
        //TODO: make every mysql action Async.
        Bukkit.broadcastMessage("§6 From groups.yml: " + groupName); //TODO: Debug.

        if (!groupName.matches("\\w{0,20}"))
            return new GroupActionResult(false, "§cInvalid group name. ([A-Za-z_] length: 0-20)");

        if (Group.getGroup(groupName) != null)
            return new GroupActionResult(false, "§cGroup §l" + groupName + " §calready exists.");

        Database database = PerWorldInventory.getMainDatabase();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(database.create_table(groupName)))
        {
            statement.executeUpdate();
        }
        catch (SQLException exception)
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
    public GroupActionResult delete ()
    {
        if (this.equals(getDefaultGroup()))
            return new GroupActionResult(false, "§cCan't delete default group.");

        Database database = PerWorldInventory.getMainDatabase();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(database.delete_table(this.name)))
        {
            statement.executeUpdate();
        }
        catch (SQLException exception)
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
        PerWorldInventory.setMainDatabase(PerWorldInventory.getTempDatabase());

        Utils.kickAllPlayers(Messages.mysql_fail_kick);

        Bukkit.shutdown();
    }

    /**
     * Checks if TempDatabase does exist. If it does, it transfers all the data from it to main database.
     */
    public static void checkForTempDatabase ()
    {
        File tempDatabaseFile = new File(PerWorldInventory.getInstance().getDataFolder(), PerWorldInventory.tempDatabaseFileName + ".db");

        if (!tempDatabaseFile.exists())
            return;

        Database mainDatabase = PerWorldInventory.getMainDatabase();
        Database tempDatabase = new SQLite(PerWorldInventory.tempDatabaseFileName, false);
        tempDatabase.connect();

        try (Connection mainConnection = mainDatabase.getConnection();
             Connection tempConnection = tempDatabase.getConnection();
             PreparedStatement tempTablesStmt = tempConnection.prepareStatement("SELECT name FROM main.sqlite_master WHERE type='table'");
             ResultSet tempTablesRs = tempTablesStmt.executeQuery())
        {

            while (tempTablesRs.next())
            {
                String table = tempTablesRs.getString(1);

                try (PreparedStatement tempRecordsStmt = tempConnection.prepareStatement("SELECT * FROM " + table);
                     ResultSet tempRecordsRs = tempRecordsStmt.executeQuery())
                {
                    while (tempRecordsRs.next())
                    {
                        try (PreparedStatement mainRecordsInsertStmt = mainConnection.prepareStatement(mainDatabase.insert_player_data(table)))
                        {
                            mainRecordsInsertStmt.setString(1, tempRecordsRs.getString("username"));
                            mainRecordsInsertStmt.setString(2, tempRecordsRs.getString("uuid"));
                            mainRecordsInsertStmt.setString(3, tempRecordsRs.getString("data"));
                            mainRecordsInsertStmt.executeUpdate();
                        }
                    }
                }
            }

            Logger.info("§2Successfully loaded temp database to main database.");
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }

        tempDatabase.disconnect();

        boolean deleteResult = tempDatabaseFile.delete();
        if (!deleteResult)
            Logger.error(Error.TEMP_DATABASE_FILE_DELETE_FAIL);
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
        Group group = groups.stream().filter(sgroup -> sgroup.worlds.stream().anyMatch(worldName::equals)).findAny().orElse(null);

        if (group == null && returnDefault)
            return getDefaultGroup();

        return group;

        /*
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
            return null;*/
    }

    /**
     * Creates default group in config if doesn't already exist and returns it.
     *
     * @return default group.
     */
    private static Group getDefaultGroup ()
    {
        Group defaultGroup = groups.stream().filter(group -> group.getName().equals(DEFAULT_GROUP_NAME)).findAny().orElse(null);

        if (defaultGroup != null)
            return defaultGroup;
        else
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
        return groups.stream().filter(group -> group.getName().equals(groupName)).findAny().orElse(null);
    }

    /**
     * Returns all worlds, which aren't assigned to any group.
     * If there are not any, returns null.
     *
     * @return ArrayList of worlds, which aren't assigned to any group.
     */
    public static List<String> getFreeWorlds ()
    {
        //TODO: Change to stream technique.
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
    public static Set<Group> getGroups ()
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
        if (includeDefaultGroup)
            return groups.stream().map(group -> group.getName()).collect(Collectors.toList());
        else
            return groups.stream().filter(group -> !group.equals(getDefaultGroup())).map(group -> group.getName()).collect(Collectors.toList());
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
        File groupsFile = new File(PerWorldInventory.getInstance().getDataFolder(), GROUPS_FILE_NAME + ".yml");
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

        groups = new HashSet<>();

        for (String groupPath : Group.groupsFile.getKeys(false))
            groups.add(new Group(Group.groupsFile.getConfigurationSection(groupPath)));

        getDefaultGroup();
        createTables();
    }

    /**
     * Performs "CREATE IF NOT EXISTS" sql on all groups, so if some table gets deleted, it will be recreated.
     */
    private static void createTables ()
    {
        Database database = PerWorldInventory.getMainDatabase();

        try (Connection connection = database.getConnection())
        {
            for (Group group : groups)
            {
                try (PreparedStatement statement = connection.prepareStatement(database.create_table(group.getName())))
                {
                    statement.executeUpdate();
                }
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
        }
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
     *
     * @return Serialized ConfigurationSection.
     */
    public ConfigurationSection Serialize ()
    {
        ConfigurationSection section = new YamlConfiguration().createSection(this.name);

        section.set("savedData", this.savedData.Serialize());
        section.set("worlds", this.worlds);

        return section;
    }

    /**
     * Saves player's current data to this group.
     *
     * @param player Player, whose data to save.
     */
    public void savePlayerData (Player player)
    {
        PlayerData playerData = PlayerData.getPlayerData(player);

        Database database = PerWorldInventory.getMainDatabase();
        String sql = database.insert_player_data(name);

        String data = playerData.Serialize();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, player.getName());
            statement.setString(2, player.getUniqueId().toString());
            statement.setString(3, data);

            statement.executeUpdate();
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            Group.databaseFail();
        }
    }

    /**
     * Saves custom playerData to player.
     *
     * @param player     Player, whose data to save.
     * @param playerData Player data to save for this player.
     * @return boolean if the operation was successful or not.
     */
    public boolean savePlayerData (Player player, PlayerData playerData)
    {
        Database database = PerWorldInventory.getMainDatabase();
        String sql = database.insert_player_data(name);

        String data = playerData.Serialize();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, player.getName());
            statement.setString(2, player.getUniqueId().toString());
            statement.setString(3, data);

            statement.executeUpdate();

            return true;
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            Group.databaseFail();
        }

        return false;
    }

    /**
     * Saves player's data to this group async.
     *
     * @param player Player, whose data to save.
     */
    public void savePlayerDataAsync (Player player, Consumer<Player> successful, Consumer<Player> unsuccessful)
    {
        PlayerData playerData = PlayerData.getPlayerData(player);

        new BukkitRunnable()
        {
            @Override
            public void run ()
            {
                savePlayerData(player, playerData);
            }
        }.runTaskAsynchronously(PerWorldInventory.getInstance());
    }

    /**
     * Get data of player, saved in this group.
     *
     * @param player Player, whose data to get.
     * @return PlayerData of the player.
     */
    public PlayerData getPlayerData (Player player)
    {
        //TODO: Make this ASync.
        PlayerData playerData = null;

        Database database = PerWorldInventory.getMainDatabase();
        String sql = database.select_player_data_by_uuid(this.name);

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, player.getUniqueId().toString());

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                    playerData = new PlayerData(resultSet.getString("data"));
            }
        }
        catch (SQLException exception)
        {
            Bukkit.broadcastMessage("§6§l EXCEPTION THROWN.");
            exception.printStackTrace();
            databaseFail();
        }

        return playerData;
    }

    /**
     * Get data of player, saved in this group.
     *
     * @param playerName Name of player, whose data to get.
     * @return PlayerData of the player.
     */
    public PlayerData getPlayerData (String playerName)
    {
        //TODO: Make this ASync.
        PlayerData playerData = null;

        Database database = PerWorldInventory.getMainDatabase();
        final String sql = database.select_player_data_by_name(this.name);

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, playerName);

            try (ResultSet resultSet = statement.executeQuery())
            {
                if (resultSet.next())
                    playerData = new PlayerData(resultSet.getString("data"));
            }
        }
        catch (SQLException exception)
        {
            exception.printStackTrace();
            databaseFail();
        }

        return playerData;
    }
}
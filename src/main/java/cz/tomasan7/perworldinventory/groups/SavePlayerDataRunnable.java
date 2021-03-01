package cz.tomasan7.perworldinventory.groups;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.database.Database;
import cz.tomasan7.perworldinventory.playerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SavePlayerDataRunnable extends BukkitRunnable
{
    private final String groupName;
    private final Player player;
    private final PlayerData playerData;

    public SavePlayerDataRunnable (String groupName, Player player, PlayerData playerData)
    {
        this.groupName = groupName;
        this.player = player;
        this.playerData = playerData;
    }

    @Override
    public void run ()
    {
        Database database = PerWorldInventory.getMainDatabase();
        String sql = database.insert_player_data(groupName);

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
}

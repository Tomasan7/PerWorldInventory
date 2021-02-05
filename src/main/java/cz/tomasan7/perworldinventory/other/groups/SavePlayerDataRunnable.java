package cz.tomasan7.perworldinventory.other.groups;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.other.Database.Database;
import cz.tomasan7.perworldinventory.other.playerData.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
        Database database = PerWorldInventory.mainDatabase;
        String sql = database.insert_player_data("pwi_" + groupName);

        String data = playerData.Serialize();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setString(1, player.getName());
            statement.setString(2, player.getUniqueId().toString());
            statement.setString(3, data);

            statement.executeUpdate();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            Group.databaseFail();
        }
    }
}

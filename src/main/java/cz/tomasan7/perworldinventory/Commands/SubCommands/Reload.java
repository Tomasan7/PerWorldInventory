package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.other.Config;
import cz.tomasan7.perworldinventory.other.Messages;
import cz.tomasan7.perworldinventory.other.database.MySQL;
import cz.tomasan7.perworldinventory.other.database.SQLite;
import cz.tomasan7.perworldinventory.other.groups.Group;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Reload implements SubCommand
{
    @Override
    public String getName ()
    {
        return "reload";
    }

    @Override
    public String getDescription ()
    {
        return "Reloads plugin's configuration file.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi reload";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.reload";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        return null;
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        Config.reloadConfig();
        Messages.loadMessages();
        Group.saveGroups();
        Group.loadGroups();

        PerWorldInventory.mainDatabase.Disconnect();

        if (Config.useMysql)
            PerWorldInventory.mainDatabase = new MySQL(Config.getConfig().getConfigurationSection("MySQL"));
        else
            PerWorldInventory.mainDatabase = new SQLite("Database", true);

        PerWorldInventory.mainDatabase.Connect(15);

        PerWorldInventory.getInstance().getLogger().info("§2Configuration and Groups reloaded by " + sender.getName() + "§2.");
        Messages.send(sender, "§2Configuration and Groups reloaded.");
    }
}

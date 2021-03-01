package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.database.MySQL;
import cz.tomasan7.perworldinventory.database.SQLite;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.other.Config;
import cz.tomasan7.perworldinventory.other.Messages;
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

        PerWorldInventory.getMainDatabase().disconnect();

        if (Config.useMysql)
            PerWorldInventory.setMainDatabase(new MySQL(Config.getConfig().getConfigurationSection("MySQL")));
        else
            PerWorldInventory.setMainDatabase(new SQLite("Database", true));

        PerWorldInventory.getMainDatabase().connect();

        PerWorldInventory.getInstance().getLogger().info("§2Configuration and Groups reloaded by " + sender.getName() + "§2.");
        Messages.send(sender, "§2Configuration and Groups reloaded.");
    }
}

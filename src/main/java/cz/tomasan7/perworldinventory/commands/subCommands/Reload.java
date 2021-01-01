package cz.tomasan7.perworldinventory.commands.subCommands;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.commands.SubCommand;
import cz.tomasan7.perworldinventory.other.*;
import org.bukkit.command.CommandSender;

import java.util.List;

public class Reload extends SubCommand
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
        Messages.Send(sender, "§2Configuration and Groups reloaded.");
    }
}

package cz.tomasan7.perworldinventory.commands.subCommands;


import cz.tomasan7.perworldinventory.commands.SubCommand;
import cz.tomasan7.perworldinventory.other.Group;
import cz.tomasan7.perworldinventory.other.GroupActionResult;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CreateGroup extends SubCommand
{
    @Override
    public String getName ()
    {
        return "creategroup";
    }

    @Override
    public String getDescription ()
    {
        return "Creates new pwi group.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi creategroup §9<group-name>";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.creategroup";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        return null;
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        String groupName;

        try
        {
            groupName = args[0];
        }
        catch (Exception exception)
        {
            Messages.Send(sender, "§cUsage: §e" + getSyntax());
            return;
        }

        GroupActionResult result = Group.createGroup(groupName);
        Messages.Send(sender, result.message);
    }
}

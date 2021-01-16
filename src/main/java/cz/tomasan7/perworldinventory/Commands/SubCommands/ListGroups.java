package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.other.Group;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListGroups implements SubCommand
{
    @Override
    public String getName ()
    {
        return "listgroups";
    }

    @Override
    public String getDescription ()
    {
        return "Lists all groups.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi listgroups";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.listgroups";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        return null;
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        if (Group.getGroups().isEmpty())
        {
            Messages.send(sender, "§2There are no groups created yet.");
            return;
        }

        for (Group group : Group.getGroups())
        {
            Messages.send(sender, "§2" + group.getName());

            for (String world : group.getWorlds())
                Messages.send(sender, "  - " + world);
        }

    }
}

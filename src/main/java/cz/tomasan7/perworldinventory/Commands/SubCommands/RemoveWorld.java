package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.other.GroupActionResult;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RemoveWorld implements SubCommand
{
    @Override
    public String getName ()
    {
        return "removeworld";
    }

    @Override
    public String getDescription ()
    {
        return "Removes world from defined group..";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi removeworld §9<group> §9<world>";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.removeworld";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        switch (index)
        {
            case 0:
                return Group.getGroupsNames(false);

            case 1:
                if (args[0] != null)
                {
                    Group group = Group.getGroup(args[0]);

                    if (group != null)
                        return group.getWorlds();
                }

            default:
                return null;
        }
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        String groupName;
        String worldName;

        try
        {
            groupName = args[0];
            worldName = args[1];
        }
        catch (Exception exception)
        {
            Messages.send(sender, "§cUsage: §e" + getSyntax());
            return;
        }

        Group group = Group.getGroup(groupName);

        if (group == null)
        {
            Messages.send(sender, "§cGroup §l" + groupName + " §cdoesn't exist.");
            return;
        }

        GroupActionResult result = group.removeWorld(worldName);
        Messages.send(sender, result.message);
    }
}

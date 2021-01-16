package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.other.Group;
import cz.tomasan7.perworldinventory.other.GroupActionResult;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class AddWorld implements SubCommand
{
    @Override
    public String getName ()
    {
        return "addworld";
    }

    @Override
    public String getDescription ()
    {
        return "Adds world to defined group.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi addworld §9<group> §9<world>";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.addworld";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        switch (index)
        {
            case 0:
                return Group.getGroupsNames(false);

            case 1:
                return Group.getFreeWorlds();

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

        GroupActionResult result = group.addWorld(worldName);
        Messages.send(sender, result.message);
    }
}

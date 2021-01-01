package cz.tomasan7.perworldinventory.commands.subCommands;

import cz.tomasan7.perworldinventory.commands.SubCommand;
import cz.tomasan7.perworldinventory.other.Group;
import cz.tomasan7.perworldinventory.other.GroupActionResult;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class RenameGroup extends SubCommand
{

    @Override
    public String getName ()
    {
        return "renamegroup";
    }

    @Override
    public String getDescription ()
    {
        return "Renames existing group.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi rename §9<group> §9<new-name>";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.renamegroup";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        switch (index)
        {
            case 0:
                return Group.getGroupsNames(false);

            default:
                return null;
        }
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        String groupName;
        String newGroupName;

        try
        {
            groupName = args[0];
            newGroupName = args[1];
        }
        catch (Exception exception)
        {
            Messages.Send(sender, "§cUsage: §e" + getSyntax());
            return;
        }

        Group group = Group.getGroup(groupName);

        if (group == null)
        {
            Messages.Send(sender, "§cGroup §l" + groupName + " §cdoesn't exist.");
            return;
        }

        GroupActionResult result = group.setName(newGroupName);
        Messages.Send(sender, result.message);
    }
}

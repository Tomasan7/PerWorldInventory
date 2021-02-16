package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.other.GroupActionResult;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.CommandSender;

import java.util.List;

public class DeleteGroup implements SubCommand
{
    @Override
    public String getName ()
    {
        return "deletegroup";
    }

    @Override
    public String getDescription ()
    {
        return "Removes defined group.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi deletegroup §9<group>";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.deletegroup";
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

        try
        {
            groupName = args[0];
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

        GroupActionResult result = group.Delete();
        Messages.send(sender, result.message);
    }
}

package cz.tomasan7.perworldinventory.Commands.SubCommands;

import cz.tomasan7.perworldinventory.Commands.SubCommand;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.groups.GroupsMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EditGroups implements SubCommand
{
    @Override
    public String getName ()
    {
        return "editgroups";
    }

    @Override
    public String getDescription ()
    {
        return "Removes opens GUI for editing groups.";
    }

    @Override
    public String getSyntax ()
    {
        return "/pwi editgroups";
    }

    @Override
    public String getPermission ()
    {
        return "pwi.editgroups";
    }

    @Override
    public List<String> getTabCompletion (int index, String[] args)
    {
        return null;
    }

    @Override
    public void perform (CommandSender sender, String[] args)
    {
        Player player = (Player) sender;

        new GroupsMenu().openMenu(player);
    }
}

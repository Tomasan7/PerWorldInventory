package cz.tomasan7.perworldinventory.menus.menus;

import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuUtils;
import cz.tomasan7.perworldinventory.menus.menuItems.GroupMI;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.Material;

import java.util.List;

public class GroupsMenu extends Menu
{
    @Override
    public String getTitle ()
    {
        return "Groups";
    }

    @Override
    public int getSize ()
    {
        return 27;
    }

    @Override
    public void setMenuItems ()
    {
        List<String> groups = Group.getGroupsNames(true);

        for (int i = 0; i < groups.size(); i++)
        {
            GroupMI groupMI = new GroupMI(groups.get(i), i, MenuUtils.createItem(groups.get(i), Material.PAPER, null, 1));
            menuItems.add(groupMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return null;
    }
}

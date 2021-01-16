package cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.PaginatedMenu;
import cz.tomasan7.perworldinventory.other.Group;

import java.util.List;

public class GroupsMenu extends PaginatedMenu
{
    @Override
    public String getTitle ()
    {
        return "Groups";
    }

    @Override
    public int getSize ()
    {
        return 18;
    }

    @Override
    public void setMenuItems ()
    {
        List<String> groups = Group.getGroupsNames(true);

        for (int i = 0; i < groups.size(); i++)
        {
            GroupMI groupMI = new GroupMI(groups.get(i), i);
            menuItems.add(groupMI);
        }

        menuItems.add(new CreateGroupMI("CreateGroup", 8));
    }

    @Override
    public Menu getClosingMenu ()
    {
        return null;
    }
}

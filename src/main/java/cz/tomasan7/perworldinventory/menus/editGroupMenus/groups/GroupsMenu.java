package cz.tomasan7.perworldinventory.menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.PaginatedMenu;

import java.util.List;

public class GroupsMenu extends PaginatedMenu
{
    public GroupsMenu ()
    {
        super("Groups", MenuSize.ONE, true);
        Setup();
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.clear();
        menuItems.add(new CreateGroupMI("CreateGroup", 5, this));
    }

    @Override
    public void setPaginetedMenuItems ()
    {
        paginatedMenuItems.clear();
        List<String> groups = Group.getGroupsNames(true);

        for (int i = 0; i < groups.size(); i++)
        {
            GroupMI groupMI = new GroupMI(groups.get(i), i, this);
            paginatedMenuItems.add(groupMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return null;
    }
}

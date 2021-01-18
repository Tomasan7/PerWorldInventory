package cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.PaginatedMenu;
import cz.tomasan7.perworldinventory.other.Group;

import java.util.List;

public class GroupsMenu extends PaginatedMenu
{
    public GroupsMenu ()
    {
        super();
    }

    @Override
    public String getTitle ()
    {
        return "Groups";
    }

    @Override
    public int getSize ()
    {
        return 54;
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.clear();
        menuItems.add(new CreateGroupMI("CreateGroup", getSize() - 5));
    }

    @Override
    public void setPaginetedMenuItems ()
    {
        paginatedMenuItems.clear();
        List<String> groups = Group.getGroupsNames(true);

        for (int i = 0; i < groups.size(); i++)
        {
            GroupMI groupMI = new GroupMI(groups.get(i), i);
            paginatedMenuItems.add(groupMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return null;
    }
}

package cz.tomasan7.perworldinventory.menus.editGroupMenus.group;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.groups.GroupsMenu;

public class GroupMenu extends Menu
{
    private final Group group;

    public GroupMenu (Group group)
    {
        super("Edit group: " + group.getName(), MenuSize.ONE, true);
        this.group = group;
        Setup();
    }

    @Override
    public void setMenuItems ()
    {
        WorldsMI worldsMI = new WorldsMI("Worlds", 2, this, group);
        SavedDataMI savedDataMI = new SavedDataMI("SavedData", 6, this, group);

        menuItems.add(worldsMI);
        menuItems.add(savedDataMI);
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

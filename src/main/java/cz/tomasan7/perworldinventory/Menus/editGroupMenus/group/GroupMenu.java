package cz.tomasan7.perworldinventory.Menus.editGroupMenus.group;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups.GroupsMenu;
import cz.tomasan7.perworldinventory.other.Group;

public class GroupMenu extends Menu
{
    private final Group group;
    private final String title;

    public GroupMenu (Group group)
    {
        super();
        this.group = group;
        this.title = "Edit group: " + group.getName();
    }

    @Override
    public String getTitle ()
    {
        return title;
    }

    @Override
    public int getSize ()
    {
        return 9;
    }

    @Override
    public void setMenuItems ()
    {
        WorldsMI worldsMI = new WorldsMI("Worlds", 2, group);
        SavedDataMI savedDataMI = new SavedDataMI("SavedData", 6, group);

        menuItems.add(worldsMI);
        menuItems.add(savedDataMI);
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

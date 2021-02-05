package cz.tomasan7.perworldinventory.Menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.PaginatedMenu;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.group.GroupMenu;
import cz.tomasan7.perworldinventory.other.groups.Group;

import java.util.List;

public class WorldsMenu extends PaginatedMenu
{
    private final Group group;

    public WorldsMenu (Group group)
    {
        super();
        this.group = group;
    }

    @Override
    public String getTitle ()
    {
        return "Edit group: " + group.getName();
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

        menuItems.add(new AddWorldMI("AddWorld", getSize() - 5, group));
    }

    @Override
    public void setPaginetedMenuItems ()
    {
        paginatedMenuItems.clear();

        List<String> worlds = group.getWorlds();

        for (int i = 0; i < worlds.size(); i++)
        {
            String world = worlds.get(i);

            WorldMI worldMI = new WorldMI(world, i, group, world);

            menuItems.add(worldMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupMenu(group);
    }
}

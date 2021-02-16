package cz.tomasan7.perworldinventory.menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.PaginatedMenu;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.group.GroupMenu;

import java.util.List;

public class WorldsMenu extends PaginatedMenu
{
    private final Group group;

    public WorldsMenu (Group group)
    {
        super("Edit group: " + group.getName(), MenuSize.FIVE, true);
        this.group = group;
        Setup();
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.clear();

        menuItems.add(new AddWorldMI("AddWorld", 5, this, group));
    }

    @Override
    public void setPaginetedMenuItems ()
    {
        paginatedMenuItems.clear();

        List<String> worlds = group.getWorlds();

        for (int i = 0; i < worlds.size(); i++)
        {
            String world = worlds.get(i);

            WorldMI worldMI = new WorldMI(world, i, this, group, world);

            menuItems.add(worldMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupMenu(group);
    }
}

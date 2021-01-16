package cz.tomasan7.perworldinventory.Menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups.GroupsMenu;
import cz.tomasan7.perworldinventory.other.Group;

import java.util.List;

public class WorldsMenu extends Menu
{
    private final Group group;
    private final String title;

    public WorldsMenu (Group group)
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
        return 18;
    }

    @Override
    public void setMenuItems ()
    {
        List<String> worlds = group.getWorlds();

        for (int i = 0; i < worlds.size(); i++)
        {
            String world = worlds.get(i);

            WorldMI worldMI = new WorldMI(world, i, world);

            menuItems.add(worldMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

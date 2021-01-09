package cz.tomasan7.perworldinventory.menus.menus;

import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuUtils;
import cz.tomasan7.perworldinventory.menus.menuItems.WorldsMI;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.Material;

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
        return 27;
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.add(new WorldsMI("Worlds", 5, MenuUtils.createItem("Worlds", Material.GRASS_BLOCK, null, 1)));
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

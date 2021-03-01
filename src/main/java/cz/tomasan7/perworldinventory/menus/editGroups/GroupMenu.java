package cz.tomasan7.perworldinventory.menus.editGroups;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.SingleMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GroupMenu extends SingleMenu
{
    private final Group group;

    public GroupMenu (Group group)
    {
        super(MenuSize.ONE, "Edit group: " + group.getName(), true);
        this.group = group;
        setMenuItems();
    }

    @Override
    protected void setMenuItems ()
    {
        menuItems.clear();

        MenuItem worldsMI = new MenuItem(Material.GRASS_BLOCK, "Worlds", this, event -> new WorldsMenu(group).openMenu((Player) event.getWhoClicked()));
        MenuItem savedDataMI = new MenuItem(Material.BOOK, "SavedData", this, event -> new SavedDataMenu(group).openMenu((Player) event.getWhoClicked()));

        inventory.setItem(3, worldsMI.getItemStack());
        inventory.setItem(6, savedDataMI.getItemStack());

        menuItems.add(worldsMI);
        menuItems.add(savedDataMI);
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

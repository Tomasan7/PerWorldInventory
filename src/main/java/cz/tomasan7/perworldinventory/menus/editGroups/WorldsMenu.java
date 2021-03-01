package cz.tomasan7.perworldinventory.menus.editGroups;

import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.PaginatableMenu;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;
import java.util.function.UnaryOperator;

public class WorldsMenu extends PaginatableMenu
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

        MenuItem addWorldMI = new MenuItem(Material.LIME_STAINED_GLASS_PANE, "§2AddWorld", this, event ->
        {
            Player player = (Player) event.getWhoClicked();

            UnaryOperator<String> action = response -> group.addWorld(response).message;

            ResponseWaiter responseWaiter = new ResponseWaiter(action, new WorldsMenu(group));

            ResponseManager.AddWaiter(player, responseWaiter);
            player.closeInventory();
            Messages.send(player, "§eEnter the world name:");
        });

        menuItems.add(addWorldMI);

        for (Inventory page : pages)
            page.setItem(page.getSize() - 5, addWorldMI.getItemStack());
    }

    @Override
    public void setupPaginatedMenuItems ()
    {
        paginatedMenuItems.clear();

        List<String> worlds = group.getWorlds();

        for (String world : worlds)
        {
            MenuItem worldMI = new WorldMI(world, this, group, world);

            paginatedMenuItems.add(worldMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupMenu(group);
    }
}

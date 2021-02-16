package cz.tomasan7.perworldinventory.menus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class MenuEvents implements Listener
{
    @EventHandler
    public void onMenuClick (InventoryClickEvent event)
    {
        Inventory inventory = event.getView().getTopInventory();
        boolean isMenu = Menu.isMenu(inventory);

        if (!isMenu)
            return;
        else
            event.setCancelled(true);

        if (event.getCurrentItem() == null)
            return;

        Menu menu = (Menu) inventory.getHolder();
        menu.Handle(event);
    }

    @EventHandler
    public void onMenuClose (InventoryCloseEvent event)
    {
        /*InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof Menu))
            return;

        Menu closingMenu = ((Menu) holder).getClosingMenu();

        if (closingMenu != null)
            closingMenu.openOneTickLater((Player) event.getPlayer());*/
    }
}

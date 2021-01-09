package cz.tomasan7.perworldinventory.menus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuEvents implements Listener
{
    @EventHandler
    public void onMenuClick (InventoryClickEvent event)
    {
        InventoryHolder holder = event.getClickedInventory().getHolder();

        if (!(holder instanceof Menu))
            return;

        if (event.getCurrentItem() == null)
            return;

        event.setCancelled(true);

        Menu menu = (Menu) holder;
        menu.Handle(event);
    }

    @EventHandler
    public void onMenuClose (InventoryCloseEvent event)
    {
        InventoryHolder holder = event.getInventory().getHolder();

        if (!(holder instanceof Menu))
            return;

        Menu closingMenu = ((Menu) holder).getClosingMenu();

        if (closingMenu != null)
            MenuUtils.openOneTickLater((Player) event.getPlayer(), closingMenu);
    }
}

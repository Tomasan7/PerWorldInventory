package cz.tomasan7.perworldinventory.menus.menuItems;

import cz.tomasan7.perworldinventory.menus.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WorldsMI extends MenuItem
{
    public WorldsMI (String name, int slot, ItemStack item)
    {
        super(name, slot, item);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        player.closeInventory();
        player.sendMessage("Yeeey, it works as intended");
    }
}

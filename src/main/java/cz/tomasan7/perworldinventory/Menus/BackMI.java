package cz.tomasan7.perworldinventory.Menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BackMI extends MenuItem
{
    private static final Material iMaterial = Material.BARRIER;
    private static final String iName = "§c§lBack";
    private static final ArrayList<String> iLore = null;

    /**
     * Menu, this menuItem is in.
     */
    private final Menu menu;

    public BackMI (Menu menu, String name, int slot)
    {
        super(name, slot);
        this.menu = menu;
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (menu.getClosingMenu() != null)
            menu.getClosingMenu().openMenu(player);
        else
            player.closeInventory();
    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(iName, iMaterial, iLore, 1);
    }
}

package cz.tomasan7.perworldinventory.Menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class NextPageMI extends MenuItem
{
    private static final Material iMaterial = Material.ARROW;
    private static final String iName = "§lNext Page";
    private static final ArrayList<String> iLore = null;

    /**
     * PaginatedMenu, this menuItem is in.
     */
    PaginatedMenu menu;

    public NextPageMI (PaginatedMenu menu, String name, int slot)
    {
        super(name, slot);
        this.menu = menu;
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {

    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(iName, iMaterial, iLore, 1);
    }
}

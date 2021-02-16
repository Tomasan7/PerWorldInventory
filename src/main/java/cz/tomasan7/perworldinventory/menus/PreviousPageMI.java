package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PreviousPageMI extends MenuItem
{
    private static final Material iMaterial = Material.ARROW;
    private static final String iName = "Previous Page";
    private static final ArrayList<String> iLore = null;

    public PreviousPageMI (String name, int slot, PaginatedMenu holder)
    {
        super(name, slot, holder);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        ((PaginatedMenu) holder).previousPage();
    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(iName, iMaterial, iLore, 1);
    }
}

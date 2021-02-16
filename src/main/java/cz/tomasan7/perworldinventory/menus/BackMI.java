package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BackMI extends BottomLineMenuItem
{
    private static final Material iMaterial = Material.BARRIER;
    private static final String iName = "§c§lBack";
    private static final ArrayList<String> iLore = null;

    public BackMI (String name, int slot, Menu holder)
    {
        super(name, holder.getSize() + slot, holder);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (holder.getClosingMenu() != null)
            holder.getClosingMenu().openMenu(player);
        else
            player.closeInventory();
    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(iName, iMaterial, iLore, 1);
    }
}

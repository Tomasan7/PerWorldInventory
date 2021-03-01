package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BackMI extends MenuItem
{
    private static final Material iMaterial = Material.BARRIER;
    private static final String iDisplayName = "§c§lBack";

    public BackMI (Menu holder)
    {
        super(iMaterial, iDisplayName, holder);
        holder.getMenuItems().add(this);
    }

    @Override
    public void ClickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (holder.getClosingMenu() != null)
            holder.getClosingMenu().openMenu(player);
        else
            player.closeInventory();
    }
}

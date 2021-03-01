package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NextPageMI extends MenuItem
{
    private static final Material iMaterial = Material.ARROW;
    private static final String iDisplayName = "Next Page";

    public NextPageMI (PaginatableMenu holder)
    {
        super(iMaterial, iDisplayName, holder);
        holder.getMenuItems().add(this);
    }

    @Override
    public void ClickAction (InventoryClickEvent event)
    {
        ((PaginatableMenu) holder).nextPage();
    }
}

package cz.tomasan7.perworldinventory.Menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem
{
    protected String name;
    protected int slot;

    public MenuItem (String name, int slot)
    {
        this.name = name;
        this.slot = slot;
    }

    public abstract void clickAction (InventoryClickEvent event);

    public String getName ()
    {
        return name;
    }

    public int getSlot ()
    {
        return slot;
    }

    public abstract ItemStack getItemStack ();
}

package cz.tomasan7.perworldinventory.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem
{
    protected String name;
    protected int slot;

    protected Menu holder;

    public MenuItem (String name, int slot, Menu holder)
    {
        this.name = name;
        this.slot = slot;
        this.holder = holder;
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

package cz.tomasan7.perworldinventory.menus;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class MenuItem
{
    protected String name;
    protected int slot;
    protected ItemStack item;

    public MenuItem (String name, int slot, ItemStack item)
    {
        this.name = name;
        this.slot = slot;
        this.item = item;
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

    public ItemStack getItemStack ()
    {
        return item;
    }
}

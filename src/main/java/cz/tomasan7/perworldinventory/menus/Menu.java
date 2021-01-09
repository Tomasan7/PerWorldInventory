package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements InventoryHolder
{
    protected final List<MenuItem> menuItems;
    protected Inventory inventory;

    public Menu ()
    {
        this.menuItems = new ArrayList<MenuItem>();
    }

    public abstract String getTitle ();

    public abstract int getSize ();

    @Override
    public Inventory getInventory ()
    {
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setMenuItems();

        for (MenuItem menuItem : menuItems)
        {
            if (menuItem.getSlot() + 1 <= getSize())
                inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
        }

        return inventory;
    }

    public abstract void setMenuItems ();

    public abstract Menu getClosingMenu ();

    public void Handle (InventoryClickEvent event)
    {
        for (MenuItem menuItem : menuItems)
        {
            if (event.getCurrentItem().equals(menuItem.getItemStack()))
            {
                menuItem.clickAction(event);
                return;
            }
        }
    }

    public List<MenuItem> getMenuItems ()
    {
        return menuItems;
    }
}

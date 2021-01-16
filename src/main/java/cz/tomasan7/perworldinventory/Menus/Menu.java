package cz.tomasan7.perworldinventory.Menus;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class Menu implements InventoryHolder
{
    protected static final ItemStack fill_item;

    static
    {
        fill_item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE, null, 1);
    }

    protected final List<MenuItem> menuItems;


    public Menu ()
    {
        this.menuItems = new ArrayList<MenuItem>();
    }

    public abstract String getTitle ();

    public abstract int getSize ();

    @Override
    public Inventory getInventory ()
    {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle());
        setMenuItems();

        for (int i = 0; i < inventory.getSize(); i++)
            inventory.setItem(i, fill_item);

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

    /**
     * Gives you an ItemStack with defined properties.
     *
     * @param name     Name of the item.
     * @param material Material of the item.
     * @param lore     Lore of the item.
     * @param amount
     * @return Final ItemStack.
     */
    public static ItemStack createItem (String name, Material material, ArrayList<String> lore, int amount)
    {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    /**
     * Opens desired menu one tick later from method call.
     *
     * @param player Player to open the menu to.
     */
    public void openOneTickLater (Player player)
    {
        BukkitRunnable bukkitRunnable = new BukkitRunnable()
        {
            @Override
            public void run ()
            {
                player.openInventory(getInventory());
            }
        };
        bukkitRunnable.runTaskLater(PerWorldInventory.getInstance(), 1);
    }

    public static boolean isMenu (Inventory inventory)
    {
        InventoryHolder holder;

        try
        {
            holder = inventory.getHolder();
        }
        catch (Exception exception)
        {
            return false;
        }

        return holder instanceof Menu;
    }

    public void openMenu (Player player)
    {
        if (Bukkit.isPrimaryThread())
            player.openInventory(this.getInventory());
        else
        {
            new BukkitRunnable()
            {
                @Override
                public void run ()
                {
                    player.openInventory(getInventory());
                }
            }.runTask(PerWorldInventory.getInstance());
        }
    }
}

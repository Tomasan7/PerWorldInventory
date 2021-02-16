package cz.tomasan7.perworldinventory.menus;

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
    public static final ItemStack bottom_line_fill_item;

    static
    {
        fill_item = createItem(" ", Material.GRAY_STAINED_GLASS_PANE, null, 1);
        bottom_line_fill_item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE, null, 1);
    }

    protected final Inventory inventory;
    private final String title;
    protected final MenuSize menuSize;

    protected final List<MenuItem> menuItems;
    private final boolean bottomLine;

    {
        this.menuItems = new ArrayList<MenuItem>();
    }

    public Menu (String title, MenuSize menuSize, boolean bottomLine)
    {
        this.title = title;
        this.menuSize = menuSize;
        this.bottomLine = bottomLine;

        this.inventory = Bukkit.createInventory(this, bottomLine ? menuSize.size + MenuSize.ONE.size : menuSize.size, title);
    }

    public String getTitle ()
    {
        return title;
    }

    public int getSize ()
    {
        return menuSize.size;
    }

    public void Setup()
    {
        setMenuItems();

        for (int i = 0; i < inventory.getSize(); i++)
        {
            inventory.setItem(i, fill_item);

            if (bottomLine && i >= inventory.getSize() - 9)
                inventory.setItem(i, bottom_line_fill_item);
        }

        for (MenuItem menuItem : menuItems)
        {
            if (menuItem.getSlot() + 1 <= getSize())
                inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
        }

        if (bottomLine)
        {
            BackMI backMI = new BackMI( "Back", 8, this);
            inventory.setItem(backMI.getSlot(), backMI.getItemStack());
            menuItems.add(backMI);
        }
    }

    @Override
    public Inventory getInventory ()
    {
        return inventory;
    }

    public abstract void setMenuItems ();

    public abstract Menu getClosingMenu ();

    public void Handle (InventoryClickEvent event)
    {
        //menuItems.stream().filter(event.getCurrentItem()::equals).findAny().get().clickAction(event);

        for (MenuItem menuItem : menuItems)
        {
            if (event.getCurrentItem().equals(menuItem.getItemStack()))
            {
                menuItem.clickAction(event);
                return;
            }
        }

        if (this instanceof PaginatedMenu)
        {
            //((PaginatedMenu) this).paginatedMenuItems.stream().filter(event.getCurrentItem()::equals).findAny().get().clickAction(event);

            for (MenuItem paginatedMenuItem : ((PaginatedMenu) this).paginatedMenuItems)
            {
                if (event.getCurrentItem().equals(paginatedMenuItem.getItemStack()))
                {
                    paginatedMenuItem.clickAction(event);
                    return;
                }
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

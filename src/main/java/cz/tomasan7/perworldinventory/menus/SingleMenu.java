package cz.tomasan7.perworldinventory.menus;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SingleMenu implements Menu
{
    protected final Inventory inventory;
    protected final List<MenuItem> menuItems;

    protected final boolean bottomLine;
    private final MenuSize menuSize;
    private final String title;

    {
        menuItems = new ArrayList<>();
    }

    public SingleMenu (MenuSize menuSize, String title, boolean bottomLine)
    {
        this.menuSize = menuSize;
        this.title = title;
        this.bottomLine = bottomLine;
        this.inventory = Bukkit.createInventory(this, bottomLine ? menuSize.size + MenuSize.ONE.size : menuSize.size, title);

        Setup();
    }

    @Override
    public void Handle (InventoryClickEvent event)
    {
        ItemStack clickedItem = event.getCurrentItem();
        MenuItem clickedMenuItem = null;

        clickedMenuItem = menuItems.stream().filter(mi -> clickedItem.equals(mi.getItemStack())).findAny().orElse(null);

        if (clickedMenuItem != null)
            clickedMenuItem.ClickAction(event);
    }

    @Override
    public void Setup ()
    {
        for (int slot = 0; slot < inventory.getSize(); slot++)
        {
            if (slot >= inventory.getSize() - MenuSize.ONE.size)
                inventory.setItem(slot, BOTTOM_LINE_FILL_ITEM);
            else
                inventory.setItem(slot, FILL_ITEM);
        }

        if (getClosingMenu() != null)
            inventory.setItem(inventory.getSize() - 1, new BackMI(this).getItemStack());
    }

    @Override
    public void openMenu (Player player)
    {
        if (Bukkit.isPrimaryThread())
            player.openInventory(getInventory());
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

    protected abstract void setMenuItems ();

    public final MenuSize getMenuSize ()
    {
        return menuSize;
    }

    public final String getTitle ()
    {
        return title;
    }

    @Override
    public List<MenuItem> getMenuItems ()
    {
        return menuItems;
    }

    @Override
    public @NotNull Inventory getInventory ()
    {
        return inventory;
    }
}

package cz.tomasan7.perworldinventory.menus;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public interface Menu extends InventoryHolder
{
    /**
     * Item, which is put into blank slots in Menus.
     */
    public static final ItemStack FILL_ITEM = Menu.createItem(Material.GRAY_STAINED_GLASS_PANE, " ", null, 1);
    ;
    /**
     * Item, which is put into blank slots in bottom line of menus, which have bottomLine enabled.
     */
    public static final ItemStack BOTTOM_LINE_FILL_ITEM = Menu.createItem(Material.BLACK_STAINED_GLASS_PANE, " ", null, 1);

    Menu getClosingMenu ();

    List<MenuItem> getMenuItems ();

    void Handle (InventoryClickEvent event);

    /**
     * Gives you an ItemStack with defined properties.
     *
     * @param material    Material of the item.
     * @param displayName Name of the item.
     * @param lore        Lore of the item.
     * @param amount
     * @return Final ItemStack.
     */
    static ItemStack createItem (Material material, String displayName, List<String> lore, int amount)
    {
        ItemStack item = new ItemStack(material, amount);

        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
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
    default void openOneTickLater (Player player)
    {
        BukkitRunnable bukkitRunnable = new BukkitRunnable()
        {
            @Override
            public void run ()
            {
                openMenu(player);
            }
        };
        bukkitRunnable.runTaskLater(PerWorldInventory.getInstance(), 1);
    }

    void Setup ();

    static boolean isMenu (Inventory inventory)
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

    void openMenu (Player player);
}

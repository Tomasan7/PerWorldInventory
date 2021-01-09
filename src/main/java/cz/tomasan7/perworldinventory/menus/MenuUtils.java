package cz.tomasan7.perworldinventory.menus;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class MenuUtils
{
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

    public static void openOneTickLater (Player player, Menu menu)
    {
        BukkitRunnable bukkitRunnable = new BukkitRunnable()
        {
            @Override
            public void run ()
            {
                player.openInventory(menu.getInventory());
            }
        };
        bukkitRunnable.runTaskLater(PerWorldInventory.getInstance(), 1);
    }
}

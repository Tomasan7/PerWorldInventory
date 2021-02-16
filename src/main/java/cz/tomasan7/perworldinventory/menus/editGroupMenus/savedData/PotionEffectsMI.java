package cz.tomasan7.perworldinventory.menus.editGroupMenus.savedData;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.worlds.WorldsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;

public class PotionEffectsMI extends MenuItem
{
    private static final String iName = "PotionEffects";
    private static final Material iMaterial = Material.POTION;
    private static final ArrayList<String> iLore = null;

    private Group group;

    public PotionEffectsMI (String name, int slot, Menu holder, Group group)
    {
        super(name, slot, holder);
        this.group = group;
    }

    @Override
    public ItemStack getItemStack ()
    {
        ItemStack item = Menu.createItem(iName, iMaterial, iLore, 1);
        PotionMeta meta = (PotionMeta) item.getItemMeta();
        meta.setBasePotionData(new PotionData(PotionType.SLOW_FALLING));
        item.setItemMeta(meta);

        return item;
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        new WorldsMenu(group).openMenu(player);
    }
}

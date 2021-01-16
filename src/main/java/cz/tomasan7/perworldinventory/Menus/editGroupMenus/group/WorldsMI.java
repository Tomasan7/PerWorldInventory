package cz.tomasan7.perworldinventory.Menus.editGroupMenus.group;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.worlds.WorldsMenu;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WorldsMI extends MenuItem
{
    private static final String iName = "Worlds";
    private static final Material iMaterial = Material.GRASS_BLOCK;
    private static final ArrayList<String> iLore = null;

    private Group group;

    public WorldsMI (String name, int slot, Group group)
    {
        super(name, slot);
        this.group = group;
    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(iName, iMaterial, iLore, 1);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        new WorldsMenu(group).openMenu(player);
    }
}

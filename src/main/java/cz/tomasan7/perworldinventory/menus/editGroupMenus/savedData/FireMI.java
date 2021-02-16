package cz.tomasan7.perworldinventory.menus.editGroupMenus.savedData;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.worlds.WorldsMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FireMI extends MenuItem
{
    private static final String iName = "Fire";
    private static final Material iMaterial = Material.BLAZE_POWDER;
    private static final ArrayList<String> iLore = null;

    private Group group;

    public FireMI (String name, int slot, Menu holder, Group group)
    {
        super(name, slot, holder);
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

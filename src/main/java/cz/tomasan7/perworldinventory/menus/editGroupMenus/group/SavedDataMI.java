package cz.tomasan7.perworldinventory.menus.editGroupMenus.group;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.savedData.SavedDataMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class SavedDataMI extends MenuItem
{
    private static final String iName = "SavedData";
    private static final Material iMaterial = Material.BOOK;
    private static final ArrayList<String> iLore = null;

    private Group group;

    public SavedDataMI (String name, int slot, Menu holder, Group group)
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

        player.openInventory(new SavedDataMenu(group).getInventory());
    }
}

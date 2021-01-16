package cz.tomasan7.perworldinventory.Menus.editGroupMenus.group;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.other.Group;
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

    public SavedDataMI (String name, int slot, Group group)
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

        //TODO: Open SavedDataMenu.
    }


}

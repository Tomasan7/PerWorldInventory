package cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseGroupAction;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CreateGroupMI extends MenuItem
{
    private static final Material iMaterial = Material.GREEN_STAINED_GLASS_PANE;
    private static final String iName = "§2§lCreate Group";
    private static final ArrayList<String> iLore = null;

    public CreateGroupMI (String name, int slot)
    {
        super(name, slot);
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

        ResponseWaiter responseWaiter = new ResponseWaiter(ResponseGroupAction.CreateGroup, null, new GroupsMenu());

        ResponseManager.AddWaiter(player, responseWaiter);
        player.closeInventory();
    }
}

package cz.tomasan7.perworldinventory.menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.ResponseSystem.ResponseAction;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CreateGroupMI extends MenuItem
{
    private static final Material iMaterial = Material.LIME_STAINED_GLASS_PANE;
    private static final String iName = "§2§lCreate Group";
    private static final ArrayList<String> iLore = null;

    public CreateGroupMI (String name, int slot, Menu holder)
    {
        super(name, slot, holder);
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

        ResponseAction action = response -> Group.createGroup(response).message;

        ResponseWaiter responseWaiter = new ResponseWaiter(action, new GroupsMenu());

        ResponseManager.AddWaiter(player, responseWaiter);
        player.closeInventory();
        Messages.send(player, "§eEnter the group name:");
    }
}

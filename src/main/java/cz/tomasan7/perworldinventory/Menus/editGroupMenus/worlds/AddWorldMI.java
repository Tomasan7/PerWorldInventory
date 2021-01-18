package cz.tomasan7.perworldinventory.Menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseGroupAction;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import cz.tomasan7.perworldinventory.other.Group;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AddWorldMI extends MenuItem
{
    private static final String iName = "§2§lAdd World";
    private static final Material iMaterial = Material.LIME_STAINED_GLASS_PANE;
    private static final ArrayList<String> iLore = null;

    private final Group group;

    public AddWorldMI (String name, int slot, Group group)
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

        ResponseWaiter responseWaiter = new ResponseWaiter(ResponseGroupAction.AddWorld, group, new WorldsMenu(group));

        ResponseManager.AddWaiter(player, responseWaiter);
        player.closeInventory();
        Messages.send(player, "§eEnter the world name:");
    }
}

package cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.group.GroupMenu;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseAction;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import cz.tomasan7.perworldinventory.other.Messages;
import cz.tomasan7.perworldinventory.other.groups.Group;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupMI extends MenuItem
{
    private static final Material iMaterial = Material.PAPER;
    private static final ArrayList<String> iLore = new ArrayList<>(Arrays.asList("§7LMB = Edit group.", "§7RMB = Rename group.", "§7MMB = Delete group."));

    private Group group;

    public GroupMI (String name, int slot)
    {
        super(name, slot);
        group = Group.getGroup(name);
    }

    @Override
    public ItemStack getItemStack ()
    {
        return Menu.createItem(group.getName(), iMaterial, iLore, 1);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        switch (event.getAction())
        {
            case PICKUP_ALL:
                new GroupMenu(group).openMenu(player);
                break;

            case PICKUP_HALF:
                ResponseAction action = response -> group.setName(response).message;
                ResponseManager.AddWaiter(player, new ResponseWaiter(action, new GroupsMenu()));
                Messages.send(player, "§eEnter new name for the group.");
                player.closeInventory();
                break;

            case CLONE_STACK:
                //TODO: Delete ConfirmationGUI.
                group.Delete();
                new GroupsMenu().openMenu(player);
                break;
        }
    }
}

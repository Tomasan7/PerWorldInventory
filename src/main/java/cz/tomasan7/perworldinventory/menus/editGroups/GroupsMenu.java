package cz.tomasan7.perworldinventory.menus.editGroups;

import cz.tomasan7.perworldinventory.ResponseSystem.ResponseManager;
import cz.tomasan7.perworldinventory.ResponseSystem.ResponseWaiter;
import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.PaginatableMenu;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.function.UnaryOperator;

public class GroupsMenu extends PaginatableMenu
{
    public GroupsMenu ()
    {
        super("Groups", MenuSize.ONE, true);
        Setup();
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.clear();

        MenuItem createGroupMI = new MenuItem(Material.LIME_STAINED_GLASS_PANE, "§2CreateGroup", this, event ->
        {
            Player player = (Player) event.getWhoClicked();

            UnaryOperator<String> action = response -> Group.createGroup(response).message;

            ResponseWaiter responseWaiter = new ResponseWaiter(action, new GroupsMenu());

            ResponseManager.AddWaiter(player, responseWaiter);
            player.closeInventory();
            Messages.send(player, "§eEnter the group name:");
        });

        for (Inventory inventory : pages)
            inventory.setItem(inventory.getSize() - 5, createGroupMI.getItemStack());

        menuItems.add(createGroupMI);
    }

    @Override
    public void setupPaginatedMenuItems ()
    {
        paginatedMenuItems.clear();

        for (Group group : Group.getGroups())
        {
            MenuItem groupMI = new MenuItem(Material.PAPER, group.getName(), Arrays.asList("§7LMB = Edit group.", "§7RMB = Rename group.", "§7MMB = Delete group."), this, event ->
            {
                Player player = (Player) event.getWhoClicked();

                switch (event.getAction())
                {
                    case PICKUP_ALL:
                        new GroupMenu(group).openMenu(player);
                        break;

                    case PICKUP_HALF:
                        UnaryOperator<String> action = response -> group.setName(response).message;
                        ResponseManager.AddWaiter(player, new ResponseWaiter(action, new GroupsMenu()));
                        Messages.send(player, "§eEnter new name for the group.");
                        player.closeInventory();
                        break;

                    case CLONE_STACK:
                        //TODO: Delete ConfirmationGUI.
                        group.delete();
                        new GroupsMenu().openMenu(player);
                        break;
                }
            });

            paginatedMenuItems.add(groupMI);
        }
    }

    @Override
    public Menu getClosingMenu ()
    {
        return null;
    }
}

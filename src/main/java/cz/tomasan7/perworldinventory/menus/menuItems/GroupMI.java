package cz.tomasan7.perworldinventory.menus.menuItems;

import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.menus.GroupMenu;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GroupMI extends MenuItem
{
    private Group group;

    public GroupMI (String name, int slot, ItemStack item)
    {
        super(name, slot, item);
        group = Group.getGroup(name);
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        player.openInventory(new GroupMenu(group).getInventory());
    }
}

package cz.tomasan7.perworldinventory.Menus.editGroupMenus.groups;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import cz.tomasan7.perworldinventory.Menus.editGroupMenus.group.GroupMenu;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GroupMI extends MenuItem
{
    private static final Material iMaterial = Material.PAPER;
    private static final ArrayList<String> iLore = null;

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

        new GroupMenu(group).openMenu(player);
    }
}

/*package cz.tomasan7.perworldinventory.menus;

import cz.tomasan7.perworldinventory.menus.menus.groups.GroupMI;
import cz.tomasan7.perworldinventory.other.Group;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GroupsGUI implements Listener
{
    private static final String title = "Groups";

    public Inventory getInventory ()
    {
        Inventory inventory = Bukkit.createInventory(null, 36, title);

        for (int i = 0; i < Group.getGroups().size(); i++)
        {
            String groupName = Group.getGroups().get(i).getName();

            inventory.setItem(i, new GroupMI(groupName, MenuUtils.createItem(groupName, Material.PAPER, null, 1)).getItemStack());
        }


        return inventory;
    }

    @EventHandler
    public void onClick (InventoryClickEvent event)
    {
        if (!event.getView().getTitle().equals(title))
            return;

        event.setCancelled(true);
    }
}
*/
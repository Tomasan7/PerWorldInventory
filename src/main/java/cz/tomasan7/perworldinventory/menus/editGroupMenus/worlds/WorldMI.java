package cz.tomasan7.perworldinventory.menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class WorldMI extends MenuItem
{
    private static final Material iMaterial_overworld = Material.GRASS_BLOCK;
    private static final Material iMaterial_nether = Material.NETHERRACK;
    private static final Material iMaterial_end = Material.END_STONE;
    private static final ArrayList<String> iLore = new ArrayList<>(Collections.singletonList("§7MMB = Remove world."));

    private final Group group;
    private final String world;

    public WorldMI (String name, int slot, Menu holder, Group group, String world)
    {
        super(name, slot, holder);
        this.group = group;
        this.world = world;
    }

    @Override
    public ItemStack getItemStack ()
    {
        Material material = getMaterial();

        return Menu.createItem(world, material, iLore, 1);
    }

    private Material getMaterial ()
    {
        Material material;

        World world = Bukkit.getWorld(this.world);

        switch (world.getEnvironment())
        {
            default:
            case NORMAL:
                material = iMaterial_overworld;
                break;

            case NETHER:
                material = iMaterial_nether;
                break;

            case THE_END:
                material = iMaterial_end;
                break;
        }

        return material;
    }

    @Override
    public void clickAction (InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (event.getAction() == InventoryAction.CLONE_STACK)
        {
            //TODO: Delete ConfirmationGUI.
            group.removeWorld(world);
            new WorldsMenu(group).openMenu(player);
        }
    }
}

package cz.tomasan7.perworldinventory.Menus.editGroupMenus.worlds;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.Menus.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class WorldMI extends MenuItem
{
    private static final Material iMaterial_overworld = Material.GRASS_BLOCK;
    private static final Material iMaterial_nether = Material.NETHERRACK;
    private static final Material iMaterial_end = Material.END_STONE;
    private static final ArrayList<String> iLore = null;

    private String world;

    public WorldMI (String name, int slot, String world)
    {
        super(name, slot);
        this.world = world;
    }

    @Override
    public ItemStack getItemStack ()
    {
        String name = world;
        Material material = getMaterial();

        return Menu.createItem(name, material, iLore, 1);
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
    }
}

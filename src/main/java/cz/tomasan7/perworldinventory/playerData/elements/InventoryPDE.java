package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class InventoryPDE extends PlayerDataElement<ItemStack[]>
{
    private static final String name = "inventory";
    public static final ItemStack[] null_value = new ItemStack[41];

    public InventoryPDE (final ItemStack[] value)
    {
        super(value);
    }

    public InventoryPDE (Player player)
    {
        super(player.getInventory().getContents());
    }

    public InventoryPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getList(name).toArray(new ItemStack[0]) : null_value);
    }

    public InventoryPDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected ItemStack[] getNull ()
    {
        return null_value;
    }

    @Override
    public boolean isNull ()
    {
        return Arrays.equals(value, null_value);
    }

    @Override
    public void setToPlayer (Player player)
    {
        player.getInventory().setContents(value);
    }
}

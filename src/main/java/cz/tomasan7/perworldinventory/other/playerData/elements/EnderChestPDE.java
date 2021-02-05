package cz.tomasan7.perworldinventory.other.playerData.elements;

import cz.tomasan7.perworldinventory.other.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class EnderChestPDE extends PlayerDataElement<ItemStack[]>
{
    private static final String name = "enderChest";
    public static final ItemStack[] null_value = new ItemStack[27];

    public EnderChestPDE (ItemStack[] value)
    {
        super(value);
    }

    public EnderChestPDE (Player player)
    {
        super(player.getEnderChest().getContents());
    }

    public EnderChestPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getList(name).toArray(new ItemStack[0]) : null_value);
    }

    public EnderChestPDE ()
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
        player.getEnderChest().setContents(value);
    }
}

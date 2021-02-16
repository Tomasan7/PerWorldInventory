package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HungerPDE extends PlayerDataElement<Integer>
{
    private static final String name = "hunger";
    public static final int null_value = 20;

    public HungerPDE (final int value)
    {
        super(value);
    }

    public HungerPDE (Player player)
    {
        super(player.getFoodLevel());
    }

    public HungerPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getInt(name) : null_value);
    }

    public HungerPDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected Integer getNull ()
    {
        return null_value;
    }

    @Override
    public void setToPlayer (Player player)
    {
        player.setFoodLevel(value);
    }
}

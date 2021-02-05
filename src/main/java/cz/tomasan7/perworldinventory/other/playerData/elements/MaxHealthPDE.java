package cz.tomasan7.perworldinventory.other.playerData.elements;

import cz.tomasan7.perworldinventory.other.playerData.PlayerDataElement;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class MaxHealthPDE extends PlayerDataElement<Double>
{
    private static final String name = "maxHealth";
    public static final double null_value = 20.0d;

    public MaxHealthPDE (double value)
    {
        super(value);
    }

    public MaxHealthPDE (Player player)
    {
        super(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }

    public MaxHealthPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getDouble(name) : null_value);
    }

    public MaxHealthPDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected Double getNull ()
    {
        return null_value;
    }

    @Override
    public void setToPlayer (Player player)
    {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(value);
    }
}

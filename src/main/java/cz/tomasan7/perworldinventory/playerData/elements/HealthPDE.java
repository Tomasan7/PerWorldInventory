package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class HealthPDE extends PlayerDataElement<Double>
{
    private static final String name = "health";
    public static final double null_value = 20.0d;

    public HealthPDE (double value)
    {
        super(value);
    }

    public HealthPDE (Player player)
    {
        super(player.getHealth());
    }

    public HealthPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getDouble(name) : null_value);
    }

    public HealthPDE ()
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
        player.setHealth(value);
    }
}

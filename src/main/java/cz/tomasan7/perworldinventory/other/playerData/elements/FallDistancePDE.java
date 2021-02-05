package cz.tomasan7.perworldinventory.other.playerData.elements;

import cz.tomasan7.perworldinventory.other.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class FallDistancePDE extends PlayerDataElement<Float>
{
    private static final String name = "fallDistance";
    public static final float null_value = 0f;

    public FallDistancePDE (float value)
    {
        super(value);
    }

    public FallDistancePDE (Player player)
    {
        super(player.getFallDistance());
    }

    public FallDistancePDE (ConfigurationSection section)
    {
        super(section.contains(name) ? (float) section.getDouble(name) : null_value);
    }

    public FallDistancePDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected Float getNull ()
    {
        return null_value;
    }

    public void setToPlayer (Player player)
    {
        player.setFallDistance(value);
    }
}

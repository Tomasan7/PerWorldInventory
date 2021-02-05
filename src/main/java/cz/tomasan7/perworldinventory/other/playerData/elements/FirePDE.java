package cz.tomasan7.perworldinventory.other.playerData.elements;

import cz.tomasan7.perworldinventory.other.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class FirePDE extends PlayerDataElement<Integer>
{
    private static final String name = "fire";
    public static final int null_value = -20;

    public FirePDE (int value)
    {
        super(value);
    }

    public FirePDE (Player player)
    {
        super(player.getFireTicks());
    }

    public FirePDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getInt(name) : null_value);
    }

    public FirePDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    public Integer getNull ()
    {
        return null_value;
    }

    @Override
    public void setToPlayer (Player player)
    {
        player.setFireTicks(value);
    }
}

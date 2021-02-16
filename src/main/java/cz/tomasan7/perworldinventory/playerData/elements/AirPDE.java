package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class AirPDE extends PlayerDataElement<Integer>
{
    private static final String name = "air";
    public static final int null_value = -300;

    public AirPDE (int value)
    {
        super(value);
    }

    public AirPDE (Player player)
    {
        super(player.getRemainingAir());
    }

    public AirPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getInt(name) : null_value);
    }

    public AirPDE ()
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
        player.setRemainingAir(value);
    }
}

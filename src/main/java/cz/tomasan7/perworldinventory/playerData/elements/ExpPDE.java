package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ExpPDE extends PlayerDataElement<Float>
{
    private static final String name = "exp";
    public static final float null_value = 0f;

    public ExpPDE (float value)
    {
        super(value);
    }

    public ExpPDE (Player player)
    {
        super(player.getExp());
    }

    public ExpPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? (float) section.getDouble(name) : null_value);
    }

    public ExpPDE ()
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

    @Override
    public void setToPlayer (Player player)
    {
        player.setExp(value);
    }
}

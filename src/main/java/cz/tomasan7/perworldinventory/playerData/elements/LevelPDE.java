package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class LevelPDE extends PlayerDataElement<Integer>
{
    private static final String name = "level";
    public static final int null_value = 0;

    public LevelPDE (int value)
    {
        super(value);
    }

    public LevelPDE (Player player)
    {
        super(player.getLevel());
    }

    public LevelPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getInt(name) : null_value);
    }

    public LevelPDE ()
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
        player.setLevel(value);
    }
}

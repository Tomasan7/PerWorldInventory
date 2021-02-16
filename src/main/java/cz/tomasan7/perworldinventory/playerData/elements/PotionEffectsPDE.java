package cz.tomasan7.perworldinventory.playerData.elements;

import cz.tomasan7.perworldinventory.playerData.PlayerDataElement;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Collection;

public class PotionEffectsPDE extends PlayerDataElement<Collection<PotionEffect>>
{
    private static final String name = "potionEffects";
    public static final Collection<PotionEffect> null_value = new ArrayList<PotionEffect>();

    public PotionEffectsPDE (Collection<PotionEffect> value)
    {
        super(value);
    }

    public PotionEffectsPDE (Player player)
    {
        super(player.getActivePotionEffects());
    }

    public PotionEffectsPDE (ConfigurationSection section)
    {
        super(section.contains(name) ? (Collection<PotionEffect>) section.getList(name) : null_value);
    }

    public PotionEffectsPDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected Collection<PotionEffect> getNull ()
    {
        return null_value;
    }

    @Override
    public boolean isNull ()
    {
        return value.size() == 0;
    }

    @Override
    public void setToPlayer (Player player)
    {
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());

        for (PotionEffect potionEffect : value)
            player.addPotionEffect(potionEffect);
    }
}

package cz.tomasan7.perworldinventory.other.playerData;

import cz.tomasan7.perworldinventory.other.SavedData;
import cz.tomasan7.perworldinventory.other.playerData.elements.*;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerData
{
    private final ArrayList<PlayerDataElement> elements;

    {
        elements = new ArrayList<PlayerDataElement>();
    }

    public PlayerData (Player player)
    {
        elements.add(new MaxHealthPDE(player));
        elements.add(new HealthPDE(player));
        elements.add(new HungerPDE(player));
        elements.add(new InventoryPDE(player));
        elements.add(new EnderChestPDE(player));
        elements.add(new LevelPDE(player));
        elements.add(new ExpPDE(player));
        elements.add(new PotionEffectsPDE(player));
        elements.add(new AirPDE(player));
        elements.add(new FirePDE(player));
        elements.add(new FallDistancePDE(player));
        elements.add(new BalancePDE(player));
    }

    public PlayerData (String serialized)
    {
        YamlConfiguration yaml = new YamlConfiguration();
        try
        {
            yaml.loadFromString(serialized);
        }
        catch (InvalidConfigurationException e)
        {
            e.printStackTrace();
        }

        elements.add(new MaxHealthPDE(yaml));
        elements.add(new HealthPDE(yaml));
        elements.add(new HungerPDE(yaml));
        elements.add(new InventoryPDE(yaml));
        elements.add(new EnderChestPDE(yaml));
        elements.add(new LevelPDE(yaml));
        elements.add(new ExpPDE(yaml));
        elements.add(new PotionEffectsPDE(yaml));
        elements.add(new AirPDE(yaml));
        elements.add(new FirePDE(yaml));
        elements.add(new FallDistancePDE(yaml));
        elements.add(new BalancePDE(yaml));
    }

    public static PlayerData getPlayerData (Player player)
    {
        return new PlayerData(player);
    }

    public static void setPlayerData (Player player, PlayerData playerData, SavedData savedData)
    {
        clearPlayerData(player);

        for (PlayerDataElement element : playerData.elements)
        {
            if (element instanceof MaxHealthPDE)
            {
                element.setToPlayer(player);
                continue;
            }

            if (savedData.getElement(element.getName()).isEnabled())
                element.setToPlayer(player);
        }
    }

    public static void clearPlayerData (Player player)
    {
        new MaxHealthPDE().setToPlayer(player);
        new HealthPDE().setToPlayer(player);
        new HungerPDE().setToPlayer(player);
        new InventoryPDE().setToPlayer(player);
        new EnderChestPDE().setToPlayer(player);
        new LevelPDE().setToPlayer(player);
        new ExpPDE().setToPlayer(player);
        new PotionEffectsPDE().setToPlayer(player);
        new FirePDE().setToPlayer(player);
        new AirPDE().setToPlayer(player);
        new FallDistancePDE().setToPlayer(player);
        new BalancePDE().setToPlayer(player);
    }

    public String Serialize ()
    {
        YamlConfiguration yaml = new YamlConfiguration();

        for (PlayerDataElement element : elements)
            yaml.set(element.getName(), element.getValue());

        return yaml.saveToString();
    }
}
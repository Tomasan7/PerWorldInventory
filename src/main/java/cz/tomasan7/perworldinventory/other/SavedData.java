package cz.tomasan7.perworldinventory.other;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class SavedData
{
    private final ArrayList<SavedDataElement> elements;

    {
        elements = new ArrayList<SavedDataElement>();
    }

    public SavedData ()
    {
        elements.add(new SavedDataElement("inventory", true, null));
        elements.add(new SavedDataElement("enderChest", true, null));
        elements.add(new SavedDataElement("health", true, null));
        elements.add(new SavedDataElement("hunger", true, null));
        elements.add(new SavedDataElement("level", true, null));
        elements.add(new SavedDataElement("exp", true, null));
        elements.add(new SavedDataElement("potionEffects", true, null));
        elements.add(new SavedDataElement("balance", true, null));
        elements.add(new SavedDataElement("air", true, null));
        elements.add(new SavedDataElement("fire", true, null));
        elements.add(new SavedDataElement("fallDistance", true, null));
        elements.add(new SavedDataElement("balance", false, null));
    }

    public SavedData (ConfigurationSection section)
    {
        elements.add(new SavedDataElement(section.getConfigurationSection("inventory")));
        elements.add(new SavedDataElement(section.getConfigurationSection("enderChest")));
        elements.add(new SavedDataElement(section.getConfigurationSection("health")));
        elements.add(new SavedDataElement(section.getConfigurationSection("hunger")));
        elements.add(new SavedDataElement(section.getConfigurationSection("level")));
        elements.add(new SavedDataElement(section.getConfigurationSection("exp")));
        elements.add(new SavedDataElement(section.getConfigurationSection("potionEffects")));
        elements.add(new SavedDataElement(section.getConfigurationSection("balance")));
        elements.add(new SavedDataElement(section.getConfigurationSection("air")));
        elements.add(new SavedDataElement(section.getConfigurationSection("fire")));
        elements.add(new SavedDataElement(section.getConfigurationSection("fallDistance")));
        elements.add(new SavedDataElement(section.getConfigurationSection("balance")));
    }

    public ConfigurationSection Serialize ()
    {
        ConfigurationSection section = new YamlConfiguration().createSection("savedData");

        for (SavedDataElement element : elements)
            section.set(element.getName(), element.Serialize());

        return section;
    }

    public SavedDataElement getElement (String name)
    {
        for (SavedDataElement element : elements)
        {
            if (element.getName().equals(name))
                return element;
        }

        return null;
    }

    public SavedDataElement getInventory ()
    {
        return getElement("inventory");
    }

    public SavedDataElement getEnderChest ()
    {
        return getElement("enderChest");
    }

    public SavedDataElement getHealth ()
    {
        return getElement("health");
    }

    public SavedDataElement getHunger ()
    {
        return getElement("hunger");
    }

    public SavedDataElement getLevel ()
    {
        return getElement("level");
    }

    public SavedDataElement getExp ()
    {
        return getElement("exp");
    }

    public SavedDataElement getPotionEffects ()
    {
        return getElement("potionEffects");
    }

    public SavedDataElement getBalance ()
    {
        return getElement("balance");
    }

    public SavedDataElement getAir ()
    {
        return getElement("air");
    }

    public SavedDataElement getFire ()
    {
        return getElement("fire");
    }

    public SavedDataElement getFallDamage ()
    {
        return getElement("fallDistance");
    }
}

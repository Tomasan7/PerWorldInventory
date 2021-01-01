package cz.tomasan7.perworldinventory.other;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class SavedData
{
    private SavedDataElement inventory;
    private SavedDataElement enderChest;
    private SavedDataElement health;
    private SavedDataElement hunger;
    private SavedDataElement level;
    private SavedDataElement exp;
    private SavedDataElement potionEffects;
    private SavedDataElement balance;

    public SavedData ()
    {
        this.inventory = new SavedDataElement("inventory", true, null);
        this.enderChest = new SavedDataElement("enderChest", true, null);
        this.health = new SavedDataElement("health", true, 20d);
        this.hunger = new SavedDataElement("hunger", true, 20d);
        this.level = new SavedDataElement("level", true, 0);
        this.exp = new SavedDataElement("exp", true, 0f);
        this.potionEffects = new SavedDataElement("potionEffects", true, null);
        this.balance = new SavedDataElement("balance", false, 0d);
    }

    public SavedData (ConfigurationSection section)
    {
        this.inventory = new SavedDataElement(section.getConfigurationSection("inventory"));
        this.enderChest = new SavedDataElement(section.getConfigurationSection("enderChest"));
        this.health = new SavedDataElement(section.getConfigurationSection("health"));
        this.hunger = new SavedDataElement(section.getConfigurationSection("hunger"));
        this.level = new SavedDataElement(section.getConfigurationSection("level"));
        this.exp = new SavedDataElement(section.getConfigurationSection("exp"));
        this.potionEffects = new SavedDataElement(section.getConfigurationSection("potionEffects"));
        this.balance = new SavedDataElement(section.getConfigurationSection("balance"));
    }

    public ConfigurationSection Serialize ()
    {
        ConfigurationSection section = new YamlConfiguration().createSection("savedData");

        section.set(inventory.getName(), inventory.Serialize());
        section.set(enderChest.getName(), enderChest.Serialize());
        section.set(health.getName(), health.Serialize());
        section.set(hunger.getName(), hunger.Serialize());
        section.set(level.getName(), level.Serialize());
        section.set(exp.getName(), exp.Serialize());
        section.set(potionEffects.getName(), potionEffects.Serialize());
        section.set(balance.getName(), balance.Serialize());

        return section;
    }

    public SavedDataElement getInventory ()
    {
        return inventory;
    }

    public void setInventory (SavedDataElement inventory)
    {
        this.inventory = inventory;
    }

    public SavedDataElement getEnderChest ()
    {
        return enderChest;
    }

    public void setEnderChest (SavedDataElement enderChest)
    {
        this.enderChest = enderChest;
    }

    public SavedDataElement getHealth ()
    {
        return health;
    }

    public void setHealth (SavedDataElement health)
    {
        this.health = health;
    }

    public SavedDataElement getHunger ()
    {
        return hunger;
    }

    public void setHunger (SavedDataElement hunger)
    {
        this.hunger = hunger;
    }

    public SavedDataElement getLevel ()
    {
        return level;
    }

    public void setLevel (SavedDataElement level)
    {
        this.level = level;
    }

    public SavedDataElement getExp ()
    {
        return exp;
    }

    public void setExp (SavedDataElement exp)
    {
        this.exp = exp;
    }

    public SavedDataElement getPotionEffects ()
    {
        return potionEffects;
    }

    public void setPotionEffects (SavedDataElement potionEffects)
    {
        this.potionEffects = potionEffects;
    }

    public SavedDataElement getBalance ()
    {
        return balance;
    }

    public void setBalance (SavedDataElement balance)
    {
        this.balance = balance;
    }
}

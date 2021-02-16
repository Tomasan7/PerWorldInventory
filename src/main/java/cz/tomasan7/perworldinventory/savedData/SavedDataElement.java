package cz.tomasan7.perworldinventory.savedData;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class SavedDataElement
{
    private String name;
    private boolean enabled;
    private Object defaultValue;

    public SavedDataElement (String name, boolean enabled, Object defaultValue)
    {
        this.name = name;
        this.enabled = enabled;
        this.defaultValue = defaultValue;
    }

    public SavedDataElement (ConfigurationSection section)
    {
        this.name = section.getName();
        this.enabled = (boolean) section.get("enabled");
        this.defaultValue = section.get("default");
    }

    public ConfigurationSection Serialize ()
    {
        ConfigurationSection section = new YamlConfiguration().createSection(this.name);

        section.set("enabled", this.enabled);
        section.set("default", this.defaultValue);

        return section;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public boolean isEnabled ()
    {
        return enabled;
    }

    public void setEnabled (boolean enabled)
    {
        this.enabled = enabled;
    }

    public Object getDefaultValue ()
    {
        return defaultValue;
    }

    public void setDefaultValue (Object defaultValue)
    {
        this.defaultValue = defaultValue;
    }
}

package cz.tomasan7.perworldinventory.other;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;

public class PlayerData
{
    public ItemStack[] inventory;
    public static final ItemStack[] NULL_INVENTORY = new ItemStack[41];

    private ItemStack[] enderChest;
    public static final ItemStack[] NULL_ENDER_CHEST = new ItemStack[27];

    private double maxHealth;
    public static final double NULL_MAX_HEALTH = 20d;

    private double health;
    public static final double NULL_HEALTH = 20d;

    private int hunger;
    public static final int NULL_HUNGER = 20;

    private int level;
    public static final int NULL_LEVEL = 0;

    private float exp;
    public static final float NULL_EXP = 0f;

    private Collection<PotionEffect> potionEffects;
    public static final Collection<PotionEffect> NULL_POTION_EFFECTS = new ArrayList<PotionEffect>();

    private double balance;
    public static final double NULL_BALANCE = 0d;

    public PlayerData (Player player)
    {
        this.maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        this.health = player.getHealth();
        this.hunger = player.getFoodLevel();
        this.inventory = player.getInventory().getContents();
        this.enderChest = player.getEnderChest().getContents();
        this.level = player.getLevel();
        this.exp = player.getExp();
        this.potionEffects = player.getActivePotionEffects();
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

        if (yaml.contains("maxHealth"))
            this.maxHealth = yaml.getDouble("maxHealth");
        else
            this.maxHealth = NULL_MAX_HEALTH;

        if (yaml.contains("health"))
            this.health = yaml.getDouble("health");
        else
            this.health = NULL_HEALTH;

        if (yaml.contains("hunger"))
            this.hunger = yaml.getInt("hunger");
        else
            this.hunger = NULL_HUNGER;

        if (yaml.contains("inventory"))
            this.inventory = ((List<ItemStack>) yaml.getList("inventory")).toArray(new ItemStack[0]);
        else
            this.inventory = NULL_INVENTORY;

        if (yaml.contains("enderChest"))
            this.enderChest = ((List<ItemStack>) yaml.getList("enderChest")).toArray(new ItemStack[0]);
        else
            this.enderChest = NULL_ENDER_CHEST;

        if (yaml.contains("level"))
            this.level = yaml.getInt("level");
        else
            this.level = NULL_LEVEL;

        if (yaml.contains("exp"))
            this.exp = (float) yaml.getDouble("exp");
        else
            this.exp = NULL_EXP;

        if (yaml.contains("potionEffects"))
            this.potionEffects = (Collection<PotionEffect>) yaml.getList("potionEffects");
        else
            this.potionEffects = NULL_POTION_EFFECTS;

        /*List<ItemStack> tempInventory = ((List<ItemStack>) map.get("inventory"));
        this.inventory = tempInventory.toArray(new ItemStack[0]);
        List<ItemStack> tempEnderChest = (List<ItemStack>) map.get("enderChest");
        this.enderChest = tempEnderChest.toArray(new ItemStack[tempEnderChest.size()]);
        this.level = (int) map.get("level");
        this.exp = (float) ((double) map.get("exp"));
        this.potionEffects = (Collection<PotionEffect>) map.get("potionEffects");*/
    }

    public static PlayerData getPlayerData (Player player)
    {
        return new PlayerData(player);
    }

    public static void setPlayerData (Player player, PlayerData playerData, SavedData savedData)
    {
        clearPlayerData(player);

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(playerData.maxHealth);

        if (savedData.getHealth().isEnabled())
            player.setHealth(playerData.health);

        if (savedData.getHunger().isEnabled())
            player.setFoodLevel(playerData.hunger);

        if (savedData.getInventory().isEnabled())
            player.getInventory().setContents(playerData.inventory);

        if (savedData.getEnderChest().isEnabled())
            player.getEnderChest().setContents(playerData.enderChest);

        if (savedData.getLevel().isEnabled())
            player.setLevel(playerData.level);

        if (savedData.getExp().isEnabled())
            player.setExp(playerData.exp);

        if (savedData.getPotionEffects().isEnabled())
            player.addPotionEffects(playerData.potionEffects);

        if (savedData.getBalance().isEnabled()) {}
            //TODO
    }

    public static void clearPlayerData (Player player)
    {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.getEnderChest().clear();
        player.setLevel(0);
        player.setExp(0);
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
    }

    /*@Override
    public Map<String, Object> serialize ()
    {
        Map<String, Object> map = new HashMap<>();

        map.put("maxHealth", this.maxHealth);
        map.put("health", this.health);
        map.put("hunger", this.hunger);
        map.put("inventory", this.inventory);
        map.put("enderChest", this.enderChest);
        map.put("level", this.level);
        map.put("exp", this.exp);
        map.put("potionEffects", this.potionEffects);

        return map;
    }*/

    public String Serialize ()
    {
        YamlConfiguration yaml = new YamlConfiguration();

        if (this.maxHealth != NULL_MAX_HEALTH)
            yaml.set("maxHealth", this.maxHealth);

        if (this.health != NULL_HEALTH)
            yaml.set("health", this.health);

        if (this.hunger != NULL_HUNGER)
            yaml.set("hunger", this.hunger);

        if (!Arrays.equals(this.inventory, NULL_INVENTORY))
            yaml.set("inventory", this.inventory);

        if (!Arrays.equals(this.enderChest, NULL_ENDER_CHEST))
            yaml.set("enderChest", this.enderChest);

        if (this.level != NULL_LEVEL)
            yaml.set("level", this.level);

        if (this.exp != NULL_EXP)
            yaml.set("exp", this.exp);

        // Instead of using NULL_POTION_EFFECTS, I just check for the size, if it is empty, then do not save.
        if (this.potionEffects.size() != 0)
            yaml.set("potionEffects", this.potionEffects);

        return yaml.saveToString();
    }
}
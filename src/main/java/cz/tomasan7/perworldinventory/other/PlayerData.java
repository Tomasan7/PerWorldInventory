package cz.tomasan7.perworldinventory.other;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PlayerData
{
    //#region invenory
    public ItemStack[] inventory;
    public static final ItemStack[] NULL_INVENTORY = new ItemStack[41];
    //#endregion

    //#region enderChest
    private ItemStack[] enderChest;
    public static final ItemStack[] NULL_ENDER_CHEST = new ItemStack[27];
    //#endregion

    //#region maxHealth
    private double maxHealth;
    public static final double NULL_MAX_HEALTH = 20d;
    //#endregion

    //#region health
    private double health;
    public static final double NULL_HEALTH = 20d;
    //#endregion

    //#region hunger
    private int hunger;
    public static final int NULL_HUNGER = 20;
    //#endregion

    //#region level
    private int level;
    public static final int NULL_LEVEL = 0;
    //#endregion

    //#region exp
    private float exp;
    public static final float NULL_EXP = 0f;
    //#endregion

    //#region potionEffects
    private Collection<PotionEffect> potionEffects;
    public static final Collection<PotionEffect> NULL_POTION_EFFECTS = new ArrayList<PotionEffect>();
    //#endregion

    //#region fire
    private int fire;
    public static int NULL_FIRE = -20;
    //#endregion

    //#region air
    private int air;
    public static int NULL_AIR = 300;
    //#endregion

    //#region balance
    private double balance;
    public static final double NULL_BALANCE = 0d;
    //#endregion

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
        this.air = player.getRemainingAir();
        this.fire = player.getFireTicks();
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

        if (yaml.contains("air"))
            this.air = yaml.getInt("air");
        else
            this.air = NULL_AIR;

        if (yaml.contains("fire"))
            this.fire = yaml.getInt("fire");
        else
            this.fire = NULL_FIRE;
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
        else
            player.setHealth((double) savedData.getHealth().getDefaultValue());

        if (savedData.getHunger().isEnabled())
            player.setFoodLevel(playerData.hunger);
        else
            player.setFoodLevel((int) savedData.getHunger().getDefaultValue());

        if (savedData.getInventory().isEnabled())
            player.getInventory().setContents(playerData.inventory);
        else
            player.getInventory().setContents((ItemStack[]) savedData.getInventory().getDefaultValue());

        if (savedData.getEnderChest().isEnabled())
            player.getEnderChest().setContents(playerData.enderChest);
        else
            player.getEnderChest().setContents((ItemStack[]) savedData.getEnderChest().getDefaultValue());

        if (savedData.getLevel().isEnabled())
            player.setLevel(playerData.level);
        else
            player.setLevel((int) savedData.getLevel().getDefaultValue());

        if (savedData.getExp().isEnabled())
            player.setExp(playerData.exp);
        else
            player.setExp((float) savedData.getExp().getDefaultValue());

        if (savedData.getPotionEffects().isEnabled())
            player.addPotionEffects(playerData.potionEffects);
        else
            player.addPotionEffects((Collection<PotionEffect>) savedData.getPotionEffects().getDefaultValue());

        if (savedData.getAir().isEnabled())
            player.setRemainingAir(playerData.air);
        else
            player.setRemainingAir((int) savedData.getAir().getDefaultValue());

        if (savedData.getFire().isEnabled())
            player.setFireTicks(playerData.fire);
        else
            player.setFireTicks((int) savedData.getFire().getDefaultValue());

        if (savedData.getBalance().isEnabled())
        {
        }
        //TODO
    }

    public static void clearPlayerData (Player player)
    {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0);
        player.setHealth(NULL_HEALTH);
        player.setFoodLevel(NULL_HUNGER);
        player.getInventory().setContents(NULL_INVENTORY);
        player.getEnderChest().setContents(NULL_ENDER_CHEST);
        player.setLevel(NULL_LEVEL);
        player.setExp(NULL_EXP);
        for (PotionEffect potionEffect : player.getActivePotionEffects())
            player.removePotionEffect(potionEffect.getType());
        player.setFireTicks(NULL_FIRE);
        player.setRemainingAir(NULL_AIR);
    }

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

        if (this.fire != NULL_FIRE)
            yaml.set("fire", this.fire);

        if (this.air != NULL_AIR)
            yaml.set("air", this.air);

        return yaml.saveToString();
    }
}
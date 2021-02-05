package cz.tomasan7.perworldinventory.other.playerData.elements;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.other.playerData.PlayerDataElement;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class BalancePDE extends PlayerDataElement<Double>
{
    private static final String name = "balance";
    public static final double null_value = 0d;

    public BalancePDE (double value)
    {
        super(value);
    }

    public BalancePDE (Player player)
    {
        super(PerWorldInventory.getEconomy().getBalance(player));
    }

    public BalancePDE (ConfigurationSection section)
    {
        super(section.contains(name) ? section.getDouble(name) : null_value);
    }

    public BalancePDE ()
    {
        super(null_value);
    }

    @Override
    public String getName ()
    {
        return name;
    }

    @Override
    protected Double getNull ()
    {
        return null_value;
    }

    @Override
    public void setToPlayer (Player player)
    {
        Economy economy = PerWorldInventory.getEconomy();

        economy.withdrawPlayer(player, economy.getBalance(player));
        economy.depositPlayer(player, value);
    }
}

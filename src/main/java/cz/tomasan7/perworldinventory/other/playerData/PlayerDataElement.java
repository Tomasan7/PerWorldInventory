package cz.tomasan7.perworldinventory.other.playerData;

import org.bukkit.entity.Player;

public abstract class PlayerDataElement<T>
{
    protected T value;

    public PlayerDataElement (T value)
    {
        this.value = value;
    }

    T getValue ()
    {
        return value;
    }

    public abstract String getName ();

    public boolean isNull ()
    {
        return value.equals(getNull());
    }

    protected abstract T getNull ();

    public abstract void setToPlayer (Player player);
}

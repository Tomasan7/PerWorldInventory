package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class MenuItem
{
    private final ItemStack itemStack;
    private final Consumer<InventoryClickEvent> clickAction;

    protected final Menu holder;

    public MenuItem (Material material, String displayName, List<String> lore, int amount, Menu holder, Consumer<InventoryClickEvent> clickAction)
    {
        itemStack = Menu.createItem(material, displayName, lore, amount);
        this.clickAction = clickAction;
        this.holder = holder;
    }

    public MenuItem (Material material, String displayName, List<String> lore, Menu holder, Consumer<InventoryClickEvent> clickAction)
    {
        this(material, displayName, lore, 1, holder, clickAction);
    }

    public MenuItem (Material material, String displayName, int amount, Menu holder, Consumer<InventoryClickEvent> clickAction)
    {
        this(material, displayName, null, amount, holder, clickAction);
    }

    public MenuItem (Material material, String displayName, Menu holder, Consumer<InventoryClickEvent> clickAction)
    {
        this(material, displayName, null, 1, holder, clickAction);
    }

    public MenuItem (Material material, String displayName, Menu holder)
    {
        this(material, displayName, null, 1, holder, null);
    }

    public void ClickAction (InventoryClickEvent event)
    {
        if (clickAction != null)
            clickAction.accept(event);
    }

    public ItemStack getItemStack ()
    {
        return itemStack;
    }
}

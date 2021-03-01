package cz.tomasan7.perworldinventory.menus.editGroups;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuItem;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.SingleMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SavedDataMenu extends SingleMenu
{
    private final Group group;

    public SavedDataMenu (Group group)
    {
        super(MenuSize.TWO, "Edit saved data: " + group.getName(), true);
        this.group = group;
        setMenuItems();
    }

    @Override
    public void setMenuItems ()
    {
        menuItems.clear();

        MenuItem airMI = new MenuItem(Material.GLASS_BOTTLE, "Air", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem enderChestMI = new MenuItem(Material.ENDER_CHEST, "EnderChest", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem expMI = new MenuItem(Material.EXPERIENCE_BOTTLE, "Exp", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem levelMI = new MenuItem(Material.EXPERIENCE_BOTTLE, "Level", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem fallDistanceMI = new MenuItem(Material.WATER_BUCKET, "FallDistance", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem fireMI = new MenuItem(Material.BLAZE_POWDER, "Fire", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem healthMI = new MenuItem(Material.POTION, "Health", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem hungerMI = new MenuItem(Material.BREAD, "Hunger", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem inventoryMI = new MenuItem(Material.CHEST, "Inventory", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));
        MenuItem potionEffectsMI = new MenuItem(Material.POTION, "PotionEffects", this, event -> new GroupsMenu().openMenu((Player) event.getWhoClicked()));

        menuItems.add(airMI);
        menuItems.add(enderChestMI);
        menuItems.add(expMI);
        menuItems.add(levelMI);
        menuItems.add(fallDistanceMI);
        menuItems.add(fireMI);
        menuItems.add(healthMI);
        menuItems.add(hungerMI);
        menuItems.add(inventoryMI);
        menuItems.add(potionEffectsMI);

        for (int i = 0; i < menuItems.size(); i++)
            this.inventory.setItem(i, menuItems.get(i).getItemStack());
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupMenu(group);
    }
}

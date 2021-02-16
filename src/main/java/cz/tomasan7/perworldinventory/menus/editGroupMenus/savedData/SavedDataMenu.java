package cz.tomasan7.perworldinventory.menus.editGroupMenus.savedData;

import cz.tomasan7.perworldinventory.groups.Group;
import cz.tomasan7.perworldinventory.menus.Menu;
import cz.tomasan7.perworldinventory.menus.MenuSize;
import cz.tomasan7.perworldinventory.menus.editGroupMenus.groups.GroupsMenu;

public class SavedDataMenu extends Menu
{
    private final Group group;

    public SavedDataMenu (Group group)
    {
        super("Edit saved data: " + group.getName(), MenuSize.TWO, true);
        this.group = group;
        Setup();
    }

    @Override
    public void setMenuItems ()
    {
        AirMI airMI = new AirMI("Air", 0, this, group);
        EnderChestMI enderChestMI = new EnderChestMI("EnderChest", 1, this, group);
        ExpMI expMI = new ExpMI("Exp", 2, this, group);
        LevelMI levelMI = new LevelMI("Level", 3, this, group);
        FallDistanceMI fallDistanceMI = new FallDistanceMI("FallDistance", 4, this, group);
        FireMI fireMI = new FireMI("Fire", 5, this, group);
        HealthMI healthMI = new HealthMI("Health", 6, this, group);
        HungerMI hungerMI = new HungerMI("Hunger", 7, this, group);
        InventoryMI inventoryMI = new InventoryMI("Inventory", 8, this, group);
        PotionEffectsMI potionEffectsMI = new PotionEffectsMI("PotionEffects", 9, this, group);

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
    }

    @Override
    public Menu getClosingMenu ()
    {
        return new GroupsMenu();
    }
}

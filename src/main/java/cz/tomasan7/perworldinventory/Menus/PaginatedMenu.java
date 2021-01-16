package cz.tomasan7.perworldinventory.Menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu
{
    public static ItemStack bottom_line_fill_item;

    static
    {
        bottom_line_fill_item = createItem(" ", Material.GRAY_STAINED_GLASS_PANE, null, 1);
    }

    protected Inventory inventory;
    protected List<ItemStack[]> pages;
    private int currentPage;

    public PaginatedMenu ()
    {
        super();
        this.pages = new ArrayList<ItemStack[]>();
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        currentPage = 0;

        InitializePages();
    }

    @Override
    public Inventory getInventory ()
    {
        inventory.setContents(pages.get(0));
        return inventory;
    }

    private void InitializePages ()
    {
        setMenuItems();

        int numberOfPages = (int) Math.ceil((double) menuItems.size() / (double) getSize());

        int menuItemCursor = 0;

        for (int pageI = 0; pageI < numberOfPages; pageI++)
        {
            ItemStack[] page = addPage();

            for (int slot = 0; slot < page.length; slot++)
            {
                if (slot >= getSize() - 9)
                    page[slot] = bottom_line_fill_item;
                else
                {
                    if (menuItemCursor != menuItems.size())
                        page[slot] = menuItems.get(menuItemCursor++).getItemStack();
                    else
                        page[slot] = fill_item;
                }
            }
        }
    }

    private ItemStack[] addPage ()
    {
        ItemStack[] page = new ItemStack[getSize()];
        pages.add(page);
        return page;
    }

    public ItemStack[] getPage (int page)
    {
        return pages.get(page);
    }

    public void nextPage ()
    {
        if (currentPage == pages.size() - 1)
            return;

        currentPage++;
        inventory.setContents(getPage(currentPage));
    }

    public void previousPage ()
    {
        if (currentPage == 0)
            return;

        currentPage--;
        inventory.setContents(getPage(currentPage));
    }
}

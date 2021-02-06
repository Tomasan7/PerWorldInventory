package cz.tomasan7.perworldinventory.Menus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu
{
    public static final ItemStack bottom_line_fill_item;
    private static final String page_suffix = " (page %s)";

    static
    {
        bottom_line_fill_item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE, null, 1);
    }

    protected List<MenuItem> paginatedMenuItems;
    private List<Inventory> pages;
    private boolean isAdaptive;

    private int currentPage;

    public PaginatedMenu (boolean isAdaptive)
    {
        super();
        this.isAdaptive = isAdaptive;
    }

    @Override
    public Inventory getInventory ()
    {
        Initialize();
        return pages.get(currentPage);
    }

    public void Initialize ()
    {
        pages = new ArrayList<Inventory>();
        paginatedMenuItems = new ArrayList<MenuItem>();
        currentPage = 0;

        setMenuItems();
        setPaginetedMenuItems();

        int rowsCount = (int) Math.ceil((double) paginatedMenuItems.size() / ROW_SIZE);
        int pagesCount = (int) Math.ceil((double) rowsCount / ((double) getSize() / ROW_SIZE));
        rowsCount += pagesCount;
        pagesCount = (int) Math.ceil((double) rowsCount / ((double) getSize() / ROW_SIZE));

        if (pagesCount == 0)
            pagesCount = 1;

        MenuItem previousPageMI = new PreviousPageMI(this, "PreviousPage", getSize() - 7);
        MenuItem nextPageMI = new NextPageMI(this, "NextPage", getSize() - 3);
        BackMI backMI = new BackMI(this, "Back", getSize() - 1);

        int paginatedMenuItemCursor = 0;

        for (int pageI = 0; pageI < pagesCount; pageI++)
        {
            Inventory page = addPage();

            for (int slot = 0; slot < page.getSize(); slot++)
            {
                if (slot >= page.getSize() - ROW_SIZE)
                    page.setItem(slot, bottom_line_fill_item);
                else
                {
                    if (paginatedMenuItemCursor != paginatedMenuItems.size())
                        page.setItem(slot, paginatedMenuItems.get(paginatedMenuItemCursor++).getItemStack());
                    else
                        page.setItem(slot, fill_item);
                }
            }

            if (pageI != 0)
                page.setItem(previousPageMI.getSlot(), previousPageMI.getItemStack());

            if (pageI == pagesCount - 2)
                page.setItem(nextPageMI.getSlot(), nextPageMI.getItemStack());

            page.setItem(backMI.getSlot(), backMI.getItemStack());

            for (MenuItem menuItem : menuItems)
                page.setItem(menuItem.getSlot(), menuItem.getItemStack());
        }

        menuItems.add(previousPageMI);
        menuItems.add(nextPageMI);
        menuItems.add(backMI);
    }

    public abstract void setPaginetedMenuItems ();

    private Inventory addPage ()
    {
        Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle() + String.format(page_suffix, pages.size() + 1));
        pages.add(inventory);
        return inventory;
    }

    public Inventory getPage (int page)
    {
        return pages.get(page);
    }

    public void nextPage ()
    {
        if (currentPage == pages.size() - 1)
            return;

        currentPage++;

        pages.get(currentPage - 1).getViewers().get(0).openInventory(pages.get(currentPage));
    }

    public void previousPage ()
    {
        if (currentPage == 0)
            return;

        currentPage--;

        pages.get(currentPage + 1).getViewers().get(0).openInventory(pages.get(currentPage));
    }
}

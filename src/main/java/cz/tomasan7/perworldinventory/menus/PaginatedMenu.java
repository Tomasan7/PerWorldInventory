package cz.tomasan7.perworldinventory.menus;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu
{
    private static final String page_suffix = " (page %s)";

    protected List<MenuItem> paginatedMenuItems;
    private List<Inventory> pages;
    private final boolean isAdaptive;

    private int currentPage;

    public PaginatedMenu (String title, MenuSize menuSize, boolean isAdaptive)
    {
        super(title, menuSize, true);
        this.isAdaptive = isAdaptive;
    }

    @Override
    public Inventory getInventory ()
    {
        return pages.get(currentPage);
    }

    @Override
    public void Setup ()
    {
        pages = new ArrayList<Inventory>();
        paginatedMenuItems = new ArrayList<MenuItem>();
        currentPage = 0;

        setMenuItems();
        setPaginetedMenuItems();

        int PMIRowsCount = (int) Math.ceil((double) paginatedMenuItems.size() / MenuSize.ONE.size); // Number of rows with paginated menu items.

        int totalRowsCount = 0;
        int rowsCursor = 1;

        for (int i = 1; i <= PMIRowsCount; i++, rowsCursor++)
        {
            totalRowsCount++;

            if (rowsCursor == menuSize.rows || i == PMIRowsCount)
            {
                totalRowsCount++;
                rowsCursor = 1;
            }
        }

        if (totalRowsCount == 0)
            totalRowsCount = 1;

        int totalPagesCount = (int) Math.ceil((double) totalRowsCount / ((double) menuSize.rows + MenuSize.ONE.rows));
        int lastPageRowsCount = (int) Math.floor((double) totalRowsCount / (double) totalPagesCount);

        MenuItem previousPageMI = new PreviousPageMI("PreviousPage", 7, this);
        MenuItem nextPageMI = new NextPageMI("NextPage", 3, this);
        BackMI backMI = new BackMI("Back", 1, this);

        int paginatedMenuItemCursor = 0;

        for (int pageI = 1; pageI <= totalPagesCount; pageI++)
        {
            Inventory page;

            if (pageI == totalPagesCount && isAdaptive)
                page = addPage(lastPageRowsCount);
            else
                page = addPage();

            for (int slot = 0; slot < page.getSize() - MenuSize.ONE.size; slot++)
            {
                if (paginatedMenuItemCursor != paginatedMenuItems.size())
                    page.setItem(slot, paginatedMenuItems.get(paginatedMenuItemCursor++).getItemStack());
                else
                    page.setItem(slot, fill_item);
            }

            for (int i = page.getSize() - MenuSize.ONE.size; i < page.getSize(); i++)
                page.setItem(i, bottom_line_fill_item);

            if (pageI != 1)
                page.setItem(page.getSize() - previousPageMI.getSlot(), previousPageMI.getItemStack());

            if (pageI != totalPagesCount)
                page.setItem(page.getSize() - nextPageMI.getSlot(), nextPageMI.getItemStack());

            page.setItem(page.getSize() - 1, backMI.getItemStack());

            for (MenuItem menuItem : menuItems)
                page.setItem(page.getSize() - menuItem.getSlot(), menuItem.getItemStack());
        }

        menuItems.add(previousPageMI);
        menuItems.add(nextPageMI);
        menuItems.add(backMI);
    }

    public abstract void setPaginetedMenuItems ();

    private Inventory addPage ()
    {
        Inventory inventory = Bukkit.createInventory(this, menuSize.size + MenuSize.ONE.size, getTitle() + String.format(page_suffix, pages.size() + 1));
        pages.add(inventory);
        return inventory;
    }

    private Inventory addPage (int size)
    {
        Inventory inventory = Bukkit.createInventory(this, size * MenuSize.ONE.size, getTitle() + String.format(page_suffix, pages.size() + 1));
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

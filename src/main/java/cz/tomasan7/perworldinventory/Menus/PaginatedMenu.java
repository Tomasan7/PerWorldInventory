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
        bottom_line_fill_item = createItem(" ", Material.BLACK_STAINED_GLASS_PANE, null, 1);
    }

    private Inventory inventory;
    protected List<MenuItem> paginatedMenuItems;
    private List<ItemStack[]> pages;
    private int currentPage;

    public PaginatedMenu ()
    {
        super();
    }

    @Override
    public Inventory getInventory ()
    {
        Initialize();
        inventory.setContents(pages.get(0));
        return inventory;
    }

    public void Initialize ()
    {

        this.pages = new ArrayList<ItemStack[]>();
        this.paginatedMenuItems = new ArrayList<MenuItem>();
        inventory = Bukkit.createInventory(this, getSize(), getTitle());
        currentPage = 0;

        pages.clear();

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
            ItemStack[] page = addPage();

            for (int slot = 0; slot < page.length; slot++)
            {
                if (slot >= page.length - ROW_SIZE)
                    page[slot] = bottom_line_fill_item;
                else
                {
                    if (paginatedMenuItemCursor != paginatedMenuItems.size())
                        page[slot] = paginatedMenuItems.get(paginatedMenuItemCursor++).getItemStack();
                    else
                        page[slot] = fill_item;
                }
            }

            if (pageI != 0)
                page[previousPageMI.getSlot()] = previousPageMI.getItemStack();

            if (pageI == pagesCount - 2)
                page[nextPageMI.getSlot()] = previousPageMI.getItemStack();

            page[backMI.getSlot()] = backMI.getItemStack();

            for (MenuItem menuItem : menuItems)
                page[menuItem.getSlot()] = menuItem.getItemStack();
        }

        menuItems.add(previousPageMI);
        menuItems.add(nextPageMI);
        menuItems.add(backMI);
    }

    public abstract void setPaginetedMenuItems ();

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

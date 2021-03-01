package cz.tomasan7.perworldinventory.menus;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatableMenu implements Menu
{
    private static final String page_suffix = " (page %s)";

    protected List<Inventory> pages;
    protected List<MenuItem> paginatedMenuItems;
    protected List<MenuItem> menuItems;

    protected String title;
    protected MenuSize menuSize;

    private final boolean isAdaptive;

    private int currentPage;

    {
        this.pages = new ArrayList<>();
        this.paginatedMenuItems = new ArrayList<>();
        this.menuItems = new ArrayList<>();
        this.currentPage = 0;
    }

    public PaginatableMenu (String title, MenuSize menuSize, boolean isAdaptive)
    {
        this.title = title;
        this.menuSize = menuSize;
        this.isAdaptive = isAdaptive;
    }

    @Override
    public Inventory getInventory ()
    {
        return pages.get(currentPage);
    }

    @Override
    public void Handle (InventoryClickEvent event)
    {
        MenuItem clickedMenuItem = null;

        clickedMenuItem = menuItems.stream().filter(menuItem -> event.getCurrentItem().equals(menuItem.getItemStack())).findAny().orElse(null);

        if (clickedMenuItem == null)
            clickedMenuItem = paginatedMenuItems.stream().filter(menuItem -> event.getCurrentItem().equals(menuItem.getItemStack())).findAny().orElse(null);

        if (clickedMenuItem != null)
            clickedMenuItem.ClickAction(event);
    }

    public void Setup ()
    {
        pages.clear();

        currentPage = 0;
        setupPaginatedMenuItems();

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

        MenuItem previousPageMI = new PreviousPageMI(this);
        MenuItem nextPageMI = new NextPageMI(this);
        BackMI backMI = new BackMI(this);

        int paginatedMenuItemCursor = 0;

        for (int pageI = 1; pageI <= totalPagesCount; pageI++)
        {
            Inventory page;

            if (pageI == totalPagesCount && isAdaptive)
                page = addPage(lastPageRowsCount);
            else
                page = addPage();

            for (int slot = 0; slot < page.getSize(); slot++)
            {
                if (slot + MenuSize.ONE.size >= page.getSize())
                    page.setItem(slot, Menu.BOTTOM_LINE_FILL_ITEM);
                else
                {
                    if (paginatedMenuItemCursor != paginatedMenuItems.size())
                        page.setItem(slot, paginatedMenuItems.get(paginatedMenuItemCursor++).getItemStack());
                    else
                        page.setItem(slot, FILL_ITEM);
                }
            }

            if (pageI != 1)
                page.setItem(page.getSize() - 7, previousPageMI.getItemStack());

            if (pageI != totalPagesCount)
                page.setItem(page.getSize() - 3, nextPageMI.getItemStack());

            if (getClosingMenu() != null)
                page.setItem(page.getSize() - 1, backMI.getItemStack());
        }

        setMenuItems();

        menuItems.add(backMI);
        menuItems.add(previousPageMI);
        menuItems.add(nextPageMI);
    }

    protected abstract void setupPaginatedMenuItems ();

    protected abstract void setMenuItems ();

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

    public final Inventory getPage (int page)
    {
        return pages.get(page);
    }

    public final void nextPage ()
    {
        if (currentPage == pages.size() - 1)
            return;

        currentPage++;

        pages.get(currentPage - 1).getViewers().get(0).openInventory(pages.get(currentPage));
    }

    public final void previousPage ()
    {
        if (currentPage == 0)
            return;

        currentPage--;

        pages.get(currentPage + 1).getViewers().get(0).openInventory(pages.get(currentPage));
    }

    public String getTitle ()
    {
        return title;
    }

    public MenuSize getMenuSize ()
    {
        return menuSize;
    }

    @Override
    public List<MenuItem> getMenuItems ()
    {
        return menuItems;
    }

    @Override
    public void openMenu (Player player)
    {
        if (Bukkit.isPrimaryThread())
            player.openInventory(getInventory());
        else
        {
            new BukkitRunnable()
            {
                @Override
                public void run ()
                {
                    player.openInventory(getPage(currentPage));
                }
            }.runTask(PerWorldInventory.getInstance());
        }
    }
}

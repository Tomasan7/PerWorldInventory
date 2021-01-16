package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.other.Group;

public class ResponseWaiter
{
    private ResponseGroupAction action;
    private Group group;
    private Menu menuToOpen;

    private int time;
    private String timeoutMessage;
    public static String default_timeout_message = "Your request timed out.";
    public static int default_timeout_time = 30;

    public ResponseWaiter (ResponseGroupAction action, Group group, Menu menuToOpen, int timeoutTime, String timeoutMessage)
    {
        this.action = action;
        this.group = group;
        this.menuToOpen = menuToOpen;
        this.time = timeoutTime;
        this.timeoutMessage = timeoutMessage;
    }

    public ResponseWaiter (ResponseGroupAction action, Group group, Menu menuToOpen)
    {
        this.action = action;
        this.group = group;
        this.menuToOpen = menuToOpen;
        this.time = default_timeout_time;
        this.timeoutMessage = default_timeout_message;
    }

    public ResponseWaiter (ResponseGroupAction action, Group group, Menu menuToOpen, String timeoutMessage)
    {
        this.action = action;
        this.group = group;
        this.menuToOpen = menuToOpen;
        this.time = default_timeout_time;
        this.timeoutMessage = timeoutMessage;
    }

    public ResponseGroupAction getAction ()
    {
        return action;
    }

    public Group getGroup ()
    {
        return group;
    }

    public Menu getMenuToOpen ()
    {
        return menuToOpen;
    }

    public int getTime ()
    {
        return time;
    }

    public void setTime (int time)
    {
        this.time = time;
    }

    public String getTimeoutMessage ()
    {
        return timeoutMessage;
    }
}
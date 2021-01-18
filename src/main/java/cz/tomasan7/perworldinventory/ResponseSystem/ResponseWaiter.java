package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.Menus.Menu;
import cz.tomasan7.perworldinventory.other.Group;

public class ResponseWaiter
{
    private final ResponseGroupAction action;
    private final Group group;
    private final Menu menuToOpen;

    private int time;
    private final String timeoutMessage;
    private final String cancelMessage;
    public static String default_timeout_message = "§eYour request timed out.";
    public static String default_cancel_message = "§eYour request has been cancelled.";
    public static int default_timeout_time = 30;

    public ResponseWaiter (ResponseGroupAction action, Group group, Menu menuToOpen, int timeoutTime, String timeoutMessage, String cancelMessage)
    {
        this.action = action;
        this.group = group;
        this.menuToOpen = menuToOpen;
        this.time = timeoutTime;
        this.timeoutMessage = timeoutMessage;
        this.cancelMessage = cancelMessage;
    }

    public ResponseWaiter (ResponseGroupAction action, Group group, Menu menuToOpen)
    {
        this(action, group, menuToOpen, default_timeout_time, default_timeout_message, default_cancel_message);
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

    public String getCancelMessage ()
    {
        return cancelMessage;
    }
}
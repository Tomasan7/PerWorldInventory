package cz.tomasan7.perworldinventory.ResponseSystem;

import cz.tomasan7.perworldinventory.Menus.Menu;

public class ResponseWaiter
{
    private final ResponseAction action;
    private final Menu menuToOpen;

    private int time;
    private final String timeoutMessage;
    private final String cancelMessage;
    public static String default_timeout_message = "§eYour request timed out.";
    public static String default_cancel_message = "§eYour request has been cancelled.";
    public static int default_timeout_time = 30;

    public ResponseWaiter (ResponseAction action, Menu menuToOpen, int timeoutTime, String timeoutMessage, String cancelMessage)
    {
        this.action = action;
        this.menuToOpen = menuToOpen;
        this.time = timeoutTime;
        this.timeoutMessage = timeoutMessage;
        this.cancelMessage = cancelMessage;
    }

    public ResponseWaiter (ResponseAction action, Menu menuToOpen)
    {
        this(action, menuToOpen, default_timeout_time, default_timeout_message, default_cancel_message);
    }

    public ResponseAction getAction ()
    {
        return action;
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
package cz.tomasan7.perworldinventory.other;

public class GroupActionResult
{
    public boolean successful;
    public Object value;
    public String message;

    public GroupActionResult ()
    {
    }

    public GroupActionResult (boolean successful, Object value)
    {
        this.successful = successful;
        this.value = value;
    }

    public GroupActionResult (boolean successful, String message)
    {
        this.successful = successful;
        this.message = message;
    }

    public GroupActionResult (boolean successful, Object value, String message)
    {
        this.successful = successful;
        this.value = value;
        this.message = message;
    }


}

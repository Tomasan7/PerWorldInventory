package cz.tomasan7.perworldinventory.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand
{
    public String getName ();

    public String getDescription ();

    public String getSyntax ();

    public String getPermission ();

    public List<String> getTabCompletion (int index, String[] args);

    public void perform (CommandSender sender, String[] args);
}

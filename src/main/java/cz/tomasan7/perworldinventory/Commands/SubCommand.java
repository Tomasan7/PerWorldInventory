package cz.tomasan7.perworldinventory.Commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand
{
    String getName ();

    String getDescription ();

    String getSyntax ();

    String getPermission ();

    List<String> getTabCompletion (int index, String[] args);

    void perform (CommandSender sender, String[] args);
}

package cz.tomasan7.perworldinventory.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand
{

    public abstract String getName ();

    public abstract String getDescription ();

    public abstract String getSyntax ();

    public abstract String getPermission ();

    public abstract List<String> getTabCompletion (int index, String[] args);

    public abstract void perform (CommandSender sender, String[] args);
}

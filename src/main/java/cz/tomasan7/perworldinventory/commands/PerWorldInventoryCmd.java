package cz.tomasan7.perworldinventory.commands;

import cz.tomasan7.perworldinventory.PerWorldInventory;
import cz.tomasan7.perworldinventory.commands.subCommands.*;
import cz.tomasan7.perworldinventory.other.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PerWorldInventoryCmd implements TabExecutor
{

    public List<SubCommand> subCommands = new ArrayList<>();

    public PerWorldInventoryCmd ()
    {
        subCommands.add(new AddWorld());
        subCommands.add(new CreateGroup());
        subCommands.add(new ListGroups());
        subCommands.add(new Reload());
        subCommands.add(new DeleteGroup());
        subCommands.add(new RemoveWorld());
        subCommands.add(new RenameGroup());
    }

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length == 0 || args[0] == null)
        {
            showHelp(sender);
            return true;
        }

        if (args[0].equals("help"))
        {
            showHelp(sender);
            return true;
        }

        boolean showHelp = true;

        for (SubCommand subCommand : subCommands)
        {
            if (subCommand.getName().equals(args[0]))
            {
                if (sender.hasPermission(subCommand.getPermission()))
                {
                    subCommand.perform(sender, Arrays.copyOfRange(args, 1, args.length));
                    showHelp = false;
                }
                else
                    Messages.Send(sender, Messages.no_permission);
                break;
            }
        }

        if (showHelp) showHelp(sender);

        return true;
    }

    public List<SubCommand> getSubCommands ()
    {
        return subCommands;
    }

    private void showHelp (CommandSender sender)
    {
        if (!sender.hasPermission("pwi.help"))
        {
            Messages.Send(sender, Messages.no_permission);
            return;
        }

        String version = PerWorldInventory.getPlugin(PerWorldInventory.class).getDescription().getVersion();

        sender.sendMessage("§7§m               §b§l PerWorldInventory §f§l" + version + " §7§m               ");

        sender.sendMessage("§b/pwi help §f| §3 Shows this help.");

        for (SubCommand subCommand : getSubCommands())
            sender.sendMessage("§b" + subCommand.getSyntax() + " §f| §3" + subCommand.getDescription());

        sender.sendMessage("§7§m                                                                  ");
    }

    @Override
    public List<String> onTabComplete (CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> tabCompletions = new ArrayList<>();

        if (args.length == 1)
        {
            for (SubCommand subCommand : getSubCommands())
            {
                if (sender.hasPermission(subCommand.getPermission()))
                {
                    tabCompletions.add(subCommand.getName());
                }
            }
            tabCompletions.add("help");
        }

        boolean matches = false;

        if (args.length > 1)
        {
            for (SubCommand subCommand : getSubCommands())
            {
                if (subCommand.getName().equals(args[0]))
                {
                    if (subCommand.getTabCompletion(args.length - 2, args) != null)
                    {
                        tabCompletions = subCommand.getTabCompletion(args.length - 2, args);
                        matches = true;
                    }
                }
            }
            if (!matches)
            {
                return null;
            }
        }

        List<String> tabCompletionsFinished = new ArrayList<>();

        StringUtil.copyPartialMatches(args[args.length - 1], tabCompletions, tabCompletionsFinished);

        Collections.sort(tabCompletionsFinished);

        return tabCompletionsFinished;
    }
}

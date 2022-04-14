package me.kidneybean.generalutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class InfoTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 1) {
            List<String> list = new ArrayList<>();
            list.add("reload");
            list.add("info");
            return StringUtil.copyPartialMatches(args[0], list, new ArrayList<String>(list.size()));
        }
        return null;
    }
}

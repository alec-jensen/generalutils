package me.kidneybean.generalutils.commands;

import me.kidneybean.generalutils.GeneralUtils;
import me.kidneybean.generalutils.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToCommand implements CommandExecutor {
    private final GeneralUtils generalUtils;

    public ToCommand(GeneralUtils generalUtils) {
        this.generalUtils = generalUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (player.hasPermission("generalutils.bring")) {
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "You must specify a player!");
                    return true;
                }
                Player selPlayer;

                selPlayer = Bukkit.getPlayer(args[0]);
                if (selPlayer == null) {
                    player.sendMessage(ChatColor.RED + "That's not a valid player!");
                    return true;
                }
                Location location = selPlayer.getLocation();
                player.teleport(location);
            } else {
                player.sendMessage(Config.permissionMessage());
            }
        } else {
            generalUtils.getLogger().info(ChatColor.RED + "This command can only be executed by a player!");
            return true;
        }
        return true;
    }
}

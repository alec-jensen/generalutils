package me.alecjensen.generalutils.commands;

import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataType;

public class UnmuteCommand implements CommandExecutor, Listener {
    private final GeneralUtils generalUtils;

    public UnmuteCommand(GeneralUtils generalUtils) {
        this.generalUtils = generalUtils;
    }

    FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must specify someone to unmute!");
            return true;
        }

        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Not a valid player!");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Player unmuted!");

        targetPlayer.sendMessage(ChatColor.RED + "You have been unmuted!");

        NamespacedKey key = new NamespacedKey(generalUtils, "muted");

        targetPlayer.getPersistentDataContainer().set(key, PersistentDataType.STRING, "false");

        return true;
    }
}

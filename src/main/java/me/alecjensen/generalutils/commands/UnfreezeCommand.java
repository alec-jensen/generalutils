package me.alecjensen.generalutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class UnfreezeCommand implements CommandExecutor {
    private final HashMap<UUID, Boolean> frozenDict;

    public UnfreezeCommand(HashMap<UUID, Boolean> frozenDict) {
        this.frozenDict = frozenDict;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "You must specify a player!");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(ChatColor.RED + "That is not a valid player!");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            targetPlayer.setWalkSpeed(.2f);
            targetPlayer.sendMessage(ChatColor.RED + "You have been unfrozen!");
            frozenDict.remove(targetPlayer.getUniqueId());
        } else {
            if (args.length == 0) {
                Bukkit.getLogger().warning("You must specify a player!");
                return true;
            }
            if (Bukkit.getPlayer(args[0]) == null) {
                Bukkit.getLogger().warning("That is not a valid player!");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            targetPlayer.setWalkSpeed(.2f);
            targetPlayer.sendMessage(ChatColor.RED + "You have been unfrozen!");
            frozenDict.remove(targetPlayer.getUniqueId());
        }
        return true;
    }
}

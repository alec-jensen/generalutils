package me.kidneybean.generalutils.commands;

import me.kidneybean.generalutils.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class BackCommand implements CommandExecutor, Listener {
    HashMap<UUID, Location> backDict = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Config.getConfig().getString("back-enabled").equals("true")) {
                if (backDict.containsKey(player.getUniqueId())) {
                    Location location = backDict.get(player.getUniqueId());
                    player.teleport(location);
                } else {
                    player.sendMessage(ChatColor.RED + "You haven't teleported yet!");
                }
            }
        } else {
            Bukkit.getLogger().info(ChatColor.RED + "This command can only be executed by players!");
        }
        return true;
    }

    @EventHandler
    public void PlayerTeleportEvent(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        player.sendMessage(from.toString());
        backDict.put(player.getUniqueId(), from);
    }
}

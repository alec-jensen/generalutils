package me.alecjensen.generalutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class BackCommand implements CommandExecutor, Listener {
    HashMap<UUID, Location> backDict = new HashMap<>();
    HashMap<UUID, Boolean> teleportFromSleepDict = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("back-enabled").equals("true")) {
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
        if (teleportFromSleepDict.containsKey(player.getUniqueId())) {
            if (teleportFromSleepDict.get(player.getUniqueId())) {
                teleportFromSleepDict.remove(player.getUniqueId());
                return;
            }
        }
        Location from = event.getFrom();
        backDict.put(player.getUniqueId(), from);
    }

    @EventHandler
    public void PlayerBedLeaveEvent(PlayerBedLeaveEvent event) {
        teleportFromSleepDict.put(event.getPlayer().getUniqueId(), true);
    }
}

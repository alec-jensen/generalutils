package me.alecjensen.generalutils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class FreezeCommand implements CommandExecutor, Listener {
    private final HashMap<UUID, Boolean> frozenPlayers;

    public FreezeCommand(HashMap<UUID, Boolean> frozenPlayers) {
        this.frozenPlayers = frozenPlayers;
    }

    FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();

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
            targetPlayer.setWalkSpeed(0);
            targetPlayer.sendMessage(ChatColor.RED + "You have been frozen!");
            frozenPlayers.put(targetPlayer.getUniqueId(), true);
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
            targetPlayer.setWalkSpeed(0);
            targetPlayer.sendMessage(ChatColor.RED + "You have been frozen!");
            frozenPlayers.put(targetPlayer.getUniqueId(), true);
        }
        return true;
    }

    @EventHandler
    public void PlayerMoveEvent(PlayerMoveEvent event) {
        if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
        if (config.getBoolean("freeze-command.negate-damage")) {
            if (frozenPlayers.get(event.getEntity().getUniqueId()) != null) {
                event.setCancelled(true);
                if (event.getDamager() instanceof Player attacker) {
                    attacker.sendMessage(ChatColor.RED + "This player is frozen!");
                }

            }
        }
        if (config.getBoolean("freeze-command.negate-attack")) {
            if (frozenPlayers.get(event.getDamager().getUniqueId()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void PlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent event) {
        if (config.getBoolean("freeze-command.stop-commands")) {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event) {
        if (config.getBoolean("freeze-command.stop-break")) {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent event) {
        if (config.getBoolean("freeze-command.stop-place")) {
            if (frozenPlayers.get(event.getPlayer().getUniqueId()) != null) {
                event.setCancelled(true);
            }
        }
    }
}

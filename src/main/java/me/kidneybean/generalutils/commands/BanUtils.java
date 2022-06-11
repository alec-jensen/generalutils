package me.kidneybean.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPluginManager;

public class BanUtils implements CommandExecutor, Listener {
    private static BanUtils instance;

    public BanUtils() {
        instance = instance == null ? this : instance;
    }

    public static BanUtils getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must specify a player!");
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.RED + "You must specify a player!");
            return true;
        }
        String reason;
        if (args.length == 1) {
            if (config.getBoolean("ban-utils.custom-ban.require-reason")) {
                sender.sendMessage(ChatColor.RED + "You must provide a reason!");
                return true;
            } else {
                reason = config.getString("ban-utils.custom-ban.default-reason");
            }
        } else {
            reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        }
        if (target != null) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
            target.kickPlayer(reason);
        } else {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "You must specify a player!");
            } else {
                Bukkit.getLogger().warning("You must specify a player!");
            }
        }
        return true;
    }

    @EventHandler
    public void banUtils(PlayerKickEvent event) {
        FileConfiguration config = Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig();
        if(event.getPlayer().isBanned()) {
            if (config.getBoolean("ban-utils.ban-lightning-effect")) {
                event.getPlayer().getWorld().strikeLightningEffect(event.getPlayer().getLocation());
                for (Player loopPlayer : getOnlinePlayers()) {
                    loopPlayer.playSound(loopPlayer.getLocation(), "entity.lightning_bolt.thunder", 1.0f, 1.0f);
                }
            }
            if (config.getBoolean("ban-utils.announce-bans")) {
                for (Player loopPlayer : getOnlinePlayers()) {
                    if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                PlaceholderAPI.setPlaceholders(event.getPlayer(),
                                        config.getString("messages.ban-message").replace("<banned>",
                                                event.getPlayer().getDisplayName()).replace("<reason>", event.getReason()))));
                    } else {
                        loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                        config.getString("messages.ban-message").replace("<banned>",
                                                event.getPlayer().getDisplayName()).replace("<reason>", event.getReason())));
                    }
                }
            }
        }
    }
}

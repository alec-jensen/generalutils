package me.kidneybean.generalutils.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.kidneybean.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getOnlinePlayers;
import static org.bukkit.Bukkit.getPluginManager;

public class AnnounceCommand implements CommandExecutor {
    private final GeneralUtils plugin = GeneralUtils.getInstance();

    BukkitScheduler scheduler = Bukkit.getScheduler();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You must specify an announcement!");
            return true;
        }
        for (Player loopPlayer : getOnlinePlayers()) {
            Player player = (Player) sender;
            // Set placeholders and color codes
            if (getPluginManager().getPlugin("PlaceholderAPI") != null) {
                loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        PlaceholderAPI.setPlaceholders(player,
                        Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.announce-prefix"))
                                + String.join(" ", args)));
            } else {
                loopPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                                Bukkit.getPluginManager().getPlugin("GeneralUtils").getConfig().getString("messages.announce-prefix"))
                                + String.join(" ", args));
            }
            // Play this sound 3 times with a 1 tick interval using scheduler
            // This is  good announcement sound, it grabs attention
            AtomicInteger integer = new AtomicInteger(1);
            scheduler.runTaskTimer(plugin, task -> {
                if (integer.getAndAdd(1) >= 4) {
                    task.cancel();
                } else {
                    integer.addAndGet(1);
                }
                loopPlayer.playSound(loopPlayer.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
            }, 1L, 1L);
        }
        return true;
    }
}

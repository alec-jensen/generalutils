package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.alecjensen.generalutils.GeneralUtils;
import me.alecjensen.generalutils.utils.SendMessagePAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.atomic.AtomicInteger;

import static org.bukkit.Bukkit.getOnlinePlayers;

@CommandAlias("announce")
public class AnnounceCommand extends BaseCommand
{
    private final GeneralUtils generalUtils = GeneralUtils.getInstance();

    BukkitScheduler scheduler = Bukkit.getScheduler();

    @Default
    @CommandPermission("generalutils.announce")
    public void onCommand(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify an announcement!");
            return;
        }
        for (Player loopPlayer : getOnlinePlayers())
        {
            // Set placeholders and color codes
            SendMessagePAPI.sendMessagePAPI(loopPlayer, ChatColor.translateAlternateColorCodes('&',
                    generalUtils.config.getString("messages.announce-prefix"))
                    + String.join(" ", args));

            // Play this sound 3 times with a 1 tick interval using scheduler
            // This is a good announcement sound, it grabs attention
            AtomicInteger integer = new AtomicInteger(1);
            scheduler.runTaskTimer(generalUtils, task ->
            {
                if (integer.getAndAdd(1) >= 4)
                {
                    task.cancel();
                } else
                {
                    integer.addAndGet(1);
                }
                loopPlayer.playSound(loopPlayer.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
            }, 1L, 1L);
        }
    }
}

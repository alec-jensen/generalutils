package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.alecjensen.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("bring")
public class BringCommand extends BaseCommand
{
    private final GeneralUtils generalUtils;

    public BringCommand(GeneralUtils generalUtils)
    {
        this.generalUtils = generalUtils;
    }

    @Default
    @CommandPermission("generalutils.bring")
    @CommandCompletion("@players")
    public void onBringCommand(Player player, String[] args)
    {
        if (player.hasPermission("generalutils.bring"))
        {
            if (args.length == 0)
            {
                player.sendMessage(ChatColor.RED + "You must specify a player!");
                return;
            }
            Player selPlayer;

            selPlayer = Bukkit.getPlayer(args[0]);
            if (selPlayer == null)
            {
                player.sendMessage(ChatColor.RED + "That's not a valid player!");
                return;
            }
            Location location = player.getLocation();
            selPlayer.teleport(location);
        }
    }
}


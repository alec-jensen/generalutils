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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("to")
public class ToCommand extends BaseCommand
{
    private final GeneralUtils generalUtils;

    public ToCommand(GeneralUtils generalUtils)
    {
        this.generalUtils = generalUtils;
    }

    @Default
    @CommandPermission("generalutils.to")
    @CommandCompletion("@players")
    public void onToCommand(CommandSender sender, String[] args)
    {
        if (sender instanceof Player player)
        {
            if (player.hasPermission("generalutils.to"))
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
                Location location = selPlayer.getLocation();
                player.teleport(location);
            }
        } else
        {
            generalUtils.getLogger().warning("This command can only be executed by a player!");
        }
    }
}


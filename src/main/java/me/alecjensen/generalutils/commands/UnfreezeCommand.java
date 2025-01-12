package me.alecjensen.generalutils.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@CommandAlias("unfreeze")
public class UnfreezeCommand extends BaseCommand
{
    private final HashMap<UUID, Boolean> frozenPlayers;

    public UnfreezeCommand(HashMap<UUID, Boolean> frozenDict)
    {
        this.frozenPlayers = frozenDict;
    }

    @Default
    @CommandPermission("generalutils.unfreeze")
    @CommandCompletion("@frozenPlayers")
    public void onUnfreezeCommand(CommandSender sender, String[] args)
    {
        if (args.length == 0)
        {
            sender.sendMessage(ChatColor.RED + "You must specify a player!");
            return;
        }
        if (Bukkit.getPlayer(args[0]) == null)
        {
            sender.sendMessage(ChatColor.RED + "That is not a valid player!");
            return;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        targetPlayer.setWalkSpeed(.2f);
        targetPlayer.setFlySpeed(.1f);
        targetPlayer.setAllowFlight(false);
        frozenPlayers.remove(targetPlayer.getUniqueId());
        targetPlayer.sendMessage(ChatColor.GREEN + "You have been unfrozen!");
        sender.sendMessage(ChatColor.GREEN + "Successfully unfroze " + targetPlayer.getName() + "!");
    }
}

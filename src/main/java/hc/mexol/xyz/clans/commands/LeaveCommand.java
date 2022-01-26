package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            String clanName = ClanUtils.getPlayerClan(player);

            boolean res = ClanUtils.removePlayer(clanName, player);

            if (res == true) {

                player.sendMessage(ChatColor.YELLOW + "You have left your previous clan");

            } else {

                player.sendMessage(ChatColor.GRAY + "You are not in a clan");

            }

        }

        return true;

    }
}

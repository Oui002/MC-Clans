package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import hc.mexol.xyz.clans.utils.RequestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (RequestUtils.playerInHashMap(player)) {

                Player reqSender = RequestUtils.getRequestSender(player);
                ClanUtils.addPlayer(ClanUtils.getPlayerClan(player), reqSender);

                RequestUtils.removeFromRequests(player);

                player.sendMessage(ChatColor.YELLOW + "Added " + reqSender.getDisplayName() + " to your clan");
                reqSender.sendMessage(ChatColor.YELLOW + "You have been accepted to " + ClanUtils.getPlayerClan(player) + " by " + player.getDisplayName());

            } else {

                player.sendMessage(ChatColor.RED + "You don't have any requests");

            }

        }

        return true;

    }
}

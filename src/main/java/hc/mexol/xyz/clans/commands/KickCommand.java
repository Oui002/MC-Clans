package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (ClanUtils.isLeader(player)) {

                if (args.length == 0) {

                    player.sendMessage(ChatColor.RED + "You need to specify a player to kick");
                    return true;

                }

                Player playerToKick = Bukkit.getPlayer(args[0]);

                if (playerToKick != null) {

                    if (playerToKick.equals(player)) {

                        player.sendMessage(ChatColor.RED + "You can not kick yourself");
                        return true;

                    }

                    String playerClan = ClanUtils.getPlayerClan(player);
                    String kickClan = ClanUtils.getPlayerClan(playerToKick);

                    if (playerClan.equals(kickClan)) {

                        ClanUtils.removePlayer(playerClan, playerToKick);
                        player.sendMessage(ChatColor.YELLOW + "Remove player " + playerToKick.getDisplayName() + " from " + playerClan);
                        playerToKick.sendMessage(ChatColor.RED + "You have been kicked from " + playerClan);

                    } else {

                        player.sendMessage(ChatColor.RED + "This player is not part of your clan");

                    }

                } else {

                    player.sendMessage(ChatColor.RED + "This player does not exist");

                }

            } else {

                String playerClanName = ClanUtils.getPlayerClan(player);

                if (!playerClanName.equals("none")) {

                    player.sendMessage(ChatColor.RED + "You are not the leader of " + ChatColor.DARK_RED + "" + ChatColor.BOLD +  playerClanName);

                } else {

                    player.sendMessage(ChatColor.RED + "You are not in a clan");

                }

            }

        }

        return true;

    }

}

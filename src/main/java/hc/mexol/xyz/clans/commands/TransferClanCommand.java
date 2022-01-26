package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TransferClanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (ClanUtils.isLeader(player)) {

                if (args.length > 0) {

                    Player newLeader = Bukkit.getPlayer(args[0]);

                    String playerClan = ClanUtils.getPlayerClan(player);
                    String newLeaderClan = ClanUtils.getPlayerClan(newLeader);

                    if (newLeader.equals(player)) {

                        player.sendMessage(ChatColor.GRAY + "You can't transfer the clan to yourself");
                        return true;

                    }

                    if (newLeaderClan.equals(playerClan)) {

                        if (newLeader != null && newLeader instanceof Player) {

                            ClanUtils.setClanLeader(playerClan, newLeader);
                            player.sendMessage(ChatColor.YELLOW + "Ownership of " + playerClan + " has been transferred to " + newLeader.getDisplayName());
                            newLeader.sendMessage(ChatColor.YELLOW + "Ownership of " + playerClan + " has been transferred to you by " + player.getDisplayName());

                        } else {

                            player.sendMessage(ChatColor.GRAY + "This player does not exist");

                        }


                    } else {

                        player.sendMessage(ChatColor.GRAY + "This player is not in the same clan as you");

                    }


                } else {

                    player.sendMessage(ChatColor.GRAY + "You need to specify a player to transfer ownership to");

                }

            }

        }

        return true;

    }

}

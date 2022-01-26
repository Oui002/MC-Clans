package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import hc.mexol.xyz.clans.utils.RequestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            if (args.length == 0) {

                player.sendMessage(ChatColor.GRAY + "You need to provide a clan name");
                return true;

            }

            if (RequestUtils.senderInHashMap(player)) {

                player.sendMessage(ChatColor.RED + "You already have a pending request to join a clan");
                return true;

            }

            String clanName = args[0];

            String currentClanName = ClanUtils.getPlayerClan(player);

            if (currentClanName.equals("none")) {

                if (ClanUtils.clanExists(clanName)) {

                    Player clanLeader = ClanUtils.getClanLeader(clanName);

                    boolean res = RequestUtils.addToRequests(clanLeader, player);

                    if (res) {

                        clanLeader.sendMessage(ChatColor.GOLD + player.getDisplayName() + " has requested to join your clan.\n"
                                + ChatColor.YELLOW + "Type /accept to accept, or /deny to deny");

                    } else {

                        player.sendMessage(ChatColor.RED + "This player already has a request from someone");

                    }


                } else {

                    player.sendMessage(ChatColor.RED + "This clan does not exist");

                }

            } else {

                player.sendMessage(ChatColor.RED + "You are already in a clan named " + currentClanName);

            }

        }

        return true;

    }
}

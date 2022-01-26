package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import hc.mexol.xyz.clans.utils.RequestUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {

            String currentClanName = ClanUtils.getPlayerClan(player);

            if (currentClanName.equalsIgnoreCase("none")) {

                if (args.length == 0) {

                    player.sendMessage(ChatColor.GRAY + "You need to provide a clan name");
                    return true;

                }

                if (RequestUtils.senderInHashMap(player)) {

                    player.sendMessage(ChatColor.RED + "You already have a pending request to join a clan");
                    return true;

                }

                String clanName = args[0];

                if (!ClanUtils.clanExists(clanName)) {

                    ClanUtils.createClan(clanName);
                    ClanUtils.addPlayer(clanName, player);
                    ClanUtils.setClanLeader(clanName, player);

                    player.sendMessage(ChatColor.YELLOW + "Created clan");

                } else {

                    player.sendMessage(ChatColor.GRAY + "A clan with this name already exists");

                }


            } else {

                player.sendMessage(ChatColor.GRAY + "You are already in a clan named " + currentClanName);

            }

        }

        return true;

    }

}

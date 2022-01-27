package hc.mexol.xyz.clans.commands;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetClanPrefixColor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            
            if (ClanUtils.isLeader(player)) {
                
                if (args.length == 0) {
                    
                    player.sendMessage(ChatColor.RED + "You need to provide a character for a color code in the form of § color codes");
                    return true;
                    
                } else {

                    if (args[0].length() != 1) {

                        player.sendMessage(ChatColor.RED + "Color codes can only be a single character long");
                        return true;

                    }

                }

                String colorCode = args[0];
                String clanName = ClanUtils.getPlayerClan(player);

                ClanUtils.setClanColor(clanName, colorCode);

                player.sendMessage(ChatColor.YELLOW + "Updated your clan's color to" + "§" + colorCode + " " + colorCode);

            }
            
        }

        return true;

    }
}

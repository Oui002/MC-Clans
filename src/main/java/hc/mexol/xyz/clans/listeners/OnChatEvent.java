package hc.mexol.xyz.clans.listeners;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (ClanUtils.inClan(player)) {

            String playerClanName = ClanUtils.getPlayerClan(player);
            String colorPrefix = ClanUtils.getClanSettings(playerClanName).getString("clan-color");

            event.setFormat("[" + colorPrefix + playerClanName.toUpperCase() + "§f" + "] " + "§f" + player.getDisplayName() + ": " + event.getMessage());

        } else {

            event.setFormat(player.getDisplayName() + ": " + event.getMessage());

        }

    }

}

package hc.mexol.xyz.clans.listeners;

import hc.mexol.xyz.clans.utils.ClanUtils;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerDamageEvent implements Listener {

    @EventHandler
    public void PlayerDamageEvent(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (event.getDamager() instanceof Player damager) {

                String playerClan = ClanUtils.getPlayerClan(player);
                String damagerClan = ClanUtils.getPlayerClan(damager);

                if (playerClan.equalsIgnoreCase(damagerClan) /* && clanSettings.pvpEnabled */) {

                    event.setCancelled(true);

                }

            }

        }

    }

}

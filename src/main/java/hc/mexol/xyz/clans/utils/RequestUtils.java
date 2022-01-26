package hc.mexol.xyz.clans.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.UUID;

public class RequestUtils {

    private static HashMap<String, String> requests = new HashMap<String, String>();

    public static boolean addToRequests(Player receiver, Player sender) {

        String receiverUUID = receiver.getUniqueId().toString();
        String requesterUUID = sender.getUniqueId().toString();

        if (!playerInHashMap(receiver)) {

            requests.put(receiverUUID, requesterUUID);
            return true;

        }

        return false;

    }

    public static boolean removeFromRequests(Player receiver) {

        String receiverUUID = receiver.getUniqueId().toString();

        if (playerInHashMap(receiver)) {

            requests.remove(receiverUUID);
            return true;

        }

        return false;

    }

    public static Player getRequestSender(Player receiver) {

        String receiverUUID = receiver.getUniqueId().toString();
        String senderUUID = requests.get(receiverUUID);

        return Bukkit.getPlayer(UUID.fromString(senderUUID));

    }

    public static boolean playerInHashMap(Player receiver) {

        String receiverUUID = receiver.getUniqueId().toString();

        return requests.containsKey(receiverUUID);

    }

    public static boolean senderInHashMap(Player sender) {

        String senderUUID = sender.getUniqueId().toString();

        return requests.containsValue(senderUUID);

    }

}

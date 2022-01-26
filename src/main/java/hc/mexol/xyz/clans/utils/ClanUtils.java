package hc.mexol.xyz.clans.utils;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import hc.mexol.xyz.clans.Clans;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ClanUtils {

    public static MongoDatabase clansDatabase = Clans.getClansDatabase();

    public static void createClan(String name) {

        if (!name.equalsIgnoreCase("none")) {

            clansDatabase.createCollection(name.toUpperCase());

            Document leaderDocument = new Document(name.toUpperCase(), "settings")
                    .append("leader-uuid", "")
                    .append("pvp-enabled", false)
                    .append("clan-color", "§l §f");

            clansDatabase.getCollection(name.toUpperCase()).insertOne(leaderDocument);

        }

    }

    public static void deleteClanIfEmpty(String name) {

        if (!clanIsEmpty(name.toUpperCase())) {

            getClansCollection(name.toUpperCase()).drop();

        }

    }

    public static void addPlayer(String name, Player player) {

        String playerClanName = getPlayerClan(player);

        if (playerClanName.equalsIgnoreCase("none")) {

            Document playerDocument = new Document
                    ("uuid", player.getUniqueId().toString())
                    .append("join-date", new Date());

            getClansCollection(name.toUpperCase()).insertOne(playerDocument);

        } else {
            
            player.sendMessage(ChatColor.RED + "Could not join clan, you are already in " + playerClanName);
            
        }

    }

    public static boolean removePlayer(String name, Player player) {

        if (name.equalsIgnoreCase("NONE")) {

            return false;

        } else {

            Document playerFilter = new Document(new Document("uuid", player.getUniqueId().toString()));

            getClansCollection(name.toUpperCase()).deleteOne(playerFilter);

            deleteClanIfEmpty(name.toUpperCase());

            return true;

        }

    }

    public static MongoCollection<Document> getClansCollection(String name) {

        return clansDatabase.getCollection(name.toUpperCase());

    }

    public static Document getClanSettings(String name) {

        Document settingsFilter = new Document(name.toUpperCase(), "settings");

        return getClansCollection(name.toUpperCase()).find(settingsFilter).first();

    }

    public static boolean clanExists(String name) {

        return Clans.getClansDatabase().listCollectionNames()
                .into(new ArrayList<>()).contains(name.toUpperCase());

    }

    public static boolean clanIsEmpty(String name) {

        return clansDatabase.getCollection(name.toUpperCase()).countDocuments() > 1;

    }

    public static boolean isLeader(Player player) {

        Document filter = new Document(new Document("leader-uuid", player.getUniqueId().toString()));
        return clansDatabase.getCollection(getPlayerClan(player)).countDocuments(filter) == 1;

    }

    public static boolean inClan(Player player) {

        String playerClanName = getPlayerClan(player);

        return playerClanName != "none";

    }

    public static String getPlayerClan(Player player) {

        Document filter = new Document(new Document("uuid", player.getUniqueId().toString()));

        ArrayList<String> collections = Clans.getClansDatabase().listCollectionNames()
                .into(new ArrayList<>());

        for (String collection : collections) {

            if (getClansCollection(collection).countDocuments(filter) == 1) {
                return collection;
            }

        }

        return "NONE";

    }

    public static Player getClanLeader(String name) {

        Document filter = new Document(name.toUpperCase(), "settings");
        ArrayList<Document> docs = getClansCollection(name.toUpperCase()).find(filter)
                .into(new ArrayList<>());

        Document clanSettingsDoc = docs.get(0);

        String playerUUID = clanSettingsDoc.getString("leader-uuid");
        return Bukkit.getPlayer(UUID.fromString(playerUUID));

    }
    
    public static void setClanLeader(String name, Player player) {

        clansDatabase.getCollection(name.toUpperCase()).updateOne(Filters.eq(name.toUpperCase(), "settings"), Updates.set("leader-uuid", player.getUniqueId().toString()));
        
    }

    public static void setClanColor(String name, String letter) {

        clansDatabase.getCollection(name.toUpperCase()).updateOne(Filters.eq(name.toUpperCase(), "settings"), Updates.set("clan-color", "§l " + "§" + letter));

    }

}

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

            clansDatabase.createCollection(name);

            Document leaderDocument = new Document(name, "settings")
                    .append("leader-uuid", "")
                    .append("pvp-enabled", false);

            clansDatabase.getCollection(name).insertOne(leaderDocument);

        }

    }

    public static void deleteClanIfEmpty(String name) {

        if (!clanIsEmpty(name)) {

            getClansCollection(name).drop();

        }

    }

    public static void addPlayer(String name, Player player) {

        String playerClanName = getPlayerClan(player);

        if (playerClanName.equals("none")) {

            Document playerDocument = new Document
                    ("uuid", player.getUniqueId().toString())
                    .append("join-date", new Date());

            getClansCollection(name).insertOne(playerDocument);

        } else {
            
            player.sendMessage(ChatColor.RED + "Could not join clan, you are already in " + playerClanName);
            
        }

    }

    public static boolean removePlayer(String name, Player player) {

        if (name.equals("none")) {

            return false;

        } else {

            Document playerFilter = new Document(new Document("uuid", player.getUniqueId().toString()));

            getClansCollection(name).deleteOne(playerFilter);

            deleteClanIfEmpty(name);

            return true;

        }

    }

    public static MongoCollection<Document> getClansCollection(String name) {

        return clansDatabase.getCollection(name);

    }

    public static boolean clanExists(String name) {

        return Clans.getClansDatabase().listCollectionNames()
                .into(new ArrayList<>()).contains(name);

    }

    public static boolean clanIsEmpty(String name) {

        return clansDatabase.getCollection(name).countDocuments() > 1;

    }

    public static boolean isLeader(Player player) {

        Document filter = new Document(new Document("leader-uuid", player.getUniqueId().toString()));
        return clansDatabase.getCollection(getPlayerClan(player)).countDocuments(filter) == 1;

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

        return "none";

    }

    public static Player getClanLeader(String name) {

        Document filter = new Document(name, "settings");
        ArrayList<Document> docs = getClansCollection(name).find(filter)
                .into(new ArrayList<>());

        Document clanSettingsDoc = docs.get(0);

        String playerUUID = clanSettingsDoc.getString("leader-uuid");
        return Bukkit.getPlayer(UUID.fromString(playerUUID));

    }
    
    public static void setClanLeader(String name, Player player) {

        clansDatabase.getCollection(name).updateOne(Filters.eq(name, "settings"), Updates.set("leader-uuid", player.getUniqueId().toString()));
        
    }

}

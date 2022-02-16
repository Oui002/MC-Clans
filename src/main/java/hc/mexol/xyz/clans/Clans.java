package hc.mexol.xyz.clans;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import hc.mexol.xyz.clans.commands.*;
import hc.mexol.xyz.clans.listeners.OnChatEvent;
import hc.mexol.xyz.clans.listeners.PlayerDamageEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Clans extends JavaPlugin {

    private MongoClient mongoClient;
    private static MongoDatabase clansDatabase;

    public static MongoDatabase getClansDatabase() { return clansDatabase; }

    @Override
    public void onEnable() {

        mongoClient = MongoClients.create("mongodb+srv://Oui002:password@cluster0.jlhx6.mongodb.net/Cluster0?retryWrites=true&w=majority");
//        mongoClient = MongoClients.create("mongodb://localhost:27017");
        clansDatabase = mongoClient.getDatabase("Clans");

        getCommand("create").setExecutor(new CreateCommand());
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("leave").setExecutor(new LeaveCommand());
        getCommand("setclancolor").setExecutor(new SetClanPrefixColor());
        getCommand("kickplayer").setExecutor(new KickCommand());
        getCommand("transferclan").setExecutor(new TransferClanCommand());
        getCommand("accept").setExecutor(new AcceptCommand());
        getCommand("deny").setExecutor(new DenyCommand());

        Bukkit.getPluginManager().registerEvents(new PlayerDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new OnChatEvent(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

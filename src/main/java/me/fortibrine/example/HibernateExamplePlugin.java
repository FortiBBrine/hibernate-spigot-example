package me.fortibrine.example;

import lombok.Getter;
import me.fortibrine.example.entity.Player;
import me.fortibrine.example.listener.JoinListener;
import me.fortibrine.example.util.DatabaseManager;
import me.fortibrine.example.util.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HibernateExamplePlugin extends JavaPlugin {

    private DatabaseManager databaseManager;
    private PlayerManager playerManager;

    @Override
    public void onEnable() {

        saveDefaultConfig();
        this.databaseManager = new DatabaseManager(getConfig().getConfigurationSection("database"));
        this.playerManager = new PlayerManager(databaseManager);

        Player player = playerManager.getPlayer("adsasda");
        System.out.println(player.getScore());

        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
    }

    @Override
    public void onDisable() {
        databaseManager.close();
    }

}

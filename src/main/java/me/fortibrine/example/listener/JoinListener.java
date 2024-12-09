package me.fortibrine.example.listener;

import lombok.RequiredArgsConstructor;
import me.fortibrine.example.HibernateExamplePlugin;
import me.fortibrine.example.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

@RequiredArgsConstructor
public class JoinListener implements Listener {

    private final HibernateExamplePlugin plugin;

    @EventHandler
    public void join(PlayerJoinEvent event) {
        EntityManager entityManager = plugin.getDatabaseManager().getEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Player player = new Player();
        player.setUsername(event.getPlayer().getName());
        player.setScore(100);
        entityManager.persist(player);

        transaction.commit();
    }

}

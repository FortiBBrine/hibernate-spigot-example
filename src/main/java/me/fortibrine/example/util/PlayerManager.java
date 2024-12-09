package me.fortibrine.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import me.fortibrine.example.entity.Player;

import java.util.List;

@RequiredArgsConstructor
public class PlayerManager {

    private final DatabaseManager databaseManager;

    public Player getPlayer(String name) {
        EntityManager entityManager = databaseManager.getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = builder.createQuery(Player.class);

        Root<Player> root = criteriaQuery.from(Player.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("username"), name));

        TypedQuery<Player> query = entityManager.createQuery(criteriaQuery);

        List<Player> players = query.getResultList();

        if (players.isEmpty()) {
            return null;
        } else {
            return players.get(0);
        }

    }

    public void removePlayer(String name) {
        EntityManager entityManager = databaseManager.getEntityManager();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = builder.createQuery(Player.class);

        Root<Player> root = criteriaQuery.from(Player.class);
        criteriaQuery.select(root)
                .where(builder.equal(root.get("username"), name));

        TypedQuery<Player> query = entityManager.createQuery(criteriaQuery);

        List<Player> players = query.getResultList();

        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        players.forEach(entityManager::remove);

        transaction.commit();

    }

}

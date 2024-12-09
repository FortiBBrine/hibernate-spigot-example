package me.fortibrine.example.util;

import lombok.Getter;
import me.fortibrine.example.entity.Player;
import org.bukkit.configuration.ConfigurationSection;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import jakarta.persistence.EntityManager;
import java.io.Closeable;

@Getter
public class DatabaseManager implements Closeable {

    private final SessionFactory sessionFactory;
    private final EntityManager entityManager;

    public DatabaseManager(ConfigurationSection section) {

        Configuration configuration = new Configuration();

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.configuration.driver_class", "org.mariadb.jdbc.Driver");
        configuration.setProperty("hibernate.connection.username", section.getString("username"));
        configuration.setProperty("hibernate.connection.password", section.getString("password"));
        configuration.setProperty("hibernate.connection.url", "jdbc:mariadb://" + section.getString("host") + "/" + section.getString("database"));

        configuration.setProperty("hibernate.connection.autoReconnect", "true");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.addAnnotatedClass(Player.class);

        this.sessionFactory = configuration.buildSessionFactory();
        this.entityManager = sessionFactory.createEntityManager();
    }

    @Override
    public void close() {
        entityManager.close();
        sessionFactory.close();
    }

}

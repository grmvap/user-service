import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;

public class TestHibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory buildSessionFactory(String jdbcUrl, String username, String password) {
        if (sessionFactory != null) return sessionFactory;
        Properties settings = new Properties();
        settings.put("hibernate.connection.driver_class", "org.postgresql.Driver");
        settings.put("hibernate.connection.url", jdbcUrl);
        settings.put("hibernate.connection.username", username);
        settings.put("hibernate.connection.password", password);
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        settings.put("hibernate.hbm2ddl.auto", "update"); // или create-drop для чистоты
        settings.put("hibernate.show_sql", "false");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(settings)
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(com.example.model.User.class)
                .buildMetadata()
                .buildSessionFactory();

        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
}


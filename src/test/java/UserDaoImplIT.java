
import com.example.dao.impl.UserDaoImpl;
import com.example.model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoImplIT {

    // Testcontainers PostgreSQL
    private static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    private SessionFactory sessionFactory;
    private UserDaoImpl userDao;

    @BeforeAll
    public void startContainerAndSetup() {
        POSTGRES.start();
        String jdbcUrl = POSTGRES.getJdbcUrl();
        String username = POSTGRES.getUsername();
        String password = POSTGRES.getPassword();

        sessionFactory = TestHibernateUtil.buildSessionFactory(jdbcUrl, username, password);


        userDao = new UserDaoImpl();
    }

    @AfterAll
    public void stopContainer() {
        TestHibernateUtil.close();
        POSTGRES.stop();
    }

    @AfterEach
    public void cleanup() throws Exception {
        // Очистка таблицы users между тестами, чтобы обеспечить изоляцию
        try (var session = sessionFactory.openSession()) {
            var tx = session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY CASCADE").executeUpdate();
            tx.commit();
        }
    }

    @Test
    public void testCreateAndFindById() throws Exception {
        User u = new User("Alice", "alice@example.com", 30);
        User created = userDao.create(u);
        assertNotNull(created);
        assertNotNull(created.getId());

        Optional<User> fetched = userDao.findById(created.getId());
        assertTrue(fetched.isPresent());
        assertEquals("Alice", fetched.get().getUsername());
    }

    @Test
    public void testFindAllAndDelete() throws Exception {
        userDao.create(new User("A", "a@e.com", 20));
        userDao.create(new User("B", "b@e.com", 25));

        List<User> list = userDao.findAll();
        assertEquals(2, list.size());

        Long idToDelete = list.get(0).getId();
        boolean deleted = userDao.delete(idToDelete);
        assertTrue(deleted);

        List<User> after = userDao.findAll();
        assertEquals(1, after.size());
    }

    @Test
    public void testUpdate() throws Exception {
        User u = userDao.create(new User("Old", "old@e.com", 40));
        u.setUsername("NewName");
        User updated = userDao.update(u);
        assertEquals("NewName", updated.getUsername());
    }
}



import com.example.dao.UserDao;
import com.example.model.User;
import com.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService; // но в вашем UserService dao создаётся внутри — нужно адаптировать

    @BeforeEach
    void setup() throws Exception {
        MockitoAnnotations.openMocks(this);


        UserService realService = new UserService();
        java.lang.reflect.Field daoField = UserService.class.getDeclaredField("dao");
        daoField.setAccessible(true);
        daoField.set(realService, userDao);

        java.lang.reflect.Field thisRef = this.getClass().getDeclaredField("userService");
        thisRef.setAccessible(true);
        thisRef.set(this, realService);
    }

    @Test
    void create_callsDaoAndReturnsUser() throws Exception {
        User mockCreated = new User("Bob", "bob@e.com", 28);
        when(userDao.create(any(User.class))).thenReturn(mockCreated);

        User res = userService.create("Bob", "bob@e.com", 28);
        assertNotNull(res);
        assertEquals("Bob", res.getName());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDao, times(1)).create(captor.capture());
        assertEquals("bob@e.com", captor.getValue().getEmail());
    }

    @Test
    void getById_delegatesToDao() throws Exception {
        User u = new User("C", "c@e.com", 22);
        when(userDao.findById(1L)).thenReturn(Optional.of(u));

        Optional<User> opt = userService.getById(1L);
        assertTrue(opt.isPresent());
        assertEquals("C", opt.get().getName());
        verify(userDao).findById(1L);
    }

    @Test
    void listAll_delegatesToDao() throws Exception {
        List<User> sample = List.of(new User("X", "x@e.com", 10));
        when(userDao.findAll()).thenReturn(sample);

        List<User> out = userService.listAll();
        assertEquals(1, out.size());
        verify(userDao).findAll();
    }

    @Test
    void update_nonExisting_throws() throws Exception {
        when(userDao.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> userService.update(99L, "n", null, null));
    }

    @Test
    void update_existing_updatesFieldsAndCallsDao() throws Exception {
        User existing = new User("Old", "old@e.com", 40);
        existing.setId(5L);
        when(userDao.findById(5L)).thenReturn(Optional.of(existing));
        when(userDao.update(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        User changed = userService.update(5L, "New", "new@e.com", 50);
        assertEquals("New", changed.getName());
        assertEquals("new@e.com", changed.getEmail());
        assertEquals(50, changed.getAge());
        verify(userDao).update(any(User.class));
    }

    @Test
    void delete_delegatesToDao() throws Exception {
        when(userDao.delete(2L)).thenReturn(true);
        boolean res = userService.delete(2L);
        assertTrue(res);
        verify(userDao).delete(2L);
    }
}

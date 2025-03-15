package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.thread.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ThreadServiceImplTest {

    private ThreadServiceImpl threadService;
    private ThreadDAO threadDAO;
    private UserDAO userDAO;
    private Validator<Thread> threadValidator = new ThreadValidator(new RegexMatcher());

    @BeforeEach
    void setUp() throws SQLException {
        Database db = mock(Database.class);
        Connection connection = mock(Connection.class);
        when(db.connect()).thenReturn(connection);
        threadDAO = mock(ThreadDAO.class);
        userDAO = mock(UserDAO.class);
        threadValidator = mock(ThreadValidator.class);

        threadService = new ThreadServiceImpl(db, threadDAO, userDAO, threadValidator);
    }

    @Test
    void createValidThread() throws Exception {
        when(threadDAO.save(any(Thread.class))).thenReturn(1L);

        long result = threadService.createThread("user", "title", "body", Category.Announcements);

        assertEquals(1L, result);
        verify(threadDAO).save(any(Thread.class));
    }

    @Test
    void createThreadValidationFailed() {
        doThrow(IllegalArgumentException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(IllegalArgumentException.class, () ->
                threadService.createThread("user", "title", "body", Category.General));
    }

    @Test
    void removeThreadValid() throws Exception {
        threadService.removeThread(1L);
        verify(threadDAO).delete(1L);
    }

    @Test
    void removeThreadInvalid() {
        assertThrows(IllegalArgumentException.class, () -> threadService.removeThread(0L));
    }

    @Test
    void updateThreadThreadNotFound() throws Exception {
        when(threadDAO.get(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                threadService.updateThread(1L, "user", "newTitle", "newBody", Category.General));
    }

    @Test
    void findThreadByIdValid() throws Exception {
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);

        Thread result = threadService.findThreadById(1L);
        assertEquals(expected, result);
    }

    @Test
    void findThreadsByTitleAndCategoryValid() throws Exception {
        List<Thread> expected = new ArrayList<>();
        expected.add(new Thread());
        when(threadDAO.getThreadsByTitle(eq("title"), eq(Category.General), anyInt(), anyInt(), any(), any(), any()))
                .thenReturn(expected);

        List<Thread> result = threadService.findThreads("title", Category.General, 1, 10, Order.Newest, null, null);
        assertEquals(expected, result);
    }

    @Test
    void rateThreadValidVote() throws Exception {
        Thread thread = new Thread();
        thread.setArchived(false);
        User user = new User();
        when(threadDAO.get(1L)).thenReturn(thread);
        when(userDAO.get("user")).thenReturn(user);

        threadService.rateThread(1L, "user", 1);
        verify(threadDAO).voteThread(1L, "user", 1);
    }

    @Test
    void updateArchivedThreadCategory() throws Exception {
        Thread thread = new Thread();
        thread.setArchived(true);
        when(threadDAO.get(1L)).thenReturn(thread);

        assertThrows(ServiceException.class, () -> threadService.updateThreadCategory(1L, Category.General));
    }

    @Test
    void archiveThreadValid() throws Exception {
        Thread thread = new Thread();
        when(threadDAO.get(1L)).thenReturn(thread);

        threadService.archiveThread(1L);
        assertTrue(thread.isArchived());
        verify(threadDAO).update(thread);
    }

    @Test
    void getThreadCountValid() throws Exception {
        when(threadDAO.getThreadCount(any(), any(), any(), any())).thenReturn(5);

        int count = threadService.getThreadCount("title", Category.General, LocalDate.now(), LocalDate.now());
        assertEquals(5, count);
    }

    @Test
    void getPersonalVoteValid() throws Exception {
        when(threadDAO.get(1L)).thenReturn(new Thread());

        assertDoesNotThrow(() -> threadService.getPersonalVote(1L, "user"));
    }

    @Test
    void getPersonalVoteInvalid() throws Exception {
        when(threadDAO.get(1L)).thenReturn(null);

        assertThrows(ServiceException.class, () -> threadService.getPersonalVote(1L, "user"));
    }
}
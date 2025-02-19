package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.ConnectException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ThreadServiceImplTest {
    private ThreadService threadService;

    @BeforeEach
    void setUp() throws ConnectException {
        Database db = new DatabaseImpl("localhost", 3306, "root", "root_pw", "gametalk_db", "mysql");
        threadService = new ThreadServiceImpl(db);
    }

    @Test
    void createThreadTest() throws ServiceException {
        assertDoesNotThrow(() -> threadService.createThread("ApexLegends", "Switch 2 Reveal", "rosse molto rosse", Category.General));
    }

    @Test
    void createThreadInvalidTest() {
        assertThrows(ServiceException.class, () -> threadService.createThread("", "", "", null));
    }

    @Test
    void updateThreadTest() throws ServiceException {
        assertDoesNotThrow(() -> threadService.updateThread(11, "AlphaMaster", "Rossissime", "Rossissime molto rosse", Category.Announcements));
    }

    @Test
    void updateThreadInvalidTest() {
        assertThrows(ServiceException.class, () -> threadService.updateThread(-1, "", "", "", null));
    }

    @Test
    void deleteThreadTest() throws ServiceException {
        assertDoesNotThrow(() -> threadService.removeThread(11));
    }

    @Test
    void deleteThreadInvalidIdTest() {
        assertThrows(IllegalArgumentException.class, () -> threadService.removeThread(-1));
    }

    @Test
    void findThreadByIdTest() throws ServiceException {
        Thread thread = threadService.findThreadById(10);
        assertNotNull(thread);
        assertEquals(10, thread.getId());
    }

    @Test
    void findThreadByIdInvalidTest() {
        assertThrows(IllegalArgumentException.class, () -> threadService.findThreadById(-1));
    }

    @Test
    void findThreadsByTitleTest() throws ServiceException {
        for (Thread t : threadService.findThreads(null, null, 1, 12, null,null, LocalDate.of(2024, 1, 1))) {
            System.out.println(t);
        }
    }
    @Test
    void findThreadsGeneral() throws ServiceException {
        assertDoesNotThrow(() -> {
            var threads = threadService.findThreads(null, null, 1, 12, Order.Best, null, null);
            assertNotNull(threads);
            assertFalse(threads.isEmpty());
        });
    }

    @Test
    void findThreadsByCategoryTest() throws ServiceException {
        assertDoesNotThrow(() -> {
            var threads = threadService.findThreads(null, Category.General, 1, 12, Order.Best, null, null);
            assertNotNull(threads);
            assertFalse(threads.isEmpty());
        });
    }

    @Test
    void findThreadsByTitleAndCategoryTest() throws ServiceException {
        assertDoesNotThrow(() -> {
            var threads = threadService.findThreads("sw", Category.Memes, 1, 12, Order.Best, null, null);
            assertNotNull(threads);
            assertFalse(threads.isEmpty());
        });
    }

    @Test
    void findThreadsByUsernameTest() throws ServiceException {
        assertDoesNotThrow(() -> {
            var threads = threadService.findThreadsByUsername("AlphaMaster", 1, 12, Order.Best);
            assertNotNull(threads);
            assertFalse(threads.isEmpty());
        });
    }

    @Test
    void rateThreadTestUpvote() throws ServiceException {
        threadService.rateThread(7,"ApexLegends",1);
    }

    @Test
    void findThreadsInvalidPaginationTest() {
        assertThrows(IllegalArgumentException.class, () -> threadService.findThreads("sw", Category.Memes, -1, 0, Order.Best, null, null));
    }

    @Test
    void changeCategoryTest() throws ServiceException {
        threadService.updateThreadCategory(7,Category.Memes);
    }

    @Test
    void archiveThreadTest() throws ServiceException {
        threadService.archiveThread(7);
    }
}

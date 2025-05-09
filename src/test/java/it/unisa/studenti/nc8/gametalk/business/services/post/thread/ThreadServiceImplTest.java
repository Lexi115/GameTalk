package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.thread.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ThreadServiceImplTest {

    private ThreadServiceImpl threadService;
    private ThreadDAO threadDAO;
    private UserDAO userDAO;
    private Validator<Thread> threadValidator = new ThreadValidator(new RegexMatcher());
    private Database db;

    @BeforeEach
    void setUp() throws SQLException {
        db = mock(Database.class);
        Connection connection = mock(Connection.class);
        when(db.connect()).thenReturn(connection);
        threadDAO = mock(ThreadDAO.class);
        userDAO = mock(UserDAO.class);
        threadValidator = mock(ThreadValidator.class);

        threadService = new ThreadServiceImpl(db, threadDAO, userDAO, threadValidator);
    }

    //Unit test di createThread con category partition
    @Test
    void createValidThreadTS1() throws Exception {
        when(threadDAO.save(any(Thread.class))).thenReturn(1L);

        long result = threadService.createThread("user", "title", "body", Category.Announcements);

        assertEquals(1L, result);
        verify(threadDAO).save(any(Thread.class));
    }

    @Test
    void createThreadValidationFailedTS2() {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(ValidationException.class, () ->
                threadService.createThread(null, "title", "body", Category.General));
    }

    @Test
    void createThreadValidationFailedTS3() {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(ValidationException.class, () ->
                threadService.createThread("title", null, "body", Category.General));
    }

    @Test
    void createThreadValidationFailedTS4() {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(ValidationException.class, () ->
                threadService.createThread("title", "title", null, Category.General));
    }

    @Test
    void createThreadValidationFailedTS5() {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(ValidationException.class, () ->
                threadService.createThread("title", "title", "body", null));
    }

    @Test
    void createThreadValidationFailedTS6() {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));

        assertThrows(ValidationException.class, () ->
                threadService.createThread("title", "title", "body", Category.General));
    }

    @Test
    void createThreadSQLExceptionTS7() throws SQLException {
        doThrow(SQLException.class).when(db).connect();

        assertThrows(ServiceException.class, () ->
                threadService.createThread("title", "title", "body", Category.General));
    }

    @Test
    void createThreadDAOExceptionTS8() throws DAOException {
        doThrow(DAOException.class).when(threadDAO).save(any(Thread.class));

        assertThrows(ServiceException.class, () ->
                threadService.createThread("title", "title", "body", Category.General));
    }

    //Fine unit test Carmelo
    /*
        Inizio unit testing
        updateThread()

        Autore: Alessio Sica
     */

    /*
        UTC_TS_U_1
        Tutto valido
     */
    @Test
    void updateThreadTSU1() throws Exception {
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);

        threadService.updateThread(1, "title", "body", Category.Announcements);
        verify(threadDAO).update(any(Thread.class));
    }

    /*
        UTC_TS_U_2
        ID Thread non valido
     */
    @Test
    void updateThreadTSU2() throws Exception {
        when(threadDAO.get(5L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class,
                () -> threadService.updateThread(5, "title", "body", Category.Announcements));
    }

    /*
        UTC_TS_U_3
        Thread archiviato
     */
    @Test
    void updateThreadTSU3() throws Exception {
        Thread expected = new Thread();
        expected.setArchived(true);
        when(threadDAO.get(2L)).thenReturn(expected);

        assertThrows(IllegalArgumentException.class,
                () -> threadService.updateThread(2, "title", "body", Category.Announcements));
    }

    /*
        UTC_TS_U_4
        Titolo nullo
     */
    @Test
    void updateThreadTSU4() throws Exception {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ValidationException.class,
                () -> threadService.updateThread(1, null, "body", Category.Announcements));
    }

    /*
        UTC_TS_U_5
        Corpo nullo
     */
    @Test
    void updateThreadTSU5() throws Exception {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ValidationException.class,
                () -> threadService.updateThread(1, "title", null, Category.Announcements));
    }

    /*
        UTC_TS_U_6
        Categoria nulla
     */
    @Test
    void updateThreadTSU6() throws Exception {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ValidationException.class,
                () -> threadService.updateThread(1, "title", "body", null));
    }

    /*
        UTC_TS_U_7
        Uno o più campi non sono validi
     */
    @Test
    void updateThreadTSU7() throws Exception {
        doThrow(ValidationException.class).when(threadValidator).validate(any(Thread.class));
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ValidationException.class,
                () -> threadService.updateThread(1, "", "", null));
    }

    /*
        UTC_TS_U_8
        Tutto valido ma il database va in errore (SQLException)
     */
    @Test
    void updateThreadTSU8() throws Exception {
        doThrow(SQLException.class).when(db).connect();
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ServiceException.class,
                () -> threadService.updateThread(1, "title", "body", Category.Announcements));
    }

    /*
        UTC_TS_U_9
        Tutto valido ma il DAO va in errore (DAOException)
     */
    @Test
    void updateThreadTSU9() throws Exception {
        doThrow(DAOException.class).when(threadDAO).update(any(Thread.class));
        Thread expected = new Thread();
        when(threadDAO.get(1L)).thenReturn(expected);
        assertThrows(ServiceException.class,
                () -> threadService.updateThread(1, "title", "body", Category.Announcements));
    }

    // Fine unit test Alessio
    //unit test findThreadByUsername

    @Test
    public void test_TC01_Valido() throws Exception {
        when(db.connect()).thenReturn(mock(Connection.class));
        when(threadDAO.getThreadsByUsername(
                eq("user123"), anyInt(), anyInt(), eq(Order.Best), any(), any()))
                .thenReturn(List.of(new Thread()));

        List<Thread> result = threadService.findThreadsByUsername(
                "user123", 1, 10, Order.Best,
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-12-31"));

        assertNotNull(result);
    }

    @Test
    public void test_TC02_UsernameVuoto() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                threadService.findThreadsByUsername("", 1, 10, Order.Best,
                        LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2023-12-31"))
        );
        assertEquals("Username non valido.", e.getMessage());
    }

    @Test
    public void test_TC03_UsernameNull() {
        Exception e = assertThrows(IllegalArgumentException.class, () ->
                threadService.findThreadsByUsername(null, 1, 10, Order.Best,
                        LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2023-12-31"))
        );
        assertEquals("Username non valido.", e.getMessage());
    }

    @Test
    public void test_TC04_PageZero() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername("user123", 0, 10, Order.Best,
                        LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC05_PageSizeZero() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername("user123", 1, 0, Order.Best,
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC06_OrderNewest() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername(
                "user123", 1, 10, Order.Newest,
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC07_OrderOldest() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername(
                "user123", 1, 10, Order.Oldest,
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC08_OrderNull() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername("user123", 1, 10,
                        null,
                        LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC09_StartAfterEndDate() throws ServiceException {
        LocalDate start = LocalDate.parse("2024-12-31");
        LocalDate end = LocalDate.parse("2023-01-01");
        List<Thread> result = threadService.findThreadsByUsername("user123", 1, 10,
                        Order.Best, start, end);
        assertNotNull(result);
    }

    @Test
    public void test_TC10_StartDateNonValida() {
        assertThrows(DateTimeParseException.class, () ->
                LocalDate.parse("invalid-date")
        );
    }

    @Test
    public void test_TC11_StartDateNull() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername(
                "user123", 1, 10, Order.Best,
                null, LocalDate.parse("2023-12-31"));
        assertNotNull(result);
    }

    @Test
    public void test_TC12_EndDateNonValida() {
        assertThrows(DateTimeParseException.class, () ->
                LocalDate.parse("invalid-date")
        );
    }

    @Test
    public void test_TC13_EndDateNull() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername(
                "user123", 1, 10, Order.Best,
                LocalDate.parse("2023-01-01"), null);
        assertNotNull(result);
    }

    @Test
    public void test_TC14_EndDatePrimaDiStartDate() throws ServiceException {
        List<Thread> result = threadService.findThreadsByUsername("user123", 1, 10,
                        Order.Best, LocalDate.parse("2023-01-01"),
                        LocalDate.parse("2022-12-31"));
        assertNotNull(result);
    }
    //fine unit test Antonino

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
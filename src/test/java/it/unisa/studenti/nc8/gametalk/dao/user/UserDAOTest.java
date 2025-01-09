package it.unisa.studenti.nc8.gametalk.dao.user;

import it.unisa.studenti.nc8.gametalk.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserDAOTest {
    static UserDAOImpl userDAO;

    @BeforeAll
    public static void setUp() throws DAOException {
        userDAO = Mockito.mock(UserDAOImpl.class);
        Mockito.when(userDAO.get(1)).thenReturn(new User());
    }

    @Test
    public void testGet() throws DAOException {
        User user = userDAO.get(1);
        Assertions.assertNotNull(user);
    }
}

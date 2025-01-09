package it.unisa.studenti.nc8.gametalk.service.user;

import it.unisa.studenti.nc8.gametalk.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.persistence.Database;
import it.unisa.studenti.nc8.gametalk.persistence.mappers.user.UserMapper;

public class UserService {
    private final UserDAO userDAO;

    public UserService(Database db) {
        this.userDAO = new UserDAOImpl(db, new UserMapper());
    }
}

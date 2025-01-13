package it.unisa.studenti.nc8.gametalk.business.service.user;

import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;

/**
 * Classe di servizio per la gestione di oggetti {@link User}.
 */
public class UserService {

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link User}.
     */
    private final UserDAO userDAO;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public UserService(final Database db) {
        this.userDAO = new UserDAOImpl(db, new UserMapper());
    }
}

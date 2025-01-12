package it.unisa.studenti.nc8.gametalk.storage.dao.user;

import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.util.List;

public interface UserDAO extends DAO<User> {
    List<User> getUsersByUsername(String username, int page);
    List<User> getStruckUsers(int page);
    List<User> getBannedUsers(int page);
}

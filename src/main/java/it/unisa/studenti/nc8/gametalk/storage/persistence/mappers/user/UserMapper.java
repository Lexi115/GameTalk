package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ResultSetMapper} per mappare i
 * risultati di una query {@link ResultSet} in una lista di oggetti
 * {@link User}.
 *
 * @version 1.0
 */
public class UserMapper implements ResultSetMapper<User> {

    /**
     * Mappa i risultati di una query SQL (un {@link ResultSet}) in una lista di
     * oggetti {@link User}.
     *
     * @param rs il {@link ResultSet} contenente i risultati della query
     * @return una lista di oggetti {@link User} corrispondenti ai dati del
     * {@link ResultSet}
     * @throws SQLException se si verifica un errore durante l'accesso ai
     * dati nel {@link ResultSet}
     */
    @Override
    public List<User> map(final ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();

        while (rs.next()) {
            User user = new User();
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password_hash"));
            user.setCreationDate(rs.getDate("creation_date").toLocalDate());
            user.setBanned(rs.getBoolean("banned"));
            user.setStrikes(rs.getInt("strikes"));
            user.setRole(Role.valueOf(rs.getString("role")));

            users.add(user);
        }

        return users;
    }
}

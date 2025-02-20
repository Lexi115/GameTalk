package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ResultSetMapper} per mappare i
 * risultati di una query {@link ResultSet} in una lista di oggetti
 * {@link User}.
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

        try {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setCreationDate(rs.getDate("creation_date").toLocalDate());
                user.setBanned(rs.getBoolean("banned"));
                user.setRole(Role.valueOf(rs.getString("role")));
                user.setAuthToken(rs.getString("auth_token"));
                users.add(user);
            }
        } catch (IllegalArgumentException e) {
            throw new SQLException(e);
        }

        return users;
    }
}

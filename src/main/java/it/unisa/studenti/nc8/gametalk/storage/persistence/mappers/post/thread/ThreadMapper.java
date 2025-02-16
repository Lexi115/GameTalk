package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ResultSetMapper} per mappare i
 * risultati di una query {@link ResultSet} in una lista di oggetti
 * {@link Thread}.
 *
 * @version 1.0
 */
public class ThreadMapper implements ResultSetMapper<Thread> {

    /**
     * Mappa i risultati di una query SQL (un {@link ResultSet}) in una lista di
     * oggetti {@link Thread}.
     *
     * @param rs il {@link ResultSet} contenente i risultati della query
     * @return una lista di oggetti {@link Thread} corrispondenti ai dati del
     * {@link ResultSet}
     * @throws SQLException se si verifica un errore durante l'accesso ai
     * dati nel {@link ResultSet}
     */
    @Override
    public List<Thread> map(final ResultSet rs) throws SQLException {
        List<Thread> threads = new ArrayList<>();

        while (rs.next()) {
            Thread thread = new Thread();
            thread.setId(rs.getLong("id"));
            thread.setUsername(rs.getString("username"));
            thread.setTitle(rs.getString("title"));
            thread.setBody(rs.getString("body"));
            thread.setVotes(rs.getInt("votes"));
            thread.setArchived(rs.getBoolean("archived"));
            thread.setCategory(Category.valueOf(rs.getString("category")));
            thread.setCreationDate(rs.getDate("creation_date").toLocalDate());

            threads.add(thread);
        }

        return threads;
    }
}

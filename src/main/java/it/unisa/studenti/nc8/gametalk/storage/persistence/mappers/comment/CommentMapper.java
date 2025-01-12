package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment;

import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementazione dell'interfaccia {@link ResultSetMapper} per mappare i
 * risultati di una query {@link ResultSet} in una lista di oggetti
 * {@link Comment}.
 *
 * @version 1.0
 */
public class CommentMapper implements ResultSetMapper<Comment> {

    /**
     * Mappa i risultati di una query SQL (un {@link ResultSet}) in una lista di
     * oggetti {@link Comment}.
     *
     * @param rs il {@link ResultSet} contenente i risultati della query
     * @return una lista di oggetti {@link Comment} corrispondenti ai dati del
     * {@link ResultSet}
     * @throws SQLException se si verifica un errore durante l'accesso ai
     * dati nel {@link ResultSet}
     */
    @Override
    public List<Comment> map(final ResultSet rs) throws SQLException {
        List<Comment> comments = new ArrayList<>();

        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getLong("id"));
            comment.setThreadId(rs.getLong("thread_id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setBody(rs.getString("body"));
            comment.setVotes(rs.getInt("votes"));
            comment.setCreationDate(rs.getDate("creation_date"));

            comments.add(comment);
        }

        return comments;
    }
}

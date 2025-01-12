package it.unisa.studenti.nc8.gametalk.storage.dao.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

/**
 * Definizione dell'interfaccia DAO per l'entità {@link Comment}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione con il database
 * per le operazioni CRUD relative all'entità {@link Comment}.
 * <p>
 * Estende {@link DAO<Comment>}.
 *
 * @version 1.0
 */
public interface CommentDAO extends DAO<Comment> {

    /**
     * Recupera una lista di commenti in base all'ID del thread, con supporto per la paginazione.
     *
     * @param threadId l'ID del thread per cui recuperare i commenti
     * @param page il numero della pagina da recuperare
     * @param limit il numero massimo di commenti per pagina
     * @return una lista di commenti del thread specificato
     * @throws DAOException se si verifica un errore durante l'esecuzione della query
     */
    List<Comment> getCommentsByThreadId(long threadId, int page,int limit) throws DAOException;
}

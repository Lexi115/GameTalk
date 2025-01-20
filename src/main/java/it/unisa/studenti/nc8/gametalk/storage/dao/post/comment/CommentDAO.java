package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;

import java.util.List;

/**
 * Definizione dell'interfaccia DAO per l'entità {@link Comment}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione
 * con il database per le operazioni CRUD relative all'entità {@link Comment}.
 * <p>
 * Estende {@link DAO<Comment>}.
 *
 * @version 1.0
 */
public interface CommentDAO extends DAO<Comment, Long> {

    /**
     * Recupera una lista di commenti in base all'ID del thread,
     * con supporto per la paginazione.
     *
     * @param threadId l'ID del thread per cui recuperare i commenti
     * @param page il numero della pagina da recuperare
     * @param limit il numero massimo di commenti per pagina
     * @return una lista di commenti del thread specificato
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    List<Comment> getCommentsByThreadId(long threadId, int page, int limit)
            throws DAOException;

    /**
     * Conta i commenti di un determinato thread.
     *
     * @param threadId l'ID del thread corrispondente.
     * @return il numero totale di commenti del thread.
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    long countCommentsByThreadId(long threadId)
            throws DAOException;

    /**
     * Vota un commento associato al suo ID.
     * Se l'utente ha già votato il commento, il voto viene aggiornato con
     * il nuovo valore.
     *
     * @param commentId ID del commento da votare.
     * @param username Nome dell'utente che sta effettuando il voto.
     * @param vote Valore del voto da assegnare al commento. Deve essere:
     *             <ul>
     *             <li>-1: Downvote.</li>
     *             <li>0: Voto neutro o rimozione del voto (se presente).</li>
     *             <li>1: Upvote.</li>
     *             </ul>
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    void voteComment(
            long commentId,
            String username,
            int vote
    ) throws DAOException;

    /**
     * Rimuove il voto di un commento associato al suo ID se questo esiste.
     *
     * @param commentId ID del commento di cui rimuovere il voto.
     * @param username Nome dell'utente che ha espresso il voto da rimuovere.
     *
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     * del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     * nel database.
     */
    void removeVoteComment(
            long commentId,
            String username
    ) throws DAOException;
}

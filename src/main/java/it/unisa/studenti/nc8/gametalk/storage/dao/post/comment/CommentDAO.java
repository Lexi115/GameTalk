package it.unisa.studenti.nc8.gametalk.storage.dao.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.utils.Bindable;

import java.util.List;
import java.util.Map;

/**
 * Interfaccia DAO per l'entità {@link Comment}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione
 * con il sistema di persistenza per le operazioni CRUD relative
 * all'entità {@link Comment}.
 */
public interface CommentDAO extends DAO<Comment, Long>, Bindable {

    /**
     * Recupera una lista di commenti in base all'ID del thread,
     * con supporto per la paginazione.
     *
     * @param threadId l'ID del thread per cui recuperare i commenti
     * @param username Il nome utente del richiedente, vuoto
     *                 se non è loggato.
     * @param page il numero della pagina da recuperare
     * @param limit il numero massimo di commenti per pagina
     * @return una lista di commenti del thread specificato
     * @throws DAOException se si verifica un errore durante
     * l'esecuzione della query
     */
    List<Comment> getCommentsByThreadId(
            long threadId,
            String username,
            int page,
            int limit
    ) throws DAOException;

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
     * @param threadId L'id del thread a cui appartiene il commento.
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
            long threadId,
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

    /**
     * Recupera i voti personali di un utente sui commenti sotto un thread.
     *
     * @param threadId L'ID del thread di cui recuperare i voti.
     * @param username Il nome utente dell'utente per cui recuperare i voti.
     * @return Una mappa in cui le chiavi sono gli ID dei commenti
     *         e i valori sono i voti assegnati dall'utente.
     * @throws DAOException Se si verifica un errore durante il recupero dei
     * voti dal database.
     */
    Map<Long, Integer> getPersonalVotes(
            long threadId,
            String username
    ) throws DAOException;
}

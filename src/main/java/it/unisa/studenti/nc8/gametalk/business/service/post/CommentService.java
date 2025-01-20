package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

/**
 * Interfaccia di servizio per la gestione dei {@link Comment}.
 * Fornisce metodi per aggiungere, rimuovere e cercare commenti.
 */
public interface CommentService {

    /**
     * Aggiunge un nuovo commento a un thread esistente e lo salva nel database.
     * Il commento viene validato prima di essere salvato.
     *
     * @param threadId L'ID del thread a cui il commento appartiene.
     * @param username L'ID dell'utente che ha scritto il commento.
     * @param body Il corpo del commento.
     *
     * @throws ServiceException Se il commento non è valido o
     * se si verifica un errore durante il salvataggio nel database.
     */
    void addComment(
            long threadId,
            String username,
            String body
    ) throws ServiceException;

    /**
     * Rimuove un commento esistente.
     *
     * @param id l'ID del commento da rimuovere.
     * @throws ServiceException se si è verificato un errore.
     */
    void deleteComment(long id) throws ServiceException;

    /**
     * Restituisce un commento dato il suo ID.
     *
     * @param id l'ID del commento da recuperare.
     * @return il commento con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    Comment findCommentById(long id) throws ServiceException;

    /**
     * Recupera i commenti di un thread, con supporto per la paginazione.
     *
     * @param threadId Il ID del thread di cui recuperare i commenti.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di commenti per pagina.
     * @return Una lista di commenti del thread specificato.
     * @throws IllegalArgumentException se il <code>threadId</code>,
     * <code>page</code> o <code>pageSize</code> sono minori o uguali a 0
     * @throws ServiceException se si è verificato un errore.
     */
    List<Comment> findCommentsByThreadId(
            long threadId,
            int page,
            int pageSize
    ) throws ServiceException;

    /**
     * Restituisce il numero totale di commenti di un thread.
     *
     * @param threadId Il ID del thread di cui recuperare i commenti.
     * @return il numero totale di commenti del thread.
     * @throws IllegalArgumentException se il <code>threadId</code>
     * è minore o uguale a 0
     * @throws ServiceException se si è verificato un errore.
     */
    long countCommentsByThreadId(long threadId) throws ServiceException;

    /**
     * Permette a un utente di votare un commento, con la possibilità di
     * rimuovere un voto esistente (impostando il voto a 0). In caso di
     * voto invalido, o se il commento non esiste, viene sollevata un'eccezione.
     *
     * @param commentId ID del commento da votare.
     * @param username Nome utente dell'utente che sta effettuando il voto.
     * @param vote Valore del voto da assegnare al commento, deve essere:
     *             <ul>
     *             <li>-1: Downvote.</li>
     *             <li>0: Rimozione del voto esistente (se presente).</li>
     *             <li>1: Upvote.</li>
     *             </ul>
     *
     * @throws ServiceException Se si verifica un errore durante l'elaborazione
     * del voto, come:
     * <ul>
     * <li>Il commento con l'ID specificato non esiste.</li>
     * <li>Errore durante l'aggiunta del voto.</li>
     * </ul>
     * @throws IllegalArgumentException Se il valore del voto non è valido
     * (diverso da -1, 0, 1).
     *
     */
    void rateComment(
            long commentId,
            String username,
            int vote
    ) throws ServiceException;
}

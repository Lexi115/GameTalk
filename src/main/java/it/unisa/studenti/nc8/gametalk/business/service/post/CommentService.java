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
     * Aggiunge un nuovo commento.
     *
     * @param comment Il commento da aggiungere.
     * @throws ServiceException se si è verificato un errore.
     */
    void addComment(Comment comment) throws ServiceException;

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
     * Restituisce tutti i commenti presenti nel sistema di persistenza.
     *
     * @return una lista di tutti i commenti.
     * @throws ServiceException se si è verificato un errore.
     */
    List<Comment> findAllComments() throws ServiceException;

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
    List<Comment> findCommentsFromThreadId(
            long threadId,
            int page,
            int pageSize
    ) throws ServiceException;
}

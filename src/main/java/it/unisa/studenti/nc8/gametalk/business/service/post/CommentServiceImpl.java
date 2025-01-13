package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Comment}.
 */
public class CommentServiceImpl implements CommentService {

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link Comment}.
     */
    private final CommentDAO commentDAO;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public CommentServiceImpl(final Database db) {
        this.commentDAO = new CommentDAOImpl(db, new CommentMapper());
    }

    /**
     * Aggiunge un nuovo commento.
     *
     * @param comment Il commento da aggiungere.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void addComment(final Comment comment) throws ServiceException {

    }

    /**
     * Rimuove un commento esistente.
     *
     * @param id l'id del commento da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void deleteComment(final long id) throws ServiceException {

    }

    /**
     * Restituisce un commento dato il suo ID.
     *
     * @param id l'ID del commento da recuperare.
     * @return il commento con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    public Comment findCommentById(final long id) throws ServiceException {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("ID commento non valido");
            }

            return commentDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore recupero commento tramite ID.", e);
        }
    }

    /**
     * Restituisce tutti i commenti presenti nel sistema di persistenza.
     *
     * @return una lista di tutti i commenti.
     * @throws ServiceException se si è verificato un errore.
     */
    public List<Comment> findAllComments() throws ServiceException {
        try {
            return commentDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore recupero commenti.", e);
        }
    }

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
    @Override
    public List<Comment> findCommentsFromThreadId(
            final long threadId,
            final int page,
            final int pageSize
    ) throws ServiceException {
        return List.of();
    }
}

package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.dao.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Comment}.
 */
public class CommentService {

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
    public CommentService(final Database db) {
        this.commentDAO = new CommentDAOImpl(db, new CommentMapper());
    }

    /**
     * Restituisce un commento dato il suo ID.
     *
     * @param id l'ID del commento da recuperare.
     * @return il commento con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si verifica un errore nell'accesso
     * al database.
     */
    public Comment getCommentById(final long id) throws ServiceException {
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
     * Restituisce tutti i commenti presenti nel database.
     *
     * @return una lista di tutti i commenti.
     * @throws ServiceException se si verifica un errore nell'accesso
     * al database.
     */
    public List<Comment> getAllComments() throws ServiceException {
        try {
            return commentDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore recupero commenti.", e);
        }
    }

    /**
     * Restituisce tutti i commenti associati a un thread specifico.
     *
     * @param threadId l'ID del thread di cui recuperare i commenti.
     * @return una lista di commenti associati al thread.
     * @throws IllegalArgumentException se l'ID del thread è minore o
     * uguale a 0.
     * todo
     */
    public List<Comment> getCommentsByThreadId(final long threadId) {
        if (threadId <= 0) {
            throw new IllegalArgumentException("ID thread non valido");
        }
        return List.of();
    }

    /**
     * Aggiorna un commento esistente nel database.
     *
     * @param comment il commento da aggiornare.
     * @throws ServiceException se si verifica un errore nell'accesso
     * al database.
     */
    public void updateComment(final Comment comment) throws ServiceException {
        try {
            commentDAO.update(comment);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore aggiornamento commento", e);
        }
    }
}

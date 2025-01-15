package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.validators.CommentValidator;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Comment}.
 */
public class CommentServiceImpl implements CommentService {

    /**
     * Oggetto {@link Database}, contiene informazioni sul database
     * con cui interagire.
     */
    private final Database db;
    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link Comment}.
     */
    private final CommentDAO commentDAO;

    /**
     * Il validator che valida i dati contenuti in
     * un oggetto {@link Comment}.
     */
    private final Validator<Comment> commentValidator;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public CommentServiceImpl(final Database db) {
        this.db = db;
        this.commentDAO = new CommentDAOImpl(db, new CommentMapper());
        this.commentValidator = new CommentValidator();
    }

    /**
     * Aggiunge un nuovo commento a un thread esistente e lo salva nel database.
     * Il commento viene validato prima di essere salvato.
     *
     * @param id L'ID del nuovo commento.
     * @param threadId L'ID del thread a cui il commento appartiene.
     * @param userId L'ID dell'utente che ha scritto il commento.
     * @param body Il corpo del commento.
     *
     * @throws ServiceException Se il commento non è valido o se si verifica
     *                          un errore durante il salvataggio nel database.
     */
    public void addComment(final long id,
                           final long threadId,
                           final long userId,
                           final String body)
            throws ServiceException {

        //Inizializzazione oggetto Comment
        Comment newComment = new Comment();
        newComment.setId(id);
        newComment.setThreadId(threadId);
        newComment.setUserId(userId);
        newComment.setBody(body);
        newComment.setVotes(0);
        newComment.setCreationDate(LocalDate.now());

        //Validazione commento
        if (!commentValidator.validate(newComment)) {
            throw new ServiceException("Commento non valido");
        }

        //Salvataggio comment
        try (db) {

            db.connect();
            commentDAO.save(newComment);

        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante il salvataggio del commento", e);
        }
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
            if (id <= 0) {
                throw new IllegalArgumentException(
                        "Id deve essere maggiore di 0");
            }
            try (db) {

                db.connect();
                commentDAO.delete(id);

        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante l'eliminazione del commento", e);
        }
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
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "ID deve essere maggiore di 0");
        }

        try (db) {

            db.connect();
            return commentDAO.get(id);

        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore recupero commento tramite ID.", e);
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

        //Sanificazione TODO da spostare
        int realPage = Math.max(page, 1);

        //Verifica id valido
        if (threadId <= 0) {
            throw new IllegalArgumentException(
                    "threadId deve essere maggiore di 0");
        }

        //Recupero commenti dal thread
        try (db) {

            db.connect();
            return commentDAO.getCommentsByThreadId(
                    threadId,
                    realPage,
                    pageSize);

        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante il recupero dei "
                            + "commenti appartenenti al thread " + threadId, e);
        }
    }
}

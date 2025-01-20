package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.business.validators.post.comment.CommentValidator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.comment.CommentDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.comment.CommentMapper;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.user.UserMapper;

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
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link User}.
     */
    private final UserDAO userDAO;

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link Thread}.
     */
    private final ThreadDAO threadDAO;

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
        this.userDAO = new UserDAOImpl(db, new UserMapper());
        this.threadDAO = new ThreadDAOImpl(db, new ThreadMapper());
        this.commentValidator = new CommentValidator();
    }

    /**
     * Aggiunge un nuovo commento a un thread esistente e lo salva nel database.
     * Il commento viene validato prima di essere salvato.
     *
     * @param threadId L'ID del thread a cui il commento appartiene.
     * @param username L'ID dell'utente che ha scritto il commento.
     * @param body Il corpo del commento.
     *
     * @throws ServiceException Se il commento non è valido o se si verifica
     *                          un errore durante il salvataggio nel database.
     */
    public void addComment(
            final long threadId,
            final String username,
            final String body
    ) throws ServiceException {

        //Inizializzazione oggetto Comment
        Comment newComment = new Comment();
        newComment.setThreadId(threadId);
        newComment.setUsername(username);
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
            db.beginTransaction();

            //Verifica thread non archiviato
            Thread thread = threadDAO.get(threadId);
            if (thread == null) {
                throw new ServiceException(
                        "Nessun thread trovato con id " + threadId);
            }

            if (thread.isArchived()) {
                throw new ServiceException("Thread archiviato");
            }

            commentDAO.save(newComment);
            db.commit();
        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException ex) {
                throw new ServiceException(ex);
            }
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
                db.beginTransaction();
                //Verifica thread non archiviato
                Comment comment = commentDAO.get(id);
                if (comment == null) {
                    throw new ServiceException(
                            "Comment non trovato con id " + id);
                }

                Thread thread = threadDAO.get(comment.getThreadId());
                if (thread == null) {
                    throw new ServiceException("Thread non trovato");
                }

                if (thread.isArchived()) {
                    throw new ServiceException("Thread archiviato");
                }

                commentDAO.delete(id);
                db.commit();
        } catch (SQLException | DAOException e) {
                try {
                    db.rollback();
                } catch (SQLException ex) {
                    throw new ServiceException(ex);
                }
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
    public List<Comment> findCommentsByThreadId(
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

    /**
     * Restituisce il numero totale di commenti di un thread.
     *
     * @param threadId Il ID del thread di cui recuperare i commenti.
     * @return il numero totale di commenti del thread.
     * @throws IllegalArgumentException se il <code>threadId</code>
     *                                  è minore o uguale a 0
     * @throws ServiceException         se si è verificato un errore.
     */
    @Override
    public long countCommentsByThreadId(
            final long threadId
    ) throws ServiceException {
        // Verifica id valido
        if (threadId <= 0) {
            throw new IllegalArgumentException(
                    "threadId deve essere maggiore di 0");
        }

        // Recupero commenti dal thread
        try (db) {
            db.connect();
            return commentDAO.countCommentsByThreadId(threadId);
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante il conteggio dei "
                            + "commenti appartenenti al thread " + threadId, e);
        }
    }

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
    @Override
    public void rateComment(
            final long commentId,
            final String username,
            final int vote
    ) throws ServiceException {

        //Sanificazione
        if (vote < -1 || vote > 1) {
            throw new IllegalArgumentException("Voto non valido");
        }

        try (db) {

            db.connect();
            db.beginTransaction();

            //Verifico l'esistenza del commento
            Comment comment = commentDAO.get(commentId);
            if (comment == null) {
                throw new ServiceException(
                        "Nessun commento trovato con id " + commentId);
            }

            //Verifico l'esistenza dell'utente
            User user = userDAO.get(username);
            if (user == null) {
                throw new ServiceException(
                        "Nessun utente trovato con username " + username);
            }

            //Verifico se il thread a cui appartiene il commento è archiviato
            Thread thread = threadDAO.get(comment.getThreadId());
            if (thread == null) {
                throw new ServiceException("Thread non trovato");
            }

            if (thread.isArchived()) {
                throw new ServiceException("Thread archiviato");
            }

            //Commento esiste, lo voto da parte dell utente
            if (vote == 0) {
                //Rimuovo il voto
                commentDAO.removeVoteComment(commentId, username);
            } else {
                //Inserisco il voto
                commentDAO.voteComment(commentId, username, vote);
            }

            db.commit();

        } catch (SQLException | DAOException e) {
            try {
                db.rollback();
            } catch (SQLException ex) {
                throw new ServiceException(ex);
            }
            throw new ServiceException(
                    "Errore durante l'aggiunta del voto " + vote
                            + " al commento " + commentId, e);
        }
    }
}

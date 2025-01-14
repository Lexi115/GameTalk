package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.validators.ThreadValidator;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.time.LocalDate;
import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Thread}.
 * Fornisce metodi per recuperare e cercare thread.
 */
public class ThreadServiceImpl implements ThreadService {

    /**
     * Il DAO utilizzato per effettuare operazioni CRUD
     * su oggetti {@link Thread}.
     */
    private final ThreadDAO threadDAO;

    /**
     * Il validator che valida i dati contenuti in
     * un oggetto {@link Thread}.
     */
    private final Validator<Thread> threadValidator;

    /**
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public ThreadServiceImpl(final Database db) {
        this.threadDAO = new ThreadDAOImpl(db, new ThreadMapper());
        this.threadValidator = new ThreadValidator();
    }

    /**
     * Crea un nuovo thread con i dati forniti e lo salva nel database.
     * Il thread viene validato prima di essere salvato.
     *
     * @param userId L'ID dell'utente che sta creando il thread.
     * @param title Il titolo del nuovo thread.
     * @param body Il corpo del nuovo thread.
     * @param votes Il numero iniziale di voti del thread.
     * @param archived Indica se il thread è archiviato
     *                 al momento della creazione.
     * @param category La categoria del nuovo thread.
     *
     * @throws ServiceException Se il thread non è valido o se si verifica un
     *                          errore durante il salvataggio nel database.
     */
    @Override
    public void createThread(
            final long userId,
            final String title,
            final String body,
            final int votes,
            final boolean archived,
            final Category category
    ) throws ServiceException {

        //Inizializzazione oggetto Thread
        Thread newThread = new Thread();
        newThread.setUserId(userId);
        newThread.setTitle(title);
        newThread.setBody(body);
        newThread.setVotes(votes);
        newThread.setArchived(archived);
        newThread.setCategory(category);
        newThread.setCreationDate(LocalDate.now());

        //Validazione Thread
        if (!threadValidator.validate(newThread)) {
            throw new ServiceException("Thread non valido.");
        }

        //Salvataggio Thread
        try {
            threadDAO.save(newThread);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore durante il salvataggio del thread", e);
        }
    }

    /**
     * Rimuove un thread esistente.
     *
     * @param id L'ID del thread da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void removeThread(final long id) throws ServiceException {
            try {
                if (id <= 0) {
                    throw new IllegalArgumentException(
                            "Id deve essere maggiore di 0");
                }
                threadDAO.delete(id);
            } catch (DAOException e) {
                throw new ServiceException(
                        "Errore durante l'eliminazione del thread", e);
            }
        }


    /**
     * Aggiorna un thread esistente con i nuovi dati forniti.
     * Il thread viene validato prima di essere salvato nel database.
     *
     *
     *
     * @param id L'ID del thread da aggiornare.
     * @param userId L'ID dell'utente che ha effettuato l'aggiornamento.
     * @param title Il nuovo titolo del thread.
     * @param body Il nuovo corpo del thread.
     * @param votes Il nuovo numero di voti del thread.
     * @param archived Indica se il thread deve essere archiviato.
     * @param category La nuova categoria del thread.
     *
     * @throws ServiceException Se il thread non è valido o se si verifica
     *                          un errore durante il salvataggio nel database.
     */
    @Override
    public void updateThread(
            final long id,
            final long userId,
            final String title,
            final String body,
            final int votes,
            final boolean archived,
            final Category category
    ) throws ServiceException {
        //Inizializzazione oggetto Thread
        Thread updatedThread = new Thread();
        updatedThread.setId(id);
        updatedThread.setUserId(userId);
        updatedThread.setTitle(title);
        updatedThread.setBody(body);
        updatedThread.setVotes(votes);
        updatedThread.setArchived(archived);
        updatedThread.setCategory(category);

        //Validazione Thread
        if (!threadValidator.validate(updatedThread)) {
            throw new ServiceException("Thread non valido.");
        }

        //Salvataggio Thread
        try {
            threadDAO.update(updatedThread);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore durante il salvataggio del thread", e);
        }
    }

    /**
     * Restituisce un thread dato il suo ID.
     *
     * @param id l'ID del thread da recuperare.
     * @return il thread con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public Thread findThreadById(final long id) throws ServiceException {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException(
                        "Id deve essere maggiore di 0");
            }

            return threadDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore durante il recupero del thread " + id + ".", e);
        }
    }

    /**
     * Esegue una ricerca di thread con opzioni di paginazione.
     * La ricerca può essere eseguita per titolo, categoria o entrambi,
     * a seconda dei parametri forniti.
     *
     * @param title    Il titolo del thread da cercare, {@code null}
     *                 se si vuole cercare solo con la categoria.
     * @param category La categoria dei thread da cercare, {@code null}
     *                 se si vuole cercare solo con il titolo.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di thread per pagina.
     * @param order Ordinamento della lista (più recenti,
     *              più vecchi, più votati).
     * @return Una lista di thread che corrispondono ai criteri di ricerca.
     * @throws IllegalArgumentException se il <code>title</code>
     * è <code>null</code>, <code>page</code>
     * o <code>pageSize</code> sono minori o uguali a 0
     * @throws ServiceException se si è verificato un errore.
     */
    public List<Thread> findThreads(
            final String title,
            final Category category,
            final int page,
            final int pageSize,
            final Order order
    ) throws ServiceException {

        //Sanificazione TODO da spostare
        int realPage = Math.max(page, 1);

        //Definizione decisioni
        boolean isCategoryNull = category == null;
        boolean isTitleEmpty = title == null || title.trim().isEmpty();

        //Ricerca per generica
        if (isCategoryNull && isTitleEmpty) {
            try {
                return threadDAO.getThreadsByTitle(
                        "",
                        realPage,
                        pageSize,
                        order
                );
            } catch (DAOException e) {
                throw new ServiceException(
                        "Errore durante la ricerca generica", e);
            }
        }

        //Ricerca per titolo
        if (isCategoryNull) {
            try {
                return threadDAO.getThreadsByTitle(
                        title,
                        realPage,
                        pageSize,
                        order
                );
            } catch (DAOException e) {
                throw new ServiceException(
                        "Errore durante la ricerca per titolo", e);
            }
        }

        //Ricerca per categoria
        if (isTitleEmpty) {
            try {
                return threadDAO.getThreadsByCategory(
                        category,
                        realPage,
                        pageSize,
                        order
                );
            } catch (DAOException e) {
                throw new ServiceException(
                        "Errore durante la ricerca per categoria", e);
            }
        }

        //Ricerca per titolo e categoria
        try {
            return threadDAO.getThreadsByTitle(
                    title,
                    category,
                    realPage,
                    pageSize,
                    order
            );
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore durante la ricerca per titolo e categoria", e);
        }
    }
}

package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

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
     * Costruttore.
     *
     * @param db il database utilizzato per la persistenza dei dati.
     */
    public ThreadServiceImpl(final Database db) {
        this.threadDAO = new ThreadDAOImpl(db, new ThreadMapper());
    }

    /**
     * Aggiunge un nuovo thread.
     *
     * @param thread Il thread da aggiungere.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void addThread(final Thread thread) throws ServiceException {

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

    }

    /**
     * Aggiorna un thread esistente.
     *
     * @param thread Il thread da aggiornare.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public void updateThread(final Thread thread) throws ServiceException {

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
                throw new IllegalArgumentException("ID thread non valido");
            }
            return threadDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore recupero thread tramite ID.", e);
        }
    }

    /**
     * Recupera i thread in base al titolo e alla categoria, con supporto per la
     * paginazione.
     *
     * @param title    Il titolo del thread da cercare.
     * @param category La categoria dei thread da cercare.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di thread per pagina.
     * @return Una lista di thread che corrispondono ai criteri di ricerca.
     * @throws IllegalArgumentException se il <code>title</code>
     * è <code>null</code>, <code>page</code>
     * o <code>pageSize</code> sono minori o uguali a 0
     * @throws ServiceException se si è verificato un errore.
     */
    public List<Thread> findThreadsByTitle(
            final String title,
            final Category category,
            final int page,
            final int pageSize
    ) throws ServiceException {
        if (title == null) {
            throw new IllegalArgumentException("Titolo non può essere nullo!");
        }

        return List.of(); // todo Implementazione da completare
    }

    /**
     * Restituisce tutti i thread presenti nel database.
     *
     * @return una lista di tutti i thread.
     * @throws ServiceException se si è verificato un errore.
     */
    @Override
    public List<Thread> findAllThreads() throws ServiceException {
        try {
            return threadDAO.getAll();
        } catch (DAOException e) {
            throw new ServiceException(
                    "Errore recupero thread.", e);
        }
    }
}

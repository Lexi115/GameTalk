package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.dao.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.thread.ThreadDAOImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.thread.ThreadMapper;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Thread}.
 * Fornisce metodi per recuperare e cercare thread.
 */
public class ThreadService {

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
    public ThreadService(final Database db) {
        this.threadDAO = new ThreadDAOImpl(db, new ThreadMapper());
    }

    /**
     * Restituisce un thread dato il suo ID.
     *
     * @param id l'ID del thread da recuperare.
     * @return il thread con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws RuntimeException se si verifica un errore nell'accesso
     * al database.
     */
    public Thread getThreadById(final long id) {
        try {
            if (id <= 0) {
                throw new IllegalArgumentException("Id thread non valido");
            }
            return threadDAO.get(id);
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restituisce tutti i thread presenti nel database.
     *
     * @return una lista di tutti i thread.
     * @throws RuntimeException se si verifica un errore nell'accesso
     * al database.
     */
    public List<Thread> getAllThreads() {
        try {
            return threadDAO.getAll();
        } catch (DAOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Restituisce tutti i thread con un titolo che corrisponde al parametro.
     *
     * @param title il titolo da cercare.
     * @return una lista di thread con il titolo specificato.
     * @throws IllegalArgumentException se il titolo è vuoto.
     */
    public List<Thread> getThreadsByTitle(final String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titolo non valido");
        }

        return List.of(); // Implementazione da completare
    }
}

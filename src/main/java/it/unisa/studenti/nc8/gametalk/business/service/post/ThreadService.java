package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.util.List;

/**
 * Interfaccia di servizio per la gestione di oggetti {@link Thread}.
 * Fornisce metodi per creare, rimuovere, aggiornare e cercare threads.
 */
public interface ThreadService {

    /**
     * Aggiunge un nuovo thread.
     *
     * @param thread Il thread da aggiungere.
     * @throws ServiceException se si è verificato un errore.
     */
    void addThread(Thread thread) throws ServiceException;

    /**
     * Rimuove un thread esistente.
     *
     * @param id L'ID del thread da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    void removeThread(long id) throws ServiceException;

    /**
     * Aggiorna un thread esistente.
     *
     * @param thread Il thread da aggiornare.
     * @throws ServiceException se si è verificato un errore.
     */
    void updateThread(Thread thread) throws ServiceException;

    /**
     * Restituisce un thread dato il suo ID.
     *
     * @param id l'ID del thread da recuperare.
     * @return il thread con l'ID specificato.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    Thread findThreadById(long id) throws ServiceException;

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
    List<Thread> findThreadsByTitle(
            String title,
            Category category,
            int page,
            int pageSize
    ) throws ServiceException;

    /**
     * Restituisce tutti i thread presenti nel sistema di persistenza.
     *
     * @return una lista di tutti i thread.
     * @throws ServiceException se si è verificato un errore.
     */
     List<Thread> findAllThreads() throws ServiceException;
}

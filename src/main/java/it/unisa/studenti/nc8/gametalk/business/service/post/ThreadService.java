package it.unisa.studenti.nc8.gametalk.business.service.post;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;
import java.util.List;

/**
 * Interfaccia di servizio per la gestione di oggetti {@link Thread}.
 * Fornisce metodi per creare, rimuovere, aggiornare e cercare threads.
 */
public interface ThreadService {

    /**
     * Crea un nuovo thread con i dati forniti e lo salva nel database.
     * Il thread viene validato prima di essere salvato.
     *
     * @param username L'ID dell'utente che sta creando il thread.
     * @param title Il titolo del nuovo thread.
     * @param body Il corpo del nuovo thread.
     * @param category La categoria del nuovo thread.
     *
     * @throws ServiceException Se il thread non è valido o se si
     *                          verifica un errore durante
     *                          il salvataggio nel database.
     */
    void createThread(
            String username,
            String title,
            String body,
            Category category
    ) throws ServiceException;

    /**
     * Rimuove un thread esistente.
     *
     * @param id L'ID del thread da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException se si è verificato un errore.
     */
    void removeThread(long id) throws ServiceException;

    /**
     * Aggiorna un thread esistente con i nuovi dati forniti.
     * Il thread viene validato prima di essere salvato nel database.
     *
     * @param id L'ID del thread da aggiornare.
     * @param username L'ID dell'utente che ha effettuato l'aggiornamento.
     * @param title Il nuovo titolo del thread.
     * @param body Il nuovo corpo del thread.
     * @param category La nuova categoria del thread.
     *
     * @throws ServiceException Se il thread non è valido o se
     *                          si verifica un errore durante
     *                          il salvataggio nel database.
     */
    void updateThread(
            long id,
            String username,
            String title,
            String body,
            Category category
    ) throws ServiceException;

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
     * Esegue una ricerca di thread con opzioni di paginazione.
     * La ricerca può essere eseguita per titolo, categoria o
     * entrambi, a seconda dei parametri forniti.
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
    List<Thread> findThreads(
            String title,
            Category category,
            int page,
            int pageSize,
            Order order
    ) throws ServiceException;
}

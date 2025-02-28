package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * @return l'id del Thread appena creato
     * @throws ServiceException Se il thread non è valido o se si
     *                          verifica un errore durante
     *                          il salvataggio nel database.
     */
    long createThread(
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
     * @throws ServiceException Se si verifica
     * un errore durante il salvataggio nel database.
     * @throws IllegalArgumentException Se il thread non è valido.
     */
    void updateThread(
            long id,
            String username,
            String title,
            String body,
            Category category
    ) throws ServiceException, IllegalArgumentException;

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
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate La data di fine da cui cercare thread, può
     *                essere {@code null}
     * @return Una lista di thread che corrispondono ai criteri di ricerca.
     * @throws IllegalArgumentException se <code>page</code>
     * o <code>pageSize</code> sono minori o uguali a 0
     * @throws ServiceException se si è verificato un errore.
     */
    List<Thread> findThreads(
            String title,
            Category category,
            int page,
            int pageSize,
            Order order,
            LocalDate startDate,
            LocalDate endDate
    ) throws ServiceException;

    /**
     * Recupera i thread pubblicati da un utente, con opzioni di paginazione.
     *
     * @param username Il nome dell'utente.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di thread per pagina.
     * @param order Ordinamento della lista (più recenti,
     *              più vecchi, più votati).
     * @return Una lista di thread pubblicati dall'utente.
     * @throws IllegalArgumentException se lo <code>username</code>
     * è <code>null</code>, <code>page</code>
     * o <code>pageSize</code> sono minori o uguali a 0
     * @throws ServiceException se si è verificato un errore.
     */
    List<Thread> findThreadsByUsername(
            String username,
            int page,
            int pageSize,
            Order order
    ) throws ServiceException;

    /**
     * Permette a un utente di votare un thread, con la possibilità di rimuovere
     * un voto esistente (impostando il voto a 0). In caso di voto invalido, o
     * se il thread non esiste, viene sollevata un'eccezione.
     *
     * @param threadId ID del thread da votare.
     * @param username Nome utente dell'utente che sta effettuando il voto.
     * @param vote Valore del voto da assegnare al thread, deve essere:
     *             <ul>
     *             <li>-1: Downvote.</li>
     *             <li>0: Rimozione del voto esistente (se presente).</li>
     *             <li>1: Upvote.</li>
     *             </ul>
     *
     * @throws ServiceException Se si verifica un errore durante l'elaborazione
     * del voto, come:
     * <ul>
     * <li>Il thread con l'ID specificato non esiste.</li>
     * <li>Errore durante l'aggiunta del voto.</li>
     * </ul>
     * @throws IllegalArgumentException Se il valore del voto non è valido
     * (diverso da -1, 0, 1).
     *
     */
    void rateThread(
            long threadId,
            String username,
            int vote
    ) throws ServiceException;

    /**
     * Sposta un thread in una nuova categoria.
     *
     * @param threadId L'ID del thread da spostare.
     * @param category La nuova categoria in cui spostare il thread.
     *                 Non può essere {@code null}.
     * @throws IllegalArgumentException Se la categoria fornita è {@code null}.
     * @throws ServiceException Se si verifica un errore durante l'operazione.
     */
    void updateThreadCategory(
            long threadId,
            Category category
    ) throws ServiceException;

    /**
     * Archivia un thread.
     *
     * @param threadId L'ID del thread da archiviare.
     * @throws ServiceException Se si verifica un errore durante l'operazione.
     */
    void archiveThread(long threadId) throws ServiceException;

    /**
     * Recupera il numero di risultati appartenenti a una ricerca.
     *
     * @param title Il titolo del thread.
     * @param category La categoria.
     * @param startDate La data di inizio della ricerca.
     * @param endDate La data di fine di una ricerca.
     * @return Il numero di risultati
     * @throws ServiceException In caso di errore durante il
     * recupero del conteggio.
     */
    int getThreadCount(
            String title,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) throws ServiceException;

    /**
     * Recupera il voto che un utente ha dato a un thread.
     *
     * @param threadId L'ID del thread di cui recuperare il voto.
     * @param username Il nome utente dell'utente per cui recuperare il voto.
     * @return Un intero che indica la valutazione.
     * @throws ServiceException Se si verifica un errore durante il recupero del voto
     * dal database.
     */
    int getPersonalVote(
            long threadId,
            String username
    ) throws ServiceException;
}



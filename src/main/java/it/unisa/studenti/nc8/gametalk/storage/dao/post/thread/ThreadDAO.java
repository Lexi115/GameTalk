package it.unisa.studenti.nc8.gametalk.storage.dao.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.utils.Bindable;

import java.time.LocalDate;
import java.util.List;

/**
 * Interfaccia DAO per l'entità {@link Thread}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione
 * con il sistema di persistenza per le operazioni CRUD relative
 * all'entità {@link Thread}.
 */
public interface ThreadDAO extends DAO<Thread, Long>, Bindable {

    /**
     * Ottiene una lista di thread che corrispondono al titolo
     * immesso o parte di esso.
     *
     * @param title     Titolo o parte di esso da cercare.
     * @param page      Numero della pagina.
     * @param limit     Numero di Thread massimi per pagina.
     * @param order     Ordinamento della lista (più votati,
     *                  più recenti, più vecchi).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate   La data di fine da cui cercare thread, può
     *                  essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByTitle(
            String title,
            int page,
            int limit,
            Order order,
            LocalDate startDate,
            LocalDate endDate
    ) throws DAOException;

    /**
     * Ottiene una lista di thread corrispondenti al titolo e alla categoria.
     *
     * @param title     Titolo o parte di esso da cercare.
     * @param category  Categoria del thread.
     * @param page      Numero della pagina.
     * @param limit     Numero di thread massimi per pagina.
     * @param order     Ordinamento della lista (più votati,
     *                  più recenti, più vecchi).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate   La data di fine da cui cercare thread, può
     *                  essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByTitle(
            String title,
            Category category,
            int page,
            int limit,
            Order order,
            LocalDate startDate,
            LocalDate endDate
    ) throws DAOException;

    /**
     * Ottiene una lista di thread filtrati per categoria.
     *
     * @param category  Categoria del thread.
     * @param page      Numero della pagina (paginazione).
     * @param limit     Numero di Thread massimi per pagina.
     * @param order     Ordinamento della lista (più recenti,
     *                  più vecchi, più votati).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate   La data di fine da cui cercare thread, può
     *                  essere {@code null}
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByCategory(
            Category category,
            int page,
            int limit,
            Order order,
            LocalDate startDate,
            LocalDate endDate
    ) throws DAOException;

    /**
     * Ottiene una lista di thread pubblicati da un utente specifico.
     *
     * @param username Il nome dell'utente.
     * @param page     Numero della pagina (paginazione).
     * @param limit    Numero di Thread massimi per pagina.
     * @param order    Ordinamento della lista (più recenti,
     *                 più vecchi, più votati).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByUsername(
            String username,
            int page,
            int limit,
            Order order
    ) throws DAOException;

    /**
     * Vota un thread associato al suo ID.
     * Se l'utente ha già votato il thread, il voto viene aggiornato con
     * il nuovo valore.
     *
     * @param threadId ID del thread da votare.
     * @param username Nome dell'utente che sta effettuando il voto.
     * @param vote     Valore del voto da assegnare al thread. Deve essere:
     *                 <ul>
     *                 <li>-1: Downvote.</li>
     *                 <li>0: Voto neutro o rimozione del voto (se presente).</li>
     *                 <li>1: Upovote.</li>
     *                 </ul>
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     *                      del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     *                      nel database.
     */
    void voteThread(
            long threadId,
            String username,
            int vote
    ) throws DAOException;

    /**
     * Rimuove il voto di un thread associato al suo ID se questo esiste.
     *
     * @param threadId ID del thread di cui rimuovere il voto.
     * @param username Nome dell'utente che ha espresso il voto da rimuovere.
     * @throws DAOException Se si verifica un errore durante l'elaborazione
     *                      del voto, ad esempio un errore durante l'inserimento o l'aggiornamento
     *                      nel database.
     */
    void removeVoteThread(
            long threadId,
            String username
    ) throws DAOException;

    /**
     * Conta il numero di thread risultanti da una ricerca.
     *
     * @param title Il titolo (o parte di esso) da cercare.
     * @param category La categoria del thread.
     * @param startDate La data di inizio dell'intervallo di ricerca.
     * @param endDate La data di fine dell'intervallo di ricerca.
     * @return Il numero di thread che corrispondono ai criteri di ricerca.
     * @throws DAOException Se si verifica un errore nel
     * conteggio dei thread dal database.
     */
    int getThreadCount(
            String title,
            Category category,
            LocalDate startDate,
            LocalDate endDate
    ) throws DAOException;

    /**
     * Recupera il voto che un utente ha dato a un thread.
     *
     * @param threadId L'ID del thread di cui recuperare il voto.
     * @param username Il nome utente dell'utente per cui recuperare il voto.
     * @return Un intero che indica la valutazione.
     * @throws DAOException Se si verifica un errore durante il recupero del voto
     * dal database.
     */
    int getPersonalVote(
            long threadId,
            String username
    ) throws DAOException;
}

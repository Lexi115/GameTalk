package it.unisa.studenti.nc8.gametalk.business.services.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.dao.post.thread.ThreadDAO;
import it.unisa.studenti.nc8.gametalk.storage.dao.user.UserDAO;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Transaction;
import it.unisa.studenti.nc8.gametalk.storage.persistence.TransactionImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Classe di servizio per la gestione di oggetti {@link Thread}.
 * Fornisce metodi per creare, rimuovere, aggiornare e cercare threads.
 */
public class ThreadServiceImpl implements ThreadService {

    /**
     * Oggetto {@link Database}, contiene informazioni sul database
     * con cui interagire.
     */
    private final Database db;

    /**
     * Il DAO per interagire con i threads sul
     * sistema di persistenza.
     */
    private final ThreadDAO threadDAO;

    /**
     * Il DAO per interagire con gli utenti sul
     * sistema di persistenza.
     */
    private final UserDAO userDAO;

    /**
     * Il validator che valida i dati contenuti in
     * un oggetto {@link Thread}.
     */
    private final Validator<Thread> threadValidator;

    /**
     * Costruttore.
     *
     * @param db                il database utilizzato per la persistenza
     *                          dei dati.
     * @param threadDAO         il DAO per gestire i thread sul sistema di
     *                          persistenza.
     * @param userDAO           il DAO per gestire gli utenti sul sistema di
     *                          persistenza.
     * @param threadValidator   il validator di thread.
     */
    public ThreadServiceImpl(
            final Database db,
            final ThreadDAO threadDAO,
            final UserDAO userDAO,
            final Validator<Thread> threadValidator
    ) {
        this.db = db;
        this.threadDAO = threadDAO;
        this.userDAO = userDAO;
        this.threadValidator = threadValidator;
    }

    /**
     * Crea un nuovo thread con i dati forniti e lo salva nel database.
     * Il thread viene validato prima di essere salvato.
     *
     * @param username L'ID dell'utente che sta creando il thread.
     * @param title    Il titolo del nuovo thread.
     * @param body     Il corpo del nuovo thread.
     * @param category La categoria del nuovo thread.
     * @throws ServiceException Se il thread non è valido o se si verifica un
     *                          errore durante il salvataggio nel database.
     */
    @Override
    public long createThread(
            final String username,
            final String title,
            final String body,
            final Category category
    ) throws ServiceException {

        //Inizializzazione oggetto Thread
        Thread newThread = new Thread();
        newThread.setUsername(username);
        newThread.setTitle(title);
        newThread.setBody(body);
        newThread.setVotes(0);
        newThread.setArchived(false);
        newThread.setCategory(category);
        newThread.setCreationDate(LocalDate.now());

        //Validazione Thread
        threadValidator.validate(newThread);

        //Salvataggio Thread
        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);
            return threadDAO.save(newThread);
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante il salvataggio del thread", e);
        }
    }

    /**
     * Rimuove un thread esistente.
     *
     * @param id L'ID del thread da rimuovere.
     * @throws IllegalArgumentException se l'ID è minore o uguale a 0.
     * @throws ServiceException         se si è verificato un errore.
     */
    @Override
    public void removeThread(final long id) throws ServiceException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Id deve essere maggiore di 0");
        }

        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);
            threadDAO.delete(id);
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante l'eliminazione del thread", e);
        }
    }


    /**
     * Aggiorna un thread esistente con i nuovi dati forniti.
     * Il thread viene validato prima di essere salvato nel database.
     *
     * @param id       L'ID del thread da aggiornare.
     * @param username L'ID dell'utente che ha effettuato l'aggiornamento.
     * @param title    Il nuovo titolo del thread.
     * @param body     Il nuovo corpo del thread.
     * @param category La nuova categoria del thread.
     * @throws ServiceException Se si verifica
     *                          un errore durante il salvataggio nel database.
     * @throws IllegalArgumentException Se il thread non è valido.
     */
    @Override
    public void updateThread(
            final long id,
            final String username,
            final String title,
            final String body,
            final Category category
    ) throws ServiceException, IllegalArgumentException {
        //Operazioni di aggiornamento
        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                //Verifico se esiste un thread con quell'id e lo recupero
                Thread updatedThread = threadDAO.get(id);
                if (updatedThread == null) {
                    throw new IllegalArgumentException(
                            "Nessun thread trovato con id " + id);
                }

                if (updatedThread.isArchived()) {
                    throw new IllegalArgumentException("Thread è archiviato");
                }

                //Aggiorno i campi
                updatedThread.setTitle(title);
                updatedThread.setBody(body);
                updatedThread.setCategory(category);

                //Valido i campi aggiornati
                threadValidator.validate(updatedThread);

                //Aggiorno il thread sul database
                threadDAO.update(updatedThread);
                tx.commit();
            }
        } catch (SQLException | DAOException e) {
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
     * @throws ServiceException         se si è verificato un errore.
     */
    @Override
    public Thread findThreadById(final long id) throws ServiceException {
        if (id <= 0) {
            throw new IllegalArgumentException(
                    "Id deve essere maggiore di 0");
        }

        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);
            return threadDAO.get(id);
        } catch (SQLException | DAOException e) {
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
     * @param order    Ordinamento della lista (più recenti,
     *                 più vecchi, più votati).
     * @param startDate La data di inizio da cui cercare thread, può
     *                  essere {@code null}
     * @param endDate La data di fine da cui cercare thread, può
     *                essere {@code null}
     * @return Una lista di thread che corrispondono ai criteri di ricerca.
     * @throws ServiceException se si è verificato un errore.
     */
    public List<Thread> findThreads(
            final String title,
            final Category category,
            final int page,
            final int pageSize,
            final Order order,
            final LocalDate startDate,
            final LocalDate endDate
    ) throws ServiceException {
        int realPage = Math.max(page, 1);

        //Definizione decisioni
        boolean isCategoryNull = category == null;
        boolean isTitleEmpty = title == null || title.isBlank();

        LocalDate actualStartDate = LocalDate.of(1000, 1, 1);
        LocalDate actualEndDate = LocalDate.of(9999, 12, 31);

        //Controllo data inizio
        if (startDate != null && startDate.isAfter(actualStartDate)) {
            actualStartDate = startDate;
        }

        if (endDate != null && endDate.isBefore(actualEndDate)) {
            actualEndDate = endDate;
        }

        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);

            // Ricerca generica
            if (isCategoryNull && isTitleEmpty) {
                return threadDAO.getThreadsByTitle(
                        "",
                        realPage,
                        pageSize,
                        order,
                        actualStartDate,
                        actualEndDate
                );
            }

            // Ricerca per titolo
            if (isCategoryNull) {
                return threadDAO.getThreadsByTitle(
                        title,
                        realPage,
                        pageSize,
                        order,
                        actualStartDate,
                        actualEndDate
                );
            }

            // Ricerca per categoria
            if (isTitleEmpty) {
                return threadDAO.getThreadsByCategory(
                        category,
                        realPage,
                        pageSize,
                        order,
                        actualStartDate,
                        actualEndDate
                );
            }

            // Ricerca per titolo e categoria
            return threadDAO.getThreadsByTitle(
                    title,
                    category,
                    realPage,
                    pageSize,
                    order,
                    actualStartDate,
                    actualEndDate
            );
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante la ricerca thread", e);
        }
    }

    /**
     * Recupera i thread pubblicati da un utente, con opzioni di paginazione.
     *
     * @param username Il nome dell'utente.
     * @param page     Il numero della pagina da recuperare.
     * @param pageSize Il numero di thread per pagina.
     * @param order    Ordinamento della lista (più recenti,
     *                 più vecchi, più votati).
     * @return Una lista di thread pubblicati dall'utente.
     * @throws IllegalArgumentException se lo <code>username</code>
     *                                  è <code>null</code> o è vuoto.
     * @throws ServiceException         se si è verificato un errore.
     */
    @Override
    public List<Thread> findThreadsByUsername(
            final String username,
            final int page,
            final int pageSize,
            final Order order
    ) throws ServiceException {
        int realPage = Math.max(page, 1);

        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username non valido.");
        }

        //Username valido, recupero i thread.
        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);
            return threadDAO.getThreadsByUsername(
                    username, realPage, pageSize, order);
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante la ricerca per titolo", e);
        }
    }

    /**
     * Permette a un utente di votare un thread, con la possibilità di rimuovere
     * un voto esistente (impostando il voto a 0). In caso di voto invalido, o
     * se il thread non esiste, viene sollevata un'eccezione.
     *
     * @param threadId ID del thread da votare.
     * @param username Nome utente dell'utente che sta effettuando il voto.
     * @param vote     Valore del voto da assegnare al thread, deve essere:
     *                 <ul>
     *                 <li>-1: Downvote.</li>
     *                 <li>0: Rimozione del voto esistente (se presente).</li>
     *                 <li>1: Upvote.</li>
     *                 </ul>
     * @throws ServiceException Se si verifica un errore durante l'elaborazione
     *                          del voto.
     * @throws IllegalArgumentException Se il valore del voto non è valido
     *                                  (diverso da -1, 0, 1).
     */
    public void rateThread(
            final long threadId,
            final String username,
            final int vote
    ) throws ServiceException {
        //Sanificazione
        if (vote < -1 || vote > 1) {
            throw new IllegalArgumentException("Voto non valido");
        }

        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);
            userDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                //Verifico l'esistenza del thread
                Thread thread = threadDAO.get(threadId);
                if (thread == null) {
                    throw new ServiceException(
                            "Nessun thread trovato con id " + threadId);
                }

                if (thread.isArchived()) {
                    throw new ServiceException("Thread è archiviato");
                }

                //Verifico l'esistenza dell'utente
                User user = userDAO.get(username);
                if (user == null) {
                    throw new ServiceException(
                            "Nessun utente trovato con username " + username);
                }

                //Thread esiste, lo voto
                if (vote == 0) {
                    //Rimuovo il voto
                    threadDAO.removeVoteThread(threadId, username);
                } else {
                    //Inserisco il voto
                    threadDAO.voteThread(threadId, username, vote);
                }

                tx.commit();
            }
        } catch (SQLException | DAOException e) {
            throw new ServiceException(
                    "Errore durante l'aggiunta del voto " + vote
                            + " al thread " + threadId, e);
        }
    }


    /**
     * Sposta un thread in una nuova categoria.
     *
     * @param threadId L'ID del thread da spostare.
     * @param category La nuova categoria in cui spostare il thread.
     *                 Non può essere {@code null}.
     * @throws IllegalArgumentException Se la categoria fornita è {@code null}.
     * @throws ServiceException Se si verifica un errore durante l'operazione.
     */
    @Override
    public void updateThreadCategory(
            final long threadId,
            final Category category
    ) throws ServiceException {
        //Controllo categoria null
        if (category == null) {
            throw new IllegalArgumentException("Category non può essere null.");
        }

        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                //Recupero thread da modificare
                Thread thread = threadDAO.get(threadId);
                if (thread == null) {
                    throw new ServiceException(
                            "Nessun thread trovato con id " + threadId);
                }

                if (thread.isArchived()) {
                    throw new ServiceException("Thread è archiviato");
                }

                //Thread esistente, aggiorno la categoria
                thread.setCategory(category);
                threadDAO.update(thread);
                tx.commit();
            }
        } catch (DAOException | SQLException e) {
            throw new ServiceException(
                    "Errore durante lo spostamento del thread "
                            + threadId + " nella categoria " + category, e);
        }
    }

    /**
     * Archivia un thread.
     *
     * @param threadId L'ID del thread da archiviare.
     * @throws ServiceException Se si verifica un errore durante l'operazione.
     */
    @Override
    public void archiveThread(final long threadId) throws ServiceException {
        try (Connection connection = db.connect()) {
            threadDAO.bind(connection);

            try (Transaction tx = new TransactionImpl(connection)) {
                //Recupero thread da modificare
                Thread thread = threadDAO.get(threadId);
                if (thread == null) {
                    throw new ServiceException(
                            "Nessun thread trovato con id " + threadId);
                }

                //Archivio il thread
                thread.setArchived(true);
                threadDAO.update(thread);
                tx.commit();
            }
        } catch (DAOException | SQLException e) {
            throw new ServiceException(
                    "Errore durante l'archiviazione del thread", e);
        }
    }
}

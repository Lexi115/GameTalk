package it.unisa.studenti.nc8.gametalk.storage.dao;

import it.unisa.studenti.nc8.gametalk.storage.exceptions.DAOException;

import java.util.List;

/**
 * Interfaccia generica che definisce le operazioni di base per accedere
 * e gestire entità. Questa interfaccia è parametrizzata
 * con il tipo generico {@code T}.
 *
 * @param <T> il tipo dell'entità gestita dal DAO.
 */
public interface DAO<T> {

    /**
     * Recupera un'entità dal sistema di persistenza in base al suo
     * identificativo univoco.
     *
     * @param id l'identificativo univoco dell'entità da recuperare.
     * @return l'entità corrispondente all'ID fornito, o {@code null}
     * se non trovata.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    T get(long id) throws DAOException;

    /**
     * Recupera tutte le entità del tipo specificato dal sistema di persistenza.
     *
     * @return una lista contenente tutte le entità, oppure una lista vuota se
     * nessuna entità è stata trovata.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    List<T> getAll() throws DAOException;

    /**
     * Salva una nuova entità nel sistema di persistenza.
     *
     * @param entity l'entità da salvare.
     * @return l'ID della nuova riga aggiunta, o 0 se non è stata
     * aggiunta alcuna riga.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    long save(T entity) throws DAOException;

    /**
     * Aggiorna i dati di un'entità esistente nel sistema di persistenza.
     *
     * @param entity l'entità da aggiornare.
     * @return <code>true</code> se la riga è stata aggiornata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    boolean update(T entity) throws DAOException;

    /**
     * Elimina un'entità dal sistema di persistenza in base al suo
     * identificativo univoco.
     *
     * @param id l'identificativo univoco dell'entità da eliminare.
     * @return <code>true</code> se la riga è stata eliminata,
     * <code>false</code> altrimenti.
     * @throws DAOException se si verifica un errore durante l'interazione con
     * il sistema di persistenza.
     */
    boolean delete(long id) throws DAOException;
}

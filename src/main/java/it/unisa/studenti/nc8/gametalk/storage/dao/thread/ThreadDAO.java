package it.unisa.studenti.nc8.gametalk.storage.dao.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.enums.Order;
import it.unisa.studenti.nc8.gametalk.storage.dao.DAO;
import it.unisa.studenti.nc8.gametalk.business.exceptions.DAOException;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;

import java.util.List;


/**
 * Definizione dell'interfaccia DAO per l'entità {@link Thread}.
 * Questa interfaccia definisce metodi aggiuntivi per l'interazione con il database
 * per le operazioni CRUD relative all'entità {@link Thread}.
 * <p>
 * Estende {@link DAO<Thread>}.
 *
 * @version 1.0
 */
public interface ThreadDAO extends DAO<Thread> {

    /**
     * Ottiene una lista di thread che corrispondono al titolo dato o parte di esso.
     *
     * @param title Titolo o parte di esso da cercare.
     * @param page  Numero della pagina.
     * @param limit Numero di Thread massimi per pagina.
     * @param order Ordine per la lista (più votati, più recenti, più vecchi).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByTitle(String title , int page, int limit, Order order) throws DAOException;

    /**
     * Ottiene una lista di thread corrispondenti al titolo e alla categoria.
     *
     * @param title    Titolo o parte di esso da cercare.
     * @param category Categoria del thread.
     * @param page  Numero della pagina.
     * @param limit Numero di Thread massimi per pagina.
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByTitle(String title ,Category category ,int page, int limit,Order order) throws DAOException;

    /**
     * Ottiene una lista di thread filtrati per categoria.
     *
     * @param category Categoria del thread.
     * @param page     Numero della pagina (paginazione).
     * @param limit Numero di Thread massimi per pagina.
     * @param order    Ordine per la lista (più recenti, più vecchi, più votati).
     * @return Lista di thread corrispondenti.
     * @throws DAOException In caso di errori durante l'esecuzione della query.
     */
    List<Thread> getThreadsByCategory(Category category, int page, int limit, Order order) throws DAOException;

}

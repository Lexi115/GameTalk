package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ThreadServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per recuperare i thread. */
    private ThreadService threadService;

    /**
     * Init.
     */
    @Override
    public void init() {
        Database db = Functions.getContextDatabase(this.getServletContext());
        DAOFactory daoFactory = new DAOFactoryImpl(db);
        ServiceFactory serviceFactory = new ServiceFactoryImpl(db, daoFactory);

        this.threadService = serviceFactory.createThreadService();
    }

    /**
     * Restituisce la classe di servizio thread.
     * @return La classe di servizio thread.
     */
    protected ThreadService getThreadService() {
        return threadService;
    }
}

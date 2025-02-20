package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AuthenticationServlet extends HttpServlet {

    /** Logger. **/
    protected static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per gestire l'autenticazione. */
    private AuthenticationService authenticationService;

    /** La classe di servizio per recuperare gli utenti. */
    private UserService userService;

    /**
     * Init.
     */
    @Override
    public void init() {
        Database db = Functions.getContextDatabase(this.getServletContext());
        DAOFactory daoFactory = new DAOFactoryImpl(db);
        ServiceFactory serviceFactory = new ServiceFactoryImpl(db, daoFactory);

        this.authenticationService = serviceFactory.createAuthenticationService();
        this.userService = serviceFactory.createUserService();
    }

    /**
     * Restituisce la classe di servizio per l'autenticazione.
     * @return La classe di servizio per l'autenticazione.
     */
    protected AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    /**
     * Restituisce la classe di servizio utente.
     * @return La classe di servizio utente.
     */
    protected UserService getUserService() {
        return userService;
    }
}

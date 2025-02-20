package it.unisa.studenti.nc8.gametalk.presentation.servlets;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactoryImpl;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactory;
import it.unisa.studenti.nc8.gametalk.storage.factories.DAOFactoryImpl;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Servlet per gestire la registrazione degli utenti.
 * Riceve username e password.
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {

    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per creare l'utente. */
    private UserService userService;

    /**
     * Init.
     */
    @Override
    public void init() {
        Database db = Functions.getContextDatabase(this.getServletContext());
        DAOFactory daoFactory = new DAOFactoryImpl(db);
        ServiceFactory serviceFactory = new ServiceFactoryImpl(db, daoFactory);

        this.userService = serviceFactory.createUserService();
    }

    /**
     * Gestisce la richiesta GET per visualizzare la pagina di registrazione.
     *
     * @param req  l'oggetto HttpServletRequest contenente i
     *             parametri della richiesta
     * @param resp l'oggetto HttpServletResponse per inviare
     *             la risposta al client
     * @throws IOException se si verifica un errore.
     */
    @Override
    protected void doGet(
            final HttpServletRequest req,
            final HttpServletResponse resp
    ) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("signup.jsp");
        rd.forward(req, resp);
    }

    /**
     * Gestisce la richiesta POST per registrare l'utente.
     *
     * @param req  l'oggetto HttpServletRequest contenente i
     *             parametri della richiesta
     * @param resp l'oggetto HttpServletResponse per inviare
     *             la risposta al client
     * @throws IOException se si verifica un errore.
     */
    @Override
    protected void doPost(
            final HttpServletRequest req,
            final HttpServletResponse resp
    ) throws ServletException, IOException {
        // Parametri di registrazione (username e password)
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            userService.createUser(username, password);

            // Reindirizza sulla pagina di login dopo la creazione dell'utente
            resp.sendRedirect(req.getContextPath() + "/login");

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di registrazione", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            req.setAttribute("error", e.getMessage());
            doGet(req, resp);
        }
    }
}

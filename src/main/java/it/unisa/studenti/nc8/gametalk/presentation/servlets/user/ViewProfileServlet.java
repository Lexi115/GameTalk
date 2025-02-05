package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.service.user.UserService;
import it.unisa.studenti.nc8.gametalk.business.service.user.UserServiceImpl;
import it.unisa.studenti.nc8.gametalk.presentation.exceptions.NotFoundException;
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
 * Servlet per mostrare una pagina di profilo utente.
 */
@WebServlet("/profile")
public class ViewProfileServlet extends HttpServlet {

    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per ricercare l'utente. */
    private UserService userService;

    /**
     * Init.
     */
    @Override
    public void init() {
        this.userService = new UserServiceImpl(
                Functions.getContextDatabase(this.getServletContext()));
    }

    /**
     * Gestisce la richiesta GET per mostrare la pagina profilo.
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
    ) throws IOException, ServletException {
        try {
            // Parametro di ricerca (username)
            String username = req.getParameter("username");
            if (username == null) {
                throw new IllegalArgumentException("Username richiesto");
            }

            User userFound = userService.findUserByUsername(username);
            if (userFound == null) {
                throw new NotFoundException("Utente non trovato");
            }

            req.setAttribute("user", userFound);
            RequestDispatcher rd = req.getRequestDispatcher("viewProfile.jsp");
            rd.forward(req, resp);

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio utenti", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );

        } catch (NotFoundException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}

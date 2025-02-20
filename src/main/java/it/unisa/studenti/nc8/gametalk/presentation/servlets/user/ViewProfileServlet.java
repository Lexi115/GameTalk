package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.NotFoundException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.business.utils.Functions;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet per mostrare una pagina di profilo utente.
 */
@WebServlet("/profile")
public class ViewProfileServlet extends UserServlet {

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
        UserService userService = getUserService();
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

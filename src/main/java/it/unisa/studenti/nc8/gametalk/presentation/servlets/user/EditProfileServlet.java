package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.NotFoundException;
import it.unisa.studenti.nc8.gametalk.presentation.exceptions.PermissionException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet per mostrare una pagina di profilo utente.
 */
@WebServlet("/editProfile")
public class EditProfileServlet extends UserServlet {

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
            // Parametro di ricerca (username).
            String username = req.getParameter("username");

            User requester = (User) req.getSession().getAttribute("user");
            User userFound = getRequestedUser(username, requester);

            req.setAttribute("user", userFound);
            RequestDispatcher rd = req.getRequestDispatcher("editProfile.jsp");
            rd.forward(req, resp);

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio utenti", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );

        } catch (NotFoundException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage()
            );

        } catch (PermissionException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_FORBIDDEN,
                    e.getMessage()
            );
        }
    }

    /**
     * Gestisce la richiesta POST per modificare un profilo.
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
        UserService userService = getUserService();
        try {
            // Parametri di aggiornamento (username e password).
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            User requester = (User) req.getSession().getAttribute("user");
            User userFound = getRequestedUser(username, requester);
            userService.updatePassword(userFound.getUsername(), password);

            // Reindirizza sulla pagina del profilo dopo la modifica
            resp.sendRedirect(
                    req.getContextPath() + "/profile?username=" + username);

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio utenti", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );

        } catch (IllegalArgumentException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );

        } catch (NotFoundException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_NOT_FOUND,
                    e.getMessage()
            );

        } catch (PermissionException e) {
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_FORBIDDEN,
                    e.getMessage()
            );
        }
    }

    private User getRequestedUser(
            final String username,
            final User requester
    ) throws ServiceException, PermissionException, NotFoundException {
        UserService userService = getUserService();
        if (username == null) {
            throw new IllegalArgumentException("Username richiesto");
        }

        User userFound = userService.findUserByUsername(username);
        if (userFound == null) {
            throw new NotFoundException("Utente non trovato");
        }

        // Un utente può solamente modificare il proprio profilo.
        if (!requester.getUsername().equals(userFound.getUsername())) {
            throw new PermissionException("Accesso negato");
        }

        return userFound;
    }
}

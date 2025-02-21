package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.NotFoundException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.business.utils.Functions;
import it.unisa.studenti.nc8.gametalk.presentation.exceptions.PermissionException;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet per bandire un utente dalla piattaforma.
 */
@WebServlet("/mod/banUser")
public class BanUserServlet extends UserServlet {

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

        // Parametro username dell'utente da bandire.
        String username = req.getParameter("username");
        boolean banned = Boolean.parseBoolean(req.getParameter("banned"));

        try {
            User requester = (User) req.getSession().getAttribute("user");
            // Un utente non può bandire se stesso.
            if (requester.getUsername().equalsIgnoreCase(username)) {
                throw new PermissionException(
                        "Accesso negato: non puoi bandire te stesso!");
            }

            userService.banUser(username, banned);

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
}

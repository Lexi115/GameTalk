package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Servlet per visualizzare gli utenti bannati.
 * È possibile cambiare solamente la password.
 */
@WebServlet("/mod/bannedUsers")
public class ViewBannedUsersServlet extends UserServlet {

    /**
     * Gestisce la richiesta GET per mostrare la pagina degli utenti bannati.
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
        RequestDispatcher rd = req.getRequestDispatcher(
                "/WEB-INF/views/mod/bannedUsers.jsp");
        rd.forward(req, resp);
    }
}

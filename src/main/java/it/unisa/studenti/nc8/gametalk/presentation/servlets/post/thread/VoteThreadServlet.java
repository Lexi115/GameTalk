package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.utils.Functions;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet per votare un thread.
 */
@WebServlet("/voteThread")
public class VoteThreadServlet extends ThreadServlet {

    /**
     * Gestisce la richiesta POST per valutare un thread.
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
        ThreadService threadService = getThreadService();
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");
        String usernameReq = user.getUsername();
        String voteString = req.getParameter("vote");
        String threadIdString = req.getParameter("threadId");
        try {

            int vote = Integer.parseInt(voteString);
            long threadId = Long.parseLong(threadIdString);
            threadService.rateThread(threadId, usernameReq, vote);

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di voto thread", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Parametri non validi", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
        }
    }
}

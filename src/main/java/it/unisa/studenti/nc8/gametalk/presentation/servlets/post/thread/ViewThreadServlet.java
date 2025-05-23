package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet per visualizzare la pagina di un thread.
 */
@WebServlet("/thread")
public class ViewThreadServlet extends ThreadServlet {

    /**
     * Gestisce la richiesta GET per visualizzare la pagina di un Thread.
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
        ErrorHandler errorHandler = getErrorHandler();
        ThreadService threadService = getThreadService();
        long idThread = Long.parseLong(req.getParameter("threadId"));
        HttpSession session = req.getSession();
        try {
            Thread recoveredThread = threadService.findThreadById(idThread);

            if (recoveredThread == null) {
                errorHandler.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }

            //Recupero la valutazione personale dell'utente se è loggato.
            if (session != null) {
                User loggedUser = (User) session.getAttribute("user");
                if (loggedUser != null) {
                    int personalVote = threadService.getPersonalVote(
                            idThread, loggedUser.getUsername());
                    req.setAttribute("personalVote", personalVote);
                }
            }

            req.setAttribute("thread", recoveredThread);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di visualizzazione thread", e);
            errorHandler.handleError(req, resp,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("thread.jsp");
        rd.forward(req, resp);
    }
}

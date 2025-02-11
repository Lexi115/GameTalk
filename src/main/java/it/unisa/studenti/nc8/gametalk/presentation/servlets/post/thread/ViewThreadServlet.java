package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.models.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet("/thread")
public class ViewThreadServlet extends HttpServlet {

    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();
    /** La classe di servizio per recuperare il thread. */
    private ThreadService threadService;

    /**
     * Init.
     */
    @Override
    public void init() {
        this.threadService = new ThreadServiceImpl(
                Functions.getContextDatabase(this.getServletContext()));
    }

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

        long idThread = Long.parseLong(req.getParameter("idThread"));

        try {
            Thread recoveredThread = threadService.findThreadById(idThread);

            if (recoveredThread == null) {
                Functions.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            req.setAttribute("thread", recoveredThread);
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di visualizzazione thread", e);
            Functions.handleError(req, resp,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
            return;
        }

        RequestDispatcher rd = req.getRequestDispatcher("thread.jsp");
        rd.forward(req, resp);
    }
}

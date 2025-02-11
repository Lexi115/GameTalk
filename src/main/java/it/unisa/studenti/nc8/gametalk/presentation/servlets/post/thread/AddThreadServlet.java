package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.models.user.User;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
@WebServlet("/addThread")
public class AddThreadServlet extends HttpServlet {

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
     * Gestisce la richiesta GET per visualizzare la pagina di aggiunta Thread.
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
        RequestDispatcher rd = req.getRequestDispatcher("createThread.jsp");
        rd.forward(req, resp);
    }

    /**
     * Gestisce la richiesta POST per aggiungere un thread.
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
        HttpSession session = req.getSession();

        //Recupero usernameOp dalla sessione
        User user = (User) session.getAttribute("user");
        String usernameOp = user.getUsername();

        String title = req.getParameter("title");
        String body = req.getParameter("body");
        String categoryString = req.getParameter("category");

        try {
            Category category = Category.valueOf(categoryString);
            long threadId = threadService.createThread(
                    usernameOp, title, body, category);

            resp.sendRedirect(
                    req.getContextPath() + "/thread?idThread=" + threadId);

        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di creazione thread", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());

        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Parametri non validi", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
        }
    }
}

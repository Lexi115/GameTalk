package it.unisa.studenti.nc8.gametalk.presentation.servlets.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.service.post.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.service.post.ThreadServiceImpl;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/editThread")
public class EditThreadServlet extends HttpServlet {
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
     * Gestisce la richiesta GET per visualizzare la pagina di modifica thread.
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
        HttpSession session = req.getSession();

        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idThreadString = req.getParameter("idThread");
        User user = (User) session.getAttribute("user");
        String usernameReq = user.getUsername();

        //Recupero thread da modificare
        Thread thread;
        try {
            long idThread = Long.parseLong(idThreadString);
            thread = threadService.findThreadById(idThread);

            if (thread == null) {
                Functions.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di modifica thread", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
            return;
        } catch (NumberFormatException e) {
            LOGGER.error("Parametri non validi", e);
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
            return;
        }

        //Thread esiste, controllo privilegi di modifica.
        boolean isOp = thread.getUsername().equals(usernameReq);
        boolean isMod = (boolean) session.getAttribute("isModerator");

        if (!isOp && !isMod) {
            //Non può modificare il thread
            Functions.handleError(
                    req, resp, HttpServletResponse.SC_UNAUTHORIZED,
                    "Ma ddo t abbij.....");
            return;
        }

        //Può modificare il thread
        req.setAttribute("thread", thread);
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                "/createThread.jsp");
        dispatcher.forward(req, resp);
    }

    /**
     * Gestisce la richiesta POST per modificare un thread.
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
        List<String> errors = new ArrayList<>();
        req.setAttribute("errors", errors);

        HttpSession session = req.getSession();

        if (session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //Recupero usernameReq dalla sessione
        User user = (User) session.getAttribute("user");
        String usernameReq = user.getUsername();

        String idThreadString = req.getParameter("idThread");
        String title = req.getParameter("title");
        String body = req.getParameter("body");
        String categoryString = req.getParameter("category");

        //Recupero informazioni thread
        Thread thread;
        long idThread;
        try {
            idThread = Long.parseLong(idThreadString);
            thread = threadService.findThreadById(idThread);

            if (thread == null) {
                Functions.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }

            //Thread recuperato, verifico permessi di modifica
            boolean isOp = thread.getUsername().equals(usernameReq);
            boolean isMod = (boolean) session.getAttribute("isModerator");

            if (!isOp && !isMod) {
                //Non può modificare il thread
                Functions.handleError(
                        req, resp, HttpServletResponse.SC_UNAUTHORIZED,
                        "Ma ddo t abbij.....");
                return;
            }

            //Può modificare il thread.
            Category category = Category.valueOf(categoryString);
            threadService.updateThread(
                    idThread,
                    usernameReq,
                    title,
                    body,
                    category
            );

            resp.sendRedirect(
                    req.getContextPath() + "/thread?idThread=" + idThread);
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di modifica thread", e);
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

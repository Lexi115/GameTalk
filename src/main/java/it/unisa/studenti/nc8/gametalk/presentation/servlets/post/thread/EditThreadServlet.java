package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
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
 * Servlet per modificare un thread esistente.
 */
@WebServlet("/editThread")
public class EditThreadServlet extends ThreadServlet {

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
        ErrorHandler errorHandler = getErrorHandler();
        ThreadService threadService = getThreadService();
        HttpSession session = req.getSession();

        String idThreadString = req.getParameter("idThread");
        User user = (User) session.getAttribute("user");
        String usernameReq = user.getUsername();

        //Recupero thread da modificare
        Thread thread;
        try {
            long idThread = Long.parseLong(idThreadString);
            thread = threadService.findThreadById(idThread);

            if (thread == null) {
                errorHandler.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di modifica thread", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
            return;
        } catch (NumberFormatException e) {
            LOGGER.error("Parametri non validi", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
            return;
        }

        //Thread esiste, controllo privilegi di modifica.
        if (verifyPermission(thread, usernameReq, session)) {
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
        ErrorHandler errorHandler = getErrorHandler();
        ThreadService threadService = getThreadService();
        HttpSession session = req.getSession();

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
                errorHandler.handleError(
                        req, resp, HttpServletResponse.SC_NOT_FOUND,
                        "Thread non trovato");
                return;
            }

            if (!verifyPermission(thread, usernameReq, session)) {
                //Non può modificare il thread
                errorHandler.handleError(
                        req, resp, HttpServletResponse.SC_UNAUTHORIZED,
                        "Ma ddo t abbij....."); //todo: lo lasciamo così? lol
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
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Parametri non validi", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Parametri non validi");
        }
    }

    private static boolean verifyPermission(
            final Thread thread,
            final String usernameReq,
            final HttpSession session
    ) {
        //Thread recuperato, verifico permessi di modifica
        boolean isOp = thread.getUsername().equals(usernameReq);
        boolean isMod = (boolean) session.getAttribute("isModerator");

        return isOp || isMod;
    }
}

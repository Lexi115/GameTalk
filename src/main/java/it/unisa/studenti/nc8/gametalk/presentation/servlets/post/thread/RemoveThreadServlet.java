package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet per rimuovere un thread esistente.
 */
@WebServlet("/removeThread")
public class RemoveThreadServlet extends ThreadServlet {

    /**
     * Gestisce la richiesta POST per eliminare un thread.
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
        String idThreadString = req.getParameter("threadId");

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

            //Può eliminare il thread.
            threadService.removeThread(idThread);

            resp.sendRedirect(
                    req.getContextPath() + "/");
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di eliminazione thread", e);
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
        //Thread recuperato, verifico permessi di rimozione
        boolean isOp = thread.getUsername().equals(usernameReq);
        boolean isMod = (boolean) session.getAttribute("isModerator");
        return isOp || isMod;
    }
}

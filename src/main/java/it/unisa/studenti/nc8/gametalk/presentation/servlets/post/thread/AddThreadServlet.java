package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.thread;

import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;

/**
 * Servlet per aggiungere un thread.
 */
@WebServlet("/addThread")
public class AddThreadServlet extends ThreadServlet {

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
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Writer writer = resp.getWriter();
        ThreadService threadService = getThreadService();

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

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write("{\"threadId\": " + threadId + "}");

        } catch (ServiceException e) {
            LOGGER.error("Errore interno durante la creazione del thread", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\": \"Errore interno del server\"}");

        } catch (IllegalArgumentException | NullPointerException e) {
            LOGGER.error("Parametri non validi", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("{\"error\": \"Parametri non validi\"}");
        }
    }
}

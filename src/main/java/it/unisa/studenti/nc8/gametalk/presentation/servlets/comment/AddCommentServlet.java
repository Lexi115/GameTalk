package it.unisa.studenti.nc8.gametalk.presentation.servlets.comment;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.service.post.CommentService;
import it.unisa.studenti.nc8.gametalk.business.service.post.CommentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;

@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {
    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();
    /** La classe di servizio per recuperare il thread. */
    private CommentService commentService;

    /**
     * Init.
     */
    @Override
    public void init() {
        this.commentService = new CommentServiceImpl(
                Functions.getContextDatabase(this.getServletContext()));
    }

    /**
     * Gestisce la richiesta POST per creare un commento.
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
        Writer writer = resp.getWriter();
        HttpSession session = req.getSession();

        //Recupero usernameOp dalla sessione
        User user = (User) session.getAttribute("user");
        String usernameOp = user.getUsername();

        long threadId = Long.parseLong(req.getParameter("idThread"));
        String body = req.getParameter("body");

        try {
            commentService.addComment(threadId, usernameOp, body);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write("{\"status\": \"OK.\"}");
        } catch (ServiceException e) {
            LOGGER.error("Errore durante l'aggiunta del commento.", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(
                    "{\"error\": \"Errore durante l'aggiunta del commento.\"}");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Parametri commento non validi", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(
                    "{\"error\": \"Contenuto commento non valido.\"}");
        }
    }
}

package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.comment;

import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.JsonSerializer;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.JsonSerializerImpl;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet per ottenere i commenti di un thread dato il suo ID.
 * Supporta la paginazione.
 * Restituisce il risultato in formato JSON.
 */
@WebServlet("/getThreadComments")
public class GetThreadCommentsServlet extends CommentServlet {

    /** Pagina default. */
    private static final int DEFAULT_PAGE = 1;

    /** Numero default di commenti per pagina. */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /** Il serializzatore JSON. */
    private JsonSerializer jsonSerializer;

    /**
     * Init.
     */
    @Override
    public void init() {
        super.init();
        jsonSerializer = new JsonSerializerImpl();
    }

    /**
     * Gestisce la richiesta GET per ottenere i commenti di un thread.
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
    ) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        Writer writer = resp.getWriter();

        // ID del thread
        String threadIdParam = req.getParameter("threadId");
        long threadId;
        try {
            threadId = Long.parseLong(threadIdParam);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("{\"error\": \"ID del thread non valido\"}");
            return;
        }

        //Username richiedente
        User user = (User) session.getAttribute("user");
        String username = (user != null) ? user.getUsername() : null;

        // Parametri di paginazione
        String pageParam = req.getParameter("page");
        int page = DEFAULT_PAGE;
        int pageSize = DEFAULT_PAGE_SIZE;

        try {
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (page <= 0) {
                throw new IllegalArgumentException(
                        "Numero pagina deve essere maggiore di 0");
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(
                    "{\"error\": \"Pagina non valida\"}");
            return;
        }

        CommentService commentService = getCommentService();
        try {
            // Ottieni i commenti del thread
            List<Comment> comments = commentService.findCommentsByThreadId(
                    threadId, username, page, pageSize);
            long totalComments =
                    commentService.countCommentsByThreadId(threadId);

            //Recupera valutazioni personali ai commenti, se l'utente è loggato.
            boolean isLoggedIn = user != null;
            List<Map<String, Object>> loggedInComments = new ArrayList<>();
            if (isLoggedIn) {
                Map<Long, Integer> personalRatings =
                        commentService.getPersonalVotes(threadId, username);

                // Commenti con rating personale
                for (Comment comment : comments) {
                    Map<String, Object> commentData = new HashMap<>();
                    commentData.put("id", comment.getId());
                    commentData.put("author", comment.getUsername());
                    commentData.put("body", StringEscapeUtils.escapeEcmaScript(comment.getBody()));
                    commentData.put("creationDate", comment.getCreationDate());
                    commentData.put("votes", comment.getVotes());
                    int rating = personalRatings.getOrDefault(comment.getId(), 0);
                    commentData.put("personalRating", rating);
                    loggedInComments.add(commentData);
                }
            }


            // Prepara la risposta JSON
            Map<String, Object> response = new HashMap<>();
            response.put("threadId", threadId);
            response.put("page", page);
            response.put("pageSize", pageSize);

            //Se l'utente è loggato appende a ogni commento il voto personale.
            if (isLoggedIn) {
                response.put("comments", loggedInComments);
            } else {
                response.put("comments", comments);
            }

            response.put("totalComments", totalComments);
            response.put("totalPages",
                    (int) Math.ceil((double) totalComments / pageSize));

            // Scrivi la risposta
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(jsonSerializer.stringify(response));

        } catch (Exception e) {
            LOGGER.error("Errore durante la chiamata API", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\": \"Errore interno del server\"}");
        }
    }
}

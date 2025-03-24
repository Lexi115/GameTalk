package it.unisa.studenti.nc8.gametalk.presentation.servlets.post.comment;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Writer;

/**
 * Servlet per rimuovere un commento dato il suo ID.
 * Restituisce l'esito in formato JSON.
 */
@WebServlet("/removeComment")
public class RemoveCommentServlet extends CommentServlet {

    /**
     * Gestisce la richiesta POST per eliminare un commento.
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

        //Recupero usernameReq dalla sessione
        User user = (User) session.getAttribute("user");
        String usernameReq = user.getUsername();

        String idCommentString = req.getParameter("commentId");

        //Recupero informazioni commento
        Comment comment;
        long idComment;
        CommentService commentService = getCommentService();
        try {
            idComment = Long.parseLong(idCommentString);
            comment = commentService.findCommentById(idComment);

            if (comment == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                writer.write(
                        "{\"error\": \"Nessun commento trovato.\"}");
                return;
            }

            if (!verifyPermission(
                    comment, usernameReq, session)) {
                //Non può modificare il thread
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                writer.write(
                        "{\"error\": \"Non puoi eliminare il commento.\"}");
                return;
            }

            //Può eliminare il commento.
            commentService.deleteComment(idComment);

            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(
                    "{\"status\": \"OK\"}");
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di eliminazione commento", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write(
            "{\"error\": \"Errore durante la rimozione del commento.\"}");
        } catch (IllegalArgumentException e) {
            LOGGER.error("Parametri rimozione non validi", e);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write(
                    "{\"error\": \"Parametri non validi.\"}");
        }
    }

    private static boolean verifyPermission(
            final Comment comment,
            final String usernameReq,
            final HttpSession session
    ) {
        //Thread recuperato, verifico permessi di modifica
        boolean isOp = comment.getUsername().equals(usernameReq);
        boolean isMod = (boolean) session.getAttribute("isModerator");

        return isOp || isMod;
    }
}

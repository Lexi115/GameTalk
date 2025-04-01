package it.unisa.studenti.nc8.gametalk.presentation.servlets.user;

import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.JsonSerializer;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.JsonSerializerImpl;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/mod/getBannedUsers")
public class GetBannedUsersServlet extends UserServlet {

    /**
     * Pagina default.
     */
    private static final int DEFAULT_PAGE = 1;

    /**
     * Numero default di commenti per pagina.
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Il serializzatore JSON.
     */
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
     * Gestisce la richiesta GET per ottenere la lista degli utenti bannati.
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
        Writer writer = resp.getWriter();

        // Parametri di paginazione
        String pageParam = req.getParameter("page");
        int page = DEFAULT_PAGE;
        int pageSize = DEFAULT_PAGE_SIZE;

        try {
            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (page <= 0) {
                throw new IllegalArgumentException("Numero pagina deve essere maggiore di 0");
            }
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("{\"error\": \"Pagina non valida\"}");
            return;
        }

        UserService userService = getUserService();
        try {
            // Ottieni gli utenti bannati
            List<User> bannedUsers = userService.findBannedUsers(page, pageSize);

            // Prepara la risposta JSON
            Map<String, Object> response = new HashMap<>();
            response.put("bannedUsers", bannedUsers);
            response.put("page", page);
            response.put("pageSize", pageSize);

            // Scrivi la risposta
            resp.setStatus(HttpServletResponse.SC_OK);
            writer.write(jsonSerializer.stringify(response));

        } catch (Exception e) {
            LOGGER.error("Errore durante la chiamata API", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\": \"Errore interno del server.\"}");
        }
    }
}

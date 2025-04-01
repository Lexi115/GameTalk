package it.unisa.studenti.nc8.gametalk.presentation.servlets;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.factories.ServiceFactory;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

/**
 * Servlet per gestire la registrazione di un utente.
 * Riceve username e password.
 */
@WebServlet("/signup")
@MultipartConfig
public class SignupServlet extends HttpServlet {

    /** Logger. **/
    private static final Logger LOGGER =
            LoggerFactory.getLogger(SignupServlet.class);

    /** La classe di servizio per creare l'utente. */
    private UserService userService;

    /**
     * Init.
     */
    @Override
    public void init() {
        ServletContext ctx = getServletContext();
        ServiceFactory serviceFactory =
                (ServiceFactory) ctx.getAttribute("serviceFactory");

        this.userService = serviceFactory.createUserService();
    }

    /**
     * Gestisce la richiesta POST per registrare l'utente.
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

        // Parametri di registrazione (username e password)
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            userService.createUser(username, password);

            // Reindirizza sulla pagina home dopo la creazione dell'utente
            resp.setStatus(HttpServletResponse.SC_CREATED);
            writer.write("{}");
        } catch (ServiceException e) {
            LOGGER.error("Errore con il servizio di registrazione", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writer.write("{\"error\": \"Errore interno del server\"}");

        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            writer.write("{\"error\": \"Parametri non validi\"}");
        }
    }
}

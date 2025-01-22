package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import it.unisa.studenti.nc8.gametalk.business.service.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.service.auth.AuthenticationServiceImpl;
import it.unisa.studenti.nc8.gametalk.business.service.user.UserService;
import it.unisa.studenti.nc8.gametalk.business.service.user.UserServiceImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet per gestire il login degli utenti.
 * Riceve username e password, autentica l'utente
 * e imposta un cookie di autenticazione.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    /** Logger. **/
    private static final Logger LOGGER = LogManager.getLogger();

    /** La classe di servizio per effettuare l'autenticazione. */
    private AuthenticationService authenticationService;

    /** La classe di servizio per recuperare l'utente. */
    private UserService userService;

    /** Durata cookie di autenticazione. */
    private static final int AUTH_TOKEN_COOKIE_EXPIRY = 86400 * 7; // 7 giorni

    /**
     * Init.
     */
    @Override
    public void init() {
        this.authenticationService = new AuthenticationServiceImpl(
                Functions.getContextDatabase(this.getServletContext()));
        this.userService = new UserServiceImpl(
                Functions.getContextDatabase(this.getServletContext()));
    }

    /**
     * Gestisce la richiesta GET per visualizzare la pagina di login.
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
        RequestDispatcher rd = req.getRequestDispatcher("login.jsp");
        rd.forward(req, resp);
    }

    /**
     * Gestisce la richiesta POST per autenticare l'utente.
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

        // Lista di eventuali errori.
        List<String> errors = new ArrayList<>();
        req.setAttribute("errors", errors);

        // Parametri di login (username e password)
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            // Autentica l'utente
            User loggedUser = authenticationService.login(username, password);

            // Genera cookie token di autenticazione
            Cookie authTokenCookie = Functions.createSecureCookie(
                    "auth_token",
                    Functions.hash(username + password),
                    AUTH_TOKEN_COOKIE_EXPIRY
            );

            // Aggiorna token nel database (doppio hash)
            userService.updateToken(username, Functions.hash(
                    authTokenCookie.getValue()));

            // Imposta utente nella sessione
            session.setAttribute("user", loggedUser);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.addCookie(authTokenCookie);
            resp.sendRedirect(req.getContextPath() + "/");

        } catch (ServiceException e) {
            LOGGER.error("Errore con servizio di autenticazione", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            errors.add("Errore interno del server.");
            doGet(req, resp);

        } catch (AuthenticationException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            errors.add("Credenziali non valide.");
            doGet(req, resp);
        }
    }
}

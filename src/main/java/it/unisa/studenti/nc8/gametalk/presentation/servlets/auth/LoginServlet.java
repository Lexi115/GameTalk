package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.exceptions.AuthenticationException;
import it.unisa.studenti.nc8.gametalk.business.exceptions.ServiceException;
import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;
import it.unisa.studenti.nc8.gametalk.presentation.utils.cookies.CookieHelper;
import it.unisa.studenti.nc8.gametalk.presentation.utils.handlers.ErrorHandler;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet per gestire il login degli utenti.
 * Riceve username e password, autentica l'utente
 * e imposta un cookie di autenticazione.
 */
@WebServlet("/login")
public class LoginServlet extends AuthenticationServlet {

    /** Durata cookie di autenticazione. */
    private static final int AUTH_TOKEN_COOKIE_EXPIRY = 86400 * 7; // 7 giorni

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
        ErrorHandler errorHandler = getErrorHandler();
        AuthenticationService authenticationService =
                getAuthenticationService();
        UserService userService = getUserService();

        // Parametri di login (username e password)
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try {
            HttpSession session = req.getSession();

            // Autentica l'utente
            User loggedUser = authenticationService.login(username, password);

            // Genera cookie token di autenticazione
            CookieHelper cookieHelper = getCookieHelper();
            String authToken = authenticationService.generateToken(
                    username + password);
            Cookie authTokenCookie = cookieHelper.createCookie(
                    "auth_token", authToken, AUTH_TOKEN_COOKIE_EXPIRY);

            // Aggiorna token nel database (doppio hash)
            String hashedAuthToken =
                    authenticationService.generateToken(authToken);
            userService.updateToken(username, hashedAuthToken);

            // Imposta utente nella sessione
            session.setAttribute("user", loggedUser);
            resp.addCookie(authTokenCookie);
            resp.sendRedirect(req.getContextPath() + "/");

        } catch (ServiceException e) {
            LOGGER.error("Errore con servizio di autenticazione", e);
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage()
            );

        } catch (AuthenticationException | IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            req.setAttribute("error", e.getMessage());
        }
    }
}

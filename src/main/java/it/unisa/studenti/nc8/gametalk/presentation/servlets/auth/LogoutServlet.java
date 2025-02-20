package it.unisa.studenti.nc8.gametalk.presentation.servlets.auth;

import it.unisa.studenti.nc8.gametalk.business.utils.Functions;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet per gestire il logout degli utenti.
 */
@WebServlet("/logout")
public class LogoutServlet extends AuthenticationServlet {

    /**
     * Gestisce la richiesta GET per effettuare il logout.
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
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Invalida cookie di autenticazione
        Cookie expiredCookie = Functions.createSecureCookie(
                "auth_token", "", 0);
        resp.addCookie(expiredCookie);

        resp.sendRedirect(req.getContextPath() + "/");
    }
}

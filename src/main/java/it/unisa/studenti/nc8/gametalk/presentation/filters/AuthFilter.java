package it.unisa.studenti.nc8.gametalk.presentation.filters;

import it.unisa.studenti.nc8.gametalk.business.model.user.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filtro che intercetta le richieste HTTP per garantire che solo gli utenti
 * autenticati possano accedere ad alcune pagine (ad esempio
 * il proprio profilo).
 * <p>
 * Se l'utente non è autenticato, viene reindirizzato alla pagina di login.
 * </p>
 */
@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {

    /**
     * Intercetta le richieste HTTP per verificare che l'utente sia
     * autenticato. Nel caso non lo sia, viene reindirizzato alla pagina
     * di login.
     *
     * @param request  la richiesta HTTP in entrata.
     * @param response la risposta HTTP in uscita.
     * @param chain    la catena di filtri a cui delegare la richiesta.
     * @throws ServletException se si verifica un errore generico.
     * @throws IOException se si verifica un errore I/O.
     */
    @Override
    public void doFilter(
            final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain
    ) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        User user = (session != null)
                ? (User) session.getAttribute("user") : null;

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}

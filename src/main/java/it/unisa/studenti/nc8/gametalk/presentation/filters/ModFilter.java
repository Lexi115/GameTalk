package it.unisa.studenti.nc8.gametalk.presentation.filters;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Filtro che permette solo agli utenti moderatori di accedere
 * a determinate pagine o compiere certe azioni.
 * <p>
 * Se l'utente non è autenticato o non ha il ruolo richiesto,
 * viene reindirizzato a una pagina di errore.
 * </p>
 */
@WebFilter(filterName = "ModFilter")
public class ModFilter implements Filter {

    /**
     * Intercetta le richieste HTTP per verificare che l'utente sia
     * un moderatore. Se non è autorizzato,
     * viene reindirizzato a una pagina di errore.
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
        User user = (User) req.getSession(false).getAttribute("user");

        if (user.getRole() != Role.Moderator) {
            errorHandler.handleError(
                    req, resp, HttpServletResponse.SC_FORBIDDEN,
                    "Accesso negato!");
            return;
        }

        chain.doFilter(request, response);
    }
}

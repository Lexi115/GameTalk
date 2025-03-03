package it.unisa.studenti.nc8.gametalk.business.factories;

import it.unisa.studenti.nc8.gametalk.business.services.auth.AuthenticationService;
import it.unisa.studenti.nc8.gametalk.business.services.post.comment.CommentService;
import it.unisa.studenti.nc8.gametalk.business.services.post.thread.ThreadService;
import it.unisa.studenti.nc8.gametalk.business.services.user.UserService;

/**
 * Factory astratta per la creazione delle classi di servizio.
 */
public interface ServiceFactory {

    /**
     * Crea e restituisce un'istanza del servizio di autenticazione.
     *
     * @return un'istanza di {@link AuthenticationService}
     */
    AuthenticationService createAuthenticationService();

    /**
     * Crea e restituisce un'istanza del servizio utente.
     *
     * @return un'istanza di {@link UserService}
     */
    UserService createUserService();

    /**
     * Crea e restituisce un'istanza del servizio per la gestione dei thread.
     *
     * @return un'istanza di {@link ThreadService}
     */
    ThreadService createThreadService();

    /**
     * Crea e restituisce un'istanza del servizio per la gestione dei commenti.
     *
     * @return un'istanza di {@link CommentService}
     */
    CommentService createCommentService();
}

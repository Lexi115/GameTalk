package it.unisa.studenti.nc8.gametalk.presentation.utils.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Classe di utilità per creare e recuperare facilmente
 * un cookie.
 */
public class CookieHelperImpl implements CookieHelper {

    /**
     * Recupera un cookie che ha un certo nome.
     *
     * @param name Il nome del cookie da cercare.
     * @param request La request HTTP in cui cercare.
     * @return Il cookie con quel nome, <code>null</code> altrimenti.
     */
    public Cookie getCookie(
            final String name,
            final HttpServletRequest request
    ) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * Crea un cookie con le opzioni di protezione abilitate.
     *
     * @param name   il nome del cookie
     * @param value  il valore del cookie
     * @param expiry il tempo di scadenza del cookie in secondi
     * @return un oggetto {@link Cookie}
     */
    public Cookie createCookie(
            final String name,
            final String value,
            final int expiry
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(true); // Solo HTTPS
        cookie.setMaxAge(expiry); // Scadenza in secondi
        cookie.setHttpOnly(true); // Inaccessibile da JS
        cookie.setPath("/"); // Valido per tutto il sito

        // I browser moderni bloccano i cookies senza flag "SameSite"
        cookie.setAttribute("SameSite", "Strict");

        return cookie;
    }
}

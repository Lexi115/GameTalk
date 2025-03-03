package it.unisa.studenti.nc8.gametalk.presentation.utils.cookies;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Interfaccia di utilità per creare e recuperare facilmente
 * un cookie.
 */
public interface CookieHelper {

    /**
     * Recupera un cookie che ha un certo nome.
     *
     * @param name Il nome del cookie da cercare.
     * @param request La request HTTP in cui cercare.
     * @return Il cookie con quel nome, <code>null</code> altrimenti.
     */
    Cookie getCookie(String name, HttpServletRequest request);

    /**
     * Crea un cookie con le opzioni di protezione abilitate.
     *
     * @param name   il nome del cookie
     * @param value  il valore del cookie
     * @param expiry il tempo di scadenza del cookie in secondi
     * @return un oggetto {@link Cookie}
     */
    Cookie createCookie(String name, String value, int expiry);
}

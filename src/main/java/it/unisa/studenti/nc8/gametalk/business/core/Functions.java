package it.unisa.studenti.nc8.gametalk.business.core;

import com.google.common.hash.Hashing;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe di utilità che fornisce metodi statici per operazioni comuni
 * utilizzate nell'applicazione.
 */
public abstract class Functions {

    /**
     * Recupera il database dal contesto della servlet.
     *
     * @param servletContext la servlet da cui ottenere il contesto.
     * @return il database dal contesto della servlet,
     * oppure {@code null} se non trovato.
     */
    public static Database getContextDatabase(
            final ServletContext servletContext
    ) {
        Object obj = servletContext.getAttribute("db");
        return obj == null ? null : (Database) obj;
    }

    /**
     * Verifica se una stringa corrisponde a una determinata
     * espressione regolare.
     *
     * @param regex l'espressione regolare da utilizzare per il confronto.
     * @param input la stringa da verificare.
     * @return {@code true} se la stringa corrisponde all'espressione
     * regolare, {@code false} altrimenti.
     */
    public static boolean matchesRegex(final String regex, final String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    /**
     * Genera l'hash SHA-256 di una stringa.
     *
     * @param input La stringa da hashare
     * @return La stringa hashato SHA-256
     */
    public static String hash(final String input) {
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }

    /**
     * Recupera un cookie che ha un certo nome.
     *
     * @param name Il nome del cookie da cercare.
     * @param request La request http in cui cercare.
     * @return Il cookie con quel nome, <code>null</code> altrimenti.
     */
    public static Cookie getCookie(
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
}

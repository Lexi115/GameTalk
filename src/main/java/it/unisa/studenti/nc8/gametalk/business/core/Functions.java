package it.unisa.studenti.nc8.gametalk.business.core;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unisa.studenti.nc8.gametalk.business.adapters.json.LocalDateAdapter;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Cookie;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
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
     * @param servletContext il context.
     * @return il database dal servlet context
     * oppure {@code null} se non trovato.
     */
    public static Database getContextDatabase(
            final ServletContext servletContext) {
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
     * Serializza un oggetto in una stringa JSON.
     *
     * @param obj L'oggetto da serializzare.
     * @return la stringa JSON corrispondente.
     */
    public static String toJson(final Object obj) {
        Gson gson = new GsonBuilder()
                // Adattatore per LocalDate
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(obj);
    }

    /**
     * Crea un cookie sicuro con le opzioni di protezione abilitate.
     *
     * @param name   il nome del cookie
     * @param value  il valore del cookie
     * @param expiry il tempo di scadenza del cookie in secondi
     * @return un oggetto {@link Cookie} sicuro
     */
    public static Cookie createSecureCookie(
            final String name,
            final String value,
            final int expiry
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(true); // Solo HTTPS
        cookie.setMaxAge(expiry); // Scadenza in secondi
        cookie.setHttpOnly(true); // Inaccessibile da JS
        cookie.setPath("/"); // Valido per tutto il sito
        return cookie;
    }
}

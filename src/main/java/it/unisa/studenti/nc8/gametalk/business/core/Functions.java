package it.unisa.studenti.nc8.gametalk.business.core;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unisa.studenti.nc8.gametalk.business.adapters.json.LocalDateAdapter;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
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

    /**
     * Gestisce gli errori mostrando una pagina di errore personalizzata.
     * Imposta il codice di stato e passa il messaggio di errore alla vista.
     *
     * @param req     L'oggetto HTTP request.
     * @param resp    L'oggetto HTTP response.
     * @param status  Il codice di stato HTTP da impostare nella risposta.
     * @param message Il messaggio di errore da mostrare all'utente.
     * @throws ServletException Se si verifica un errore generico.
     * @throws IOException      Se si verifica un errore I/O.
     */
    public static void handleError(
            final HttpServletRequest req,
            final HttpServletResponse resp,
            final int status,
            final String message
    ) throws ServletException, IOException {
        req.setAttribute("errorMessage", message);
        req.setAttribute("errorCode", status);
        resp.setStatus(status);

        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/error.jsp");
        dispatcher.forward(req, resp);
    }
}

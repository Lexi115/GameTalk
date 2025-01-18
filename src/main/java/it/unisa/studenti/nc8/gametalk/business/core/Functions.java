package it.unisa.studenti.nc8.gametalk.business.core;

import com.google.common.hash.Hashing;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import jakarta.servlet.http.HttpServlet;

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
     * @param servlet la servlet da cui ottenere il contesto.
     * @return il database dal contesto della servlet,
     * oppure {@code null} se non trovato.
     */
    public static Database getContextDatabase(final HttpServlet servlet) {
        Object obj = servlet.getServletContext().getAttribute("db");
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
}

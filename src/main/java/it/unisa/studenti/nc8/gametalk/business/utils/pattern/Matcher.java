package it.unisa.studenti.nc8.gametalk.business.utils.pattern;

/**
 * Interfaccia per il matching di stringhe rispetto a un pattern.
 */
public interface Matcher {

    /**
     * Verifica se una stringa corrisponde a un pattern specificato.
     *
     * @param pattern Il pattern da utilizzare per il confronto.
     * @param input La stringa da verificare.
     * @return {@code true} se la stringa corrisponde al pattern,
     *         {@code false} altrimenti.
     */
    boolean matches(String pattern, String input);
}

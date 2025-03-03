package it.unisa.studenti.nc8.gametalk.business.utils.pattern;

import java.util.regex.Pattern;

/**
 * Implementazione dell'interfaccia {@link Matcher} per il
 * matching di espressioni regolari.
 */
public class RegexMatcher implements Matcher {

    /**
     * Verifica se una stringa corrisponde a una determinata
     * espressione regolare.
     *
     * @param regex L'espressione regolare da utilizzare per il confronto.
     * @param input La stringa da verificare.
     * @return {@code true} se la stringa corrisponde all'espressione
     *         regolare, {@code false} altrimenti.
     */
    @Override
    public boolean matches(final String regex, final String input) {
        Pattern pattern = Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}

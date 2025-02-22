package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

/**
 * Interfaccia per la gestione dell'hashing delle stringhe.
 */
public interface Hasher {

    /**
     * Restituisce l'hash di una stringa.
     *
     * @param input La stringa da hashare
     * @return L'hash corrispondente
     */
    String hash(String input);

    /**
     * Verifica se l'input corrisponde all'hash fornito.
     *
     * @param input La stringa da verificare
     * @param hash L'hash da confrontare
     * @return {@code true} se l'input corrisponde all'hash,
     * {@code false} altrimenti
     */
    boolean verify(String input, String hash);
}

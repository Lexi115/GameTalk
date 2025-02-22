package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Classe per la gestione dell'hashing usando l'algoritmo BCrypt.
 */
public class BCryptHasher implements Hasher {

    /**
     * Restituisce l'hash BCrypt di una stringa.
     *
     * @param input La stringa da hashare
     * @return L'hash corrispondente
     */
    @Override
    public String hash(final String input) {
        return BCrypt.withDefaults().hashToString(12, input.toCharArray());
    }

    /**
     * Verifica se l'input corrisponde all'hash BCrypt fornito.
     *
     * @param input La stringa da verificare
     * @param hash L'hash BCrypt da confrontare
     * @return {@code true} se l'input corrisponde all'hash BCrypt,
     * {@code false} altrimenti
     */
    @Override
    public boolean verify(final String input, final String hash) {
        return BCrypt.verifyer().verify(input.toCharArray(), hash).verified;
    }
}

package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Classe per la gestione dell'hashing usando l'algoritmo SHA-256.
 */
public class SHA256Hasher implements Hasher {

    /**
     * Restituisce l'hash SHA-256 di una stringa.
     *
     * @param input La stringa da hashare
     * @return L'hash SHA-256 corrispondente
     */
    @Override
    public String hash(final String input) {
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }

    /**
     * Verifica se l'input corrisponde all'hash SHA-256 fornito.
     *
     * @param input La stringa da verificare
     * @param hash L'hash SHA-256 da confrontare
     * @return {@code true} se l'input corrisponde all'hash SHA-256,
     * {@code false} altrimenti
     */
    @Override
    public boolean verify(final String input, final String hash) {
        return hash(input).equals(hash);
    }
}

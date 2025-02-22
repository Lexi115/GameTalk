package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHA256Hasher implements Hasher {

    /**
     * Restituisce l'hash SHA-256 di una stringa.
     *
     * @param input La stringa da hashare
     * @return La stringa hashata in SHA-256
     */
    @Override
    public String hash(final String input) {
        return Hashing.sha256()
                .hashString(input, StandardCharsets.UTF_8)
                .toString();
    }

    @Override
    public boolean verify(final String input, final String hash) {
        return hash(input).equals(hash);
    }
}

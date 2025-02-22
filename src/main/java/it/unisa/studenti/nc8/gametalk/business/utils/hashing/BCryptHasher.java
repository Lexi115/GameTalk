package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class BCryptHasher implements Hasher {
    @Override
    public String hash(final String input) {
        return BCrypt.withDefaults().hashToString(12, input.toCharArray());
    }

    @Override
    public boolean verify(String input, String hash) {
        return BCrypt.verifyer().verify(input.toCharArray(), hash).verified;
    }
}

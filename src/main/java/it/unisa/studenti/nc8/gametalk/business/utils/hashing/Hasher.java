package it.unisa.studenti.nc8.gametalk.business.utils.hashing;

public interface Hasher {
    String hash(String input);
    boolean verify(String input, String hash);
}

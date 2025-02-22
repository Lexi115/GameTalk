package it.unisa.studenti.nc8.gametalk.config;

/**
 * Interfaccia per la gestione della configurazione dell'applicazione.
 */
public interface Config {

    /**
     * Restituisce il valore associato alla chiave specificata.
     *
     * @param key la chiave della proprietà
     * @return il valore della configurazione o
     * {@code null} se la chiave non esiste
     */
    String get(String key);

    /**
     * Restituisce il valore associato alla chiave specificata,
     * oppure un valore di default.
     *
     * @param key          la chiave della proprietà
     * @param defaultValue il valore di default da restituire
     *                     se la chiave non esiste
     * @return il valore della proprietà o il valore di
     * default se la chiave non esiste
     */
    String get(String key, String defaultValue);
}

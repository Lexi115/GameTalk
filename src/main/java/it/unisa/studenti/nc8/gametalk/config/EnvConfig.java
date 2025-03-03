package it.unisa.studenti.nc8.gametalk.config;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Configurazione dell'applicazione.
 * Carica le variabili da un file `.env`.
 */
public final class EnvConfig implements Config {

    /** Istanza singleton. */
    private static volatile EnvConfig instance;

    /** Oggetto Dotenv per la lettura delle variabili di configurazione. */
    private final Dotenv dotenv;

    /**
     * Costruttore.
     */
    private EnvConfig() {
        dotenv = Dotenv.load();
    }

    /**
     * Restituisce l'istanza singleton della configurazione.
     *
     * @return l'istanza di {@code EnvConfig}
     */
    public static EnvConfig getInstance() {
        if (instance == null) {
            synchronized (EnvConfig.class) {
                if (instance == null) {
                    instance = new EnvConfig();
                }
            }
        }
        return instance;
    }

    /**
     * Restituisce il valore associato alla chiave specificata.
     *
     * @param key la chiave della proprietà
     * @return il valore della configurazione o
     * {@code null} se la chiave non esiste
     */
    @Override
    public String get(final String key) {
        return dotenv.get(key);
    }

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
    @Override
    public String get(final String key, final String defaultValue) {
        return dotenv.get(key, defaultValue);
    }
}

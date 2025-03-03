package it.unisa.studenti.nc8.gametalk.storage.factories;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;

/**
 * Factory astratta per la creazione del database.
 */
public interface DatabaseFactory {

    /**
     * Crea e restituisce un'istanza di {@link Database}.
     *
     * @return Un'istanza di {@link Database}.
     */
    Database createDatabase();
}

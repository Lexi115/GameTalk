package it.unisa.studenti.nc8.gametalk.storage.dao;

import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.mappers.ResultSetMapper;

import java.sql.Connection;

/**
 * Classe astratta che rappresenta un DAO generico per la gestione
 * dell'accesso ai dati tramite un database.
 *
 * <p>Questa classe fornisce un'infrastruttura di base per i DAO
 * specifici, consentendo l'uso di un database e un mapper per il
 * mapping dei risultati del database su oggetti.</p>
 *
 * @param <T> Il tipo di oggetto gestito dal DAO.
 */
public abstract class DatabaseDAO<T> {

    /** Il database utilizzato per le operazioni di accesso ai dati. */
    private final Database db;

    private Connection connection;

    /** Il mapper utilizzato per convertire i {@link java.sql.ResultSet}
     * in oggetti di tipo {@code T}. */
    private final ResultSetMapper<T> mapper;

    /**
     * Costruttore.
     *
     * @param db     Istanza di {@link Database} per la connessione al database.
     * @param mapper Mapper per convertire un {@link java.sql.ResultSet}
     *               in un oggetto entità.
     */
    public DatabaseDAO(
            final Database db,
            final Connection connection,
            final ResultSetMapper<T> mapper
    ) {
        this.db = db;
        this.connection = connection;
        this.mapper = mapper;
    }

    /**
     * Restituisce il database utilizzato per le operazioni di accesso ai dati.
     *
     * @return Il database.
     */
    protected Database getDatabase() {
        return db;
    }

    protected Connection getConnection() {
        return connection;
    }

    /**
     * Restituisce il mapper per convertire i risultati del database
     * in oggetti entità.
     *
     * @return Il mapper.
     */
    protected ResultSetMapper<T> getMapper() {
        return mapper;
    }
}

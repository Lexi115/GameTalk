package it.unisa.studenti.nc8.gametalk.storage.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaccia che definisce un mapper per convertire un {@link ResultSet} in
 * una lista di oggetti di tipo {@code T}.
 *
 * @param <T> Il tipo degli oggetti risultanti dal mapping
 *           del {@link ResultSet}.
 */
public interface ResultSetMapper<T> {

    /**
     * Mappa un {@link ResultSet} su una lista di oggetti di tipo {@code T}.
     *
     * @param rs Il {@link ResultSet} da mappare.
     * @return Una lista di oggetti di tipo {@code T} ottenuti dal
     * mapping del {@link ResultSet}.
     * @throws SQLException Se si verifica un errore nell'accesso
     * ai dati dal {@link ResultSet}.
     */
    List<T> map(ResultSet rs) throws SQLException;
}

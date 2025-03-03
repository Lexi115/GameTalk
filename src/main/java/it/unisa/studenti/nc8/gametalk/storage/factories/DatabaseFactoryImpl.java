package it.unisa.studenti.nc8.gametalk.storage.factories;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import it.unisa.studenti.nc8.gametalk.storage.persistence.Database;
import it.unisa.studenti.nc8.gametalk.storage.persistence.DatabaseImpl;

import javax.sql.DataSource;
import java.util.TimeZone;

/**
 * Factory concreta per la creazione del database.
 * Utilizza HikariCP per gestire il pool di connessioni.
 */
public class DatabaseFactoryImpl implements DatabaseFactory {

    /** Host del database. */
    private final String host;

    /** Porta su cui è in ascolto il database. */
    private final int port;

    /** Nome utente per autenticarsi al database. */
    private final String username;

    /** Password per autenticarsi al database. */
    private final String password;

    /** Nome del database a cui connettersi. */
    private final String databaseName;

    /** Tipo del database a cui connettersi (es. MySQL). */
    private final String type;

    /**
     * Costruttore della factory per il database.
     *
     * @param host         L'host del database.
     * @param port         La porta del database.
     * @param username     Il nome utente per l'autenticazione.
     * @param password     La password per l'autenticazione.
     * @param databaseName Il nome del database.
     * @param type         Il tipo di database (es. MySQL).
     */
    public DatabaseFactoryImpl(
            final String host,
            final int port,
            final String username,
            final String password,
            final String databaseName,
            final String type
    ) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.type = type;
    }

    /**
     * Crea e restituisce un'istanza di {@link Database}.
     *
     * @return Un'istanza di {@link Database}
     * con il pool di connessioni configurato.
     */
    @Override
    public Database createDatabase() {
        DataSource dataSource = createDataSource();
        return new DatabaseImpl(dataSource);
    }

    /**
     * Configura e crea un {@link DataSource} utilizzando HikariCP.
     *
     * @return Un'istanza di {@link DataSource} pronta per l'uso.
     */
    private DataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(getJdbcUrl());
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        config.setMaximumPoolSize(100);
        config.setMinimumIdle(10);
        config.setIdleTimeout(60000);
        config.setMaxLifetime(1800000);
        config.setConnectionTimeout(30000);
        config.setValidationTimeout(2500);
        config.setLeakDetectionThreshold(5000);
        config.setConnectionTestQuery("SELECT 1");

        // Ottimizzazioni delle performance
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.setConnectionInitSql("SET autocommit=true");

        return new HikariDataSource(config);
    }

    /**
     * Costruisce e restituisce l'URL JDBC per la connessione al database.
     *
     * @return L'URL JDBC formattato correttamente.
     */
    private String getJdbcUrl() {
        return "jdbc:" + type + "://" + host + ":" + port + "/"
                + databaseName + "?serverTimezone="
                + TimeZone.getDefault().getID();
    }
}

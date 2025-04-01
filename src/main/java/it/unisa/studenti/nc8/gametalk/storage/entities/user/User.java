package it.unisa.studenti.nc8.gametalk.storage.entities.user;

import com.google.gson.annotations.Expose;
import it.unisa.studenti.nc8.gametalk.business.enums.Role;

import java.time.LocalDate;

/**
 * Entità che rappresenta un utente della piattaforma.
 * Contiene informazioni sull'identità e sullo stato dell'utente.
 */
public class User {

    /** Nome utente scelto dall'utente. */
    @Expose
    private String username;

    /** Password dell'utente. */
    private String password;

    /** Data di creazione dell'utente. */
    private LocalDate creationDate;

    /** Indica se l'utente è bannato. */
    @Expose
    private boolean banned;

    /** Ruolo associato all'utente. */
    @Expose
    private Role role;

    /** Token di autenticazione associato all'utente. */
    private String authToken;

    /**
     * Restituisce il nome utente (ID).
     *
     * @return il nome utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta il nome utente (ID).
     *
     * @param username il nome utente da assegnare.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Restituisce la password.
     *
     * @return l'hash della password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Imposta la password.
     *
     * @param password la password da assegnare.
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Restituisce la data di creazione dell'utente.
     *
     * @return la data di creazione.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Imposta la data di creazione dell'utente.
     *
     * @param creationDate la data di creazione da assegnare.
     */
    public void setCreationDate(final LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Indica se l'utente è bannato.
     *
     * @return {@code true} se l'utente è bannato, altrimenti {@code false}.
     */
    public boolean isBanned() {
        return banned;
    }

    /**
     * Imposta lo stato di ban dell'utente.
     *
     * @param banned {@code true} per bannare l'utente,
     * {@code false} altrimenti.
     */
    public void setBanned(final boolean banned) {
        this.banned = banned;
    }

    /**
     * Restituisce il ruolo dell'utente.
     *
     * @return il ruolo dell'utente.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Imposta il ruolo dell'utente.
     *
     * @param role il ruolo da assegnare all'utente.
     */
    public void setRole(final Role role) {
        this.role = role;
    }

    /**
     * Restituisce il token dell'utente.
     *
     * @return il token dell'utente.
     */
    public String getAuthToken() {
        return authToken;
    }

    /**
     * Imposta il token dell'utente.
     *
     * @param authToken il token da assegnare all'utente.
     */
    public void setAuthToken(final String authToken) {
        this.authToken = authToken;
    }

    /**
     * Restituisce una rappresentazione testuale dell'utente.
     *
     * @return una stringa che rappresenta l'utente.
     */
    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", creationDate=" + creationDate
                + ", banned=" + banned
                + ", role=" + role
                + ", authToken='" + authToken + '\''
                + '}';
    }
}

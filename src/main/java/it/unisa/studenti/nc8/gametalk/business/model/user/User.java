package it.unisa.studenti.nc8.gametalk.business.model.user;

import it.unisa.studenti.nc8.gametalk.business.enums.Role;

import java.util.Date;

/**
 * Classe che rappresenta un utente nel sistema.
 * Contiene informazioni sull'identità e sullo stato dell'utente.
 */
public class User {

    /** Identificativo univoco dell'utente. */
    private long id;

    /** Nome utente scelto dall'utente. */
    private String username;

    /** Hash della password dell'utente. */
    private String passwordHash;

    /** Data di creazione dell'utente. */
    private Date creationDate;

    /** Indica se l'utente è bannato. */
    private boolean banned;

    /** Numero di avvertimenti ricevuti dall'utente. */
    private int strikes;

    /** Ruolo associato all'utente. */
    private Role role;

    /**
     * Restituisce l'ID dell'utente.
     *
     * @return l'ID dell'utente.
     */
    public long getId() {
        return id;
    }

    /**
     * Imposta l'ID dell'utente.
     *
     * @param id l'ID da assegnare all'utente.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Restituisce il nome utente.
     *
     * @return il nome utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta il nome utente.
     *
     * @param username il nome utente da assegnare.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Restituisce l'hash della password.
     *
     * @return l'hash della password.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Imposta l'hash della password.
     *
     * @param passwordHash l'hash della password da assegnare.
     */
    public void setPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Restituisce la data di creazione dell'utente.
     *
     * @return la data di creazione.
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Imposta la data di creazione dell'utente.
     *
     * @param creationDate la data di creazione da assegnare.
     */
    public void setCreationDate(final Date creationDate) {
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
     * Restituisce il numero di avvertimenti dell'utente.
     *
     * @return il numero di avvertimenti.
     */
    public int getStrikes() {
        return strikes;
    }

    /**
     * Imposta il numero di avvertimenti dell'utente.
     *
     * @param strikes il numero di avvertimenti da assegnare.
     */
    public void setStrikes(final int strikes) {
        this.strikes = strikes;
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
}

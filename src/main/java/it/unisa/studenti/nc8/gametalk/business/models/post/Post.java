package it.unisa.studenti.nc8.gametalk.business.models.post;

import com.google.gson.annotations.Expose;

import java.time.LocalDate;

/**
 * Classe astratta che rappresenta un post generico.
 * Fornisce proprietà comuni a tutti i tipi di post.
 */
public abstract class Post {

    /** Identificativo univoco del post. */
    @Expose
    private long id;

    /** Identificativo dell'utente che ha creato il post. */
    @Expose
    private String username;

    /** Contenuto testuale del post. */
    @Expose
    private String body;

    /** Numero di voti ricevuti dal post. */
    @Expose
    private int votes;

    /** Data di creazione del post. */
    @Expose
    private LocalDate creationDate;
    /**
     * Restituisce l'ID del post.
     *
     * @return l'ID del post.
     */
    public long getId() {
        return id;
    }

    /**
     * Imposta l'ID del post.
     *
     * @param id l'ID da assegnare al post.
     */
    public void setId(final long id) {
        this.id = id;
    }

    /**
     * Restituisce l'ID dell'utente che ha creato il post.
     *
     * @return l'ID dell'utente.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Imposta l'ID dell'utente che ha creato il post.
     *
     * @param username l'ID dell'utente da assegnare.
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Restituisce il contenuto testuale del post.
     *
     * @return il contenuto del post.
     */
    public String getBody() {
        return body;
    }

    /**
     * Imposta il contenuto testuale del post.
     *
     * @param body il contenuto da assegnare al post.
     */
    public void setBody(final String body) {
        this.body = body;
    }

    /**
     * Restituisce il numero di voti ricevuti dal post.
     *
     * @return il numero di voti.
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Imposta il numero di voti ricevuti dal post.
     *
     * @param votes il numero di voti da assegnare.
     */
    public void setVotes(final int votes) {
        this.votes = votes;
    }

    /**
     * Restituisce la data di creazione del post.
     *
     * @return la data di creazione.
     */
    public LocalDate getCreationDate() {
        return creationDate;
    }

    /**
     * Imposta la data di creazione del post.
     *
     * @param creationDate la data di creazione da assegnare.
     */
    public void setCreationDate(final LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Restituisce una rappresentazione testuale del post.
     *
     * @return una stringa che rappresenta il post.
     */
    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", username=" + username
                + ", body='" + body + '\''
                + ", votes=" + votes
                + ", creationDate=" + creationDate
                + '}';
    }
}

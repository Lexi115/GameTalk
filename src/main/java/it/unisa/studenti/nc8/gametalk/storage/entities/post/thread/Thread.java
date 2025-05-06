package it.unisa.studenti.nc8.gametalk.storage.entities.post.thread;

import com.google.gson.annotations.Expose;
import it.unisa.studenti.nc8.gametalk.business.enums.Category;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.Post;

/**
 * Entità che rappresenta un thread.
 * Rispetto a un generico {@link Post}, contiene informazioni
 * aggiuntive come titolo, stato di archiviazione e categoria.
 */
public class Thread extends Post {

    /** Titolo del thread. */
    @Expose
    private String title;

    /** Indica se il thread è archiviato. */
    @Expose
    private boolean archived;

    /** Categoria a cui appartiene il thread. */
    private Category category;

    /**
     * Restituisce il titolo del thread.
     *
     * @return il titolo del thread.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il titolo del thread.
     *
     * @param title il titolo da assegnare.
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Indica se il thread è archiviato.
     *
     * @return {@code true} se il thread è archiviato, altrimenti {@code false}.
     */
    public boolean isArchived() {
        return archived;
    }

    /**
     * Imposta lo stato di archiviazione del thread.
     *
     * @param archived {@code true} per archiviare il thread,
     *                 {@code false} altrimenti.
     */
    public void setArchived(final boolean archived) {
        this.archived = archived;
    }

    /**
     * Restituisce la categoria del thread.
     *
     * @return la categoria del thread.
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Imposta la categoria del thread.
     *
     * @param category la categoria da assegnare.
     */
    public void setCategory(final Category category) {
        this.category = category;
    }

    /**
     * Restituisce una rappresentazione testuale del thread.
     *
     * @return una stringa che rappresenta il thread.
     */
    @Override
    public String toString() {
        return "Thread{"
                + "title='" + title + '\''
                + ", archived=" + archived
                + ", category=" + category
                + "} " + super.toString();
    }
}

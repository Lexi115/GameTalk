package it.unisa.studenti.nc8.gametalk.storage.entities.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.entities.post.Post;

/**
 * Entità che rappresenta un commento di un thread.
 * Rispetto a un generico {@link Post}, contiene informazioni
 * aggiuntive come l'ID del thread corrispondente.
 */
public class Comment extends Post {

    /** Identificativo del thread a cui il commento è associato. */
    private long threadId;

    /**
     * Restituisce l'ID del thread associato al commento.
     *
     * @return l'ID del thread.
     */
    public long getThreadId() {
        return threadId;
    }

    /**
     * Imposta l'ID del thread associato al commento.
     *
     * @param threadId l'ID del thread da assegnare.
     */
    public void setThreadId(final long threadId) {
        this.threadId = threadId;
    }

    /**
     * Restituisce una rappresentazione testuale del commento.
     *
     * @return una stringa che rappresenta il commento.
     */
    @Override
    public String toString() {
        return "Comment{"
                + "threadId=" + threadId
                + "} " + super.toString();
    }
}

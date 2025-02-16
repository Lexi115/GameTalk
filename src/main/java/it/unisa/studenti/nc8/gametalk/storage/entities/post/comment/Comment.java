package it.unisa.studenti.nc8.gametalk.storage.entities.post.comment;

import it.unisa.studenti.nc8.gametalk.storage.entities.post.Post;

/**
 * Classe che rappresenta un thread.
 * Contiene informazioni aggiuntive come ID thread.
 * <p>
 * Estende {@link Post}.
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

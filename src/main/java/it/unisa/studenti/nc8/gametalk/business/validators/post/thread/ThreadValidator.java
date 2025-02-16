package it.unisa.studenti.nc8.gametalk.business.validators.post.thread;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;

/**
 * Classe di validazione per oggetti {@link Thread}.
 */
public class ThreadValidator implements Validator<Thread> {

    /**
     * Regex per il titolo del thread.
     * Deve contenere solo lettere, numeri e alcuni caratteri speciali,
     * con una lunghezza compresa tra 1 e 150 caratteri.
     */
    public static final String REGEX_THREAD_TITLE = "^[A-Za-z0-9\\s\\-':,!?.()]"
            + "{1,32}$";

    /**
     * Regex per il corpo del thread.
     * Può contenere qualsiasi carattere, con una lunghezza
     * massima di 2000 caratteri.
     */
    public static final String REGEX_THREAD_BODY = "^.{1,2000}$";

    /**
     * Valida un oggetto {@link Thread}.
     *
     * @param thread L'oggetto {@link Thread} da validare.
     * @return {@code true} se l'oggetto è valido, {@code false} altrimenti.
     */
    @Override
    public boolean validate(final Thread thread) {
        if (thread == null) {
            return false;
        }

        return isTitleValid(thread.getTitle())
                && isBodyValid(thread.getBody());
    }

    /**
     * Verifica se il titolo del thread è valido.
     * Controlla che non sia nullo, vuoto e che rispetti la regex predefinita.
     *
     * @param title Il titolo del thread da validare.
     * @return {@code true} se il titolo è valido, {@code false} altrimenti.
     */
    private boolean isTitleValid(final String title) {
        return title != null
                && !title.isEmpty()
                && Functions.matchesRegex(REGEX_THREAD_TITLE, title);
    }

    /**
     * Verifica se il corpo del thread è valido.
     * Controlla che non sia nullo, vuoto e che rispetti la regex predefinita.
     *
     * @param body Il corpo del thread da validare.
     * @return {@code true} se il corpo è valido, {@code false} altrimenti.
     */
    private boolean isBodyValid(final String body) {
        return body != null
                && !body.isEmpty()
                && Functions.matchesRegex(REGEX_THREAD_BODY, body);
    }
}

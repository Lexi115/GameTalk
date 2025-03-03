package it.unisa.studenti.nc8.gametalk.business.validators.post.thread;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.Matcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;

/**
 * Classe di validazione per oggetti {@link Thread}.
 */
public class ThreadValidator implements Validator<Thread> {

    /**
     * Regex per il titolo del thread.
     * Deve contenere solo lettere, numeri e alcuni caratteri speciali,
     * con una lunghezza compresa tra 1 e 32 caratteri.
     */
    public static final String REGEX_THREAD_TITLE = "^[A-Za-z0-9\\s\\-':,!?.()]"
            + "{1,100}$";

    /**
     * Regex per il corpo del thread.
     * Può contenere qualsiasi carattere, con una lunghezza
     * massima di 2000 caratteri.
     */
    public static final String REGEX_THREAD_BODY = "^.{1,2000}$";

    /** Il matcher. */
    private final Matcher matcher;

    /**
     * Costruttore.
     *
     * @param matcher Il matcher.
     */
    public ThreadValidator(final Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Valida un oggetto {@link Thread}.
     *
     * @param thread L'oggetto {@link Thread} da validare.
     * @throws ValidationException se la validazione fallisce.
     */
    @Override
    public void validate(final Thread thread) throws ValidationException {
        if (!isTitleValid(thread.getTitle())) {
            throw new ValidationException("Titolo thread non valido!");
        }

        if (!isBodyValid(thread.getBody())) {
            throw new ValidationException("Corpo thread non valido!");
        }
    }

    /**
     * Verifica se il titolo del thread è valido.
     *
     * @param title Il titolo del thread da validare.
     * @return {@code true} se il titolo è valido, {@code false} altrimenti.
     */
    private boolean isTitleValid(final String title) {
        return title != null
                && !title.isEmpty()
                && matcher.matches(REGEX_THREAD_TITLE, title);
    }

    /**
     * Verifica se il corpo del thread è valido.
     *
     * @param body Il corpo del thread da validare.
     * @return {@code true} se il corpo è valido, {@code false} altrimenti.
     */
    private boolean isBodyValid(final String body) {
        return body != null
                && !body.isEmpty()
                && matcher.matches(REGEX_THREAD_BODY, body);
    }
}

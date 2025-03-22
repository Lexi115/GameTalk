package it.unisa.studenti.nc8.gametalk.business.validators.post.comment;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.Matcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;

/**
 * Classe di validazione per oggetti {@link Comment}.
 */
public class CommentValidator implements Validator<Comment> {

    /**
     * Regex per il corpo del commento.
     * Può contenere qualsiasi carattere, con una lunghezza
     * massima di 1000 caratteri.
     */
    public static final String REGEX_COMMENT_BODY = "^[\\s\\S]{1,1000}$";

    /** Il matcher. */
    private final Matcher matcher;

    /**
     * Costruttore.
     *
     * @param matcher Il matcher.
     */
    public CommentValidator(final Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Valida un oggetto {@link Comment}.
     *
     * @param comment L'oggetto {@link Comment} da validare.
     * @throws ValidationException se la validazione fallisce.
     */
    @Override
    public void validate(final Comment comment) {
        if (!isBodyValid(comment.getBody())) {
            throw new ValidationException("Corpo commento non valido!");
        }
    }

    /**
     * Verifica se il corpo del commento è valido.
     *
     * @param body Il corpo del commento da validare.
     * @return {@code true} se il corpo è valido, {@code false} altrimenti.
     */
    private boolean isBodyValid(final String body) {
        return body != null
                && !body.isEmpty()
                && matcher.matches(REGEX_COMMENT_BODY, body);
    }
}

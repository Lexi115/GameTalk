package it.unisa.studenti.nc8.gametalk.business.validators.post.comment;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.model.post.comment.Comment;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;

/**
 * Classe di validazione per oggetti {@link Comment}.
 * Contiene metodi per verificare la validità del contenuto del commento.
 */
public class CommentValidator implements Validator<Comment> {

    /**
     * Regex per il corpo del commento.
     * Può contenere qualsiasi carattere, con una lunghezza
     * massima di 1000 caratteri.
     */
    public static final String REGEX_COMMENT_BODY = "^.{1,1000}$";

    /**
     * Valida un oggetto {@link Comment}.
     *
     * @param comment L'oggetto {@link Comment} da validare.
     * @return {@code true} se il commento è valido, {@code false} altrimenti.
     */
    @Override
    public boolean validate(final Comment comment) {
        if (comment == null) {
            return false;
        }
        return isBodyValid(comment.getBody());
    }

    /**
     * Verifica se il corpo del commento è valido.
     * Controlla che non sia nullo, vuoto e che rispetti la regex predefinita.
     *
     * @param body Il corpo del commento da validare.
     * @return {@code true} se il corpo è valido, {@code false} altrimenti.
     */
    private boolean isBodyValid(final String body) {
        return body != null
                && !body.isEmpty()
                && Functions.matchesRegex(REGEX_COMMENT_BODY, body);
    }
}

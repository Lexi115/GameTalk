package it.unisa.studenti.nc8.gametalk.business.validators.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.Matcher;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;

import java.time.LocalDate;

/**
 * Classe di validazione per oggetti {@link User}.
 */
public class UserValidator implements Validator<User> {

    /**
     * Regex per il nome utente. Deve contenere solo lettere, numeri
     * e alcuni caratteri speciali.
     */
    public static final String REGEX_USER_NAME =
            "^[A-Za-z0-9\\s\\-':,!?.()]{4,24}$";

    /**
     * Regex per la password dell'utente.
     * Deve contenere almeno una lettera maiuscola, una minuscola, un numero
     * e un carattere speciale, con una lunghezza minima di 8 caratteri.
     */
    public static final String REGEX_USER_PASSWORD =
            "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%_*#?&])"
                    + "[A-Za-z\\d@$!%_*#?&]{8,100}$";

    /** Il matcher. */
    private final Matcher matcher;

    /**
     * Costruttore.
     *
     * @param matcher Il matcher.
     */
    public UserValidator(final Matcher matcher) {
        this.matcher = matcher;
    }

    /**
     * Valida un oggetto {@link User}.
     *
     * @param user L'oggetto {@link User} da validare.
     * @throws ValidationException se la validazione fallisce.
     */
    @Override
    public void validate(final User user) throws ValidationException {
        if (!isUsernameValid(user.getUsername())) {
            throw new ValidationException("Username non valido!");
        }

        if (!isPasswordValid(user.getPassword())) {
            throw new ValidationException("Password non valida!");
        }

        if (!isCreationDateValid(user.getCreationDate())) {
            throw new ValidationException("Data di creazione non valida!");
        }
    }

    /**
     * Verifica se il nome utente è valido.
     *
     * @param username Il nome utente da validare.
     * @return {@code true} se il nome utente è valido,
     * {@code false} altrimenti.
     */
    private boolean isUsernameValid(final String username) {
        return username != null
                && !username.isEmpty()
                && matcher.matches(REGEX_USER_NAME, username);
    }

    /**
     * Verifica se la password è valida.
     *
     * @param password La password da validare.
     * @return {@code true} se la password è valida, {@code false} altrimenti.
     */
    private boolean isPasswordValid(final String password) {
        return password != null
                && !password.isEmpty()
                && matcher.matches(REGEX_USER_PASSWORD, password);
    }

    /**
     * Verifica se la data di creazione è valida.
     *
     * @param date La data da validare.
     * @return {@code true} se la data di creazione è valida,
     * {@code false} altrimenti.
     */
    private boolean isCreationDateValid(final LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }
}

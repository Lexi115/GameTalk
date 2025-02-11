package it.unisa.studenti.nc8.gametalk.business.validators.user;

import it.unisa.studenti.nc8.gametalk.business.core.Functions;
import it.unisa.studenti.nc8.gametalk.business.models.user.User;
import it.unisa.studenti.nc8.gametalk.business.validators.Validator;

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

    /**
     * Valida un oggetto {@link User}.
     *
     * @param user L'oggetto {@link User} da validare.
     * @return {@code true} se l'oggetto è valido, {@code false} altrimenti.
     */
    @Override
    public boolean validate(final User user) {
        if (user == null) {
            return false;
        }

        return isUsernameValid(user.getUsername())
                && isPasswordValid(user.getPassword())
                && isCreationDateValid(user.getCreationDate());
    }

    /**
     * Verifica se il nome utente è valido.
     * Controlla che non sia nullo, vuoto e che rispetti la regex predefinita.
     *
     * @param username Il nome utente da validare.
     * @return {@code true} se il nome utente è valido,
     * {@code false} altrimenti.
     */
    private boolean isUsernameValid(final String username) {
        return username != null
                && !username.isEmpty()
                && Functions.matchesRegex(REGEX_USER_NAME, username);
    }

    /**
     * Verifica se la password è valida.
     * Controlla che non sia nulla, vuota e che rispetti la regex predefinita.
     *
     * @param password La password da validare.
     * @return {@code true} se la password è valida, {@code false} altrimenti.
     */
    private boolean isPasswordValid(final String password) {
        return password != null
                && !password.isEmpty()
                && Functions.matchesRegex(REGEX_USER_PASSWORD, password);
    }

    /**
     * Verifica se la data di creazione è valida.
     * Controlla che non sia successiva alla data corrente.
     *
     * @param date La data da validare.
     * @return {@code true} se la data di creazione è valida,
     * {@code false} altrimenti.
     */
    private boolean isCreationDateValid(final LocalDate date) {
        return date != null && !date.isAfter(LocalDate.now());
    }
}

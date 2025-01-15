package it.unisa.studenti.nc8.gametalk.business.validators;

/**
 * Interfaccia generica per la validazione di oggetti.
 *
 * @param <T> Il tipo di oggetto da validare.
 */
public interface Validator<T> {

    /**
     * Valida un oggetto del tipo specificato.
     *
     * @param t L'oggetto da validare.
     * @return {@code true} se l'oggetto è valido, {@code false} altrimenti.
     */
    boolean validate(T t);
}


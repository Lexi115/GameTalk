package it.unisa.studenti.nc8.gametalk.business.validators;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;

/**
 * Interfaccia generica per la validazione di oggetti.
 *
 * @param <T> Il tipo di oggetto da validare.
 */
public interface Validator<T> {

    /**
     * Valida un oggetto del tipo specificato.
     *
     * @param object                L'oggetto da validare.
     * @throws ValidationException  se la validazione fallisce.
     */
    void validate(T object) throws ValidationException;
}

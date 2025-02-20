package it.unisa.studenti.nc8.gametalk.storage.utils;

/**
 * Interfaccia per associare dinamicamente un oggetto
 * (ad esempio, una connessione al database) a un'istanza che ne ha bisogno.
 */
public interface Bindable {

    /**
     * Associa un oggetto all'istanza corrente.
     *
     * @param object l'oggetto da associare.
     */
    void bind(Object object);
}

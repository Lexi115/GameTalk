package it.unisa.studenti.nc8.gametalk.presentation.utils.json;

/**
 * Serializzatore JSON. Permette di trasformare
 * un oggetto in una stringa JSON e viceversa.
 */
public interface JsonSerializer {

    /**
     * Converti un oggetto in una stringa JSON.
     *
     * @param obj L'oggetto da convertire.
     * @return la stringa JSON corrispondente.
     */
    String to(Object obj);

    /**
     * Converti una stringa JSON in un oggetto.
     *
     * @param <T> il tipo dell'oggetto risultante.
     * @param json La stringa JSON da convertire.
     * @param clazz La classe dell'oggetto corrispondente.
     * @return l'oggetto corrispondente.
     */
    <T> T from(String json, Class<T> clazz);
}

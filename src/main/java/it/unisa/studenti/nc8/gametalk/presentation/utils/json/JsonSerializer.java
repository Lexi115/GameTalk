package it.unisa.studenti.nc8.gametalk.presentation.utils.json;

public interface JsonSerializer {

    /**
     * Serializza un oggetto in una stringa JSON.
     *
     * @param obj L'oggetto da serializzare.
     * @return la stringa JSON corrispondente.
     */
    String to(Object obj);

    <T> T from(String json, Class<T> clazz);
}

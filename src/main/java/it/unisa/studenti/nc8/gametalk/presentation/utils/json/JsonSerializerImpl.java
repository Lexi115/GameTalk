package it.unisa.studenti.nc8.gametalk.presentation.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.adapters.LocalDateAdapter;

import java.time.LocalDate;

/**
 * Serializzatore JSON. Permette di trasformare
 * un oggetto in una stringa JSON e viceversa.
 */
public class JsonSerializerImpl implements JsonSerializer {

    /** Oggetto per la serializzazione. */
    private final Gson gson;

    /**
     * Costruttore.
     */
    public JsonSerializerImpl() {
        gson = new GsonBuilder()
                // Adattatore per LocalDate
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    /**
     * Converti un oggetto in una stringa JSON.
     *
     * @param obj L'oggetto da convertire.
     * @return la stringa JSON corrispondente.
     */
    @Override
    public String to(final Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Converti una stringa JSON in un oggetto.
     *
     * @param <T> il tipo dell'oggetto risultante.
     * @param json La stringa JSON da convertire.
     * @param clazz La classe dell'oggetto corrispondente.
     * @return l'oggetto corrispondente.
     */
    @Override
    public <T> T from(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}

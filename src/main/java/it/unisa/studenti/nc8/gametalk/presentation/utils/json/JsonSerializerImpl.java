package it.unisa.studenti.nc8.gametalk.presentation.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unisa.studenti.nc8.gametalk.presentation.utils.json.adapters.LocalDateAdapter;

import java.time.LocalDate;

public class JsonSerializerImpl implements JsonSerializer {

    private final Gson gson;

    public JsonSerializerImpl() {
        gson = new GsonBuilder()
                // Adattatore per LocalDate
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    /**
     * Serializza un oggetto in una stringa JSON.
     *
     * @param obj L'oggetto da serializzare.
     * @return la stringa JSON corrispondente.
     */
    @Override
    public String to(final Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T from(final String json, final Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}

package it.unisa.studenti.nc8.gametalk.presentation.utils.json.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Adattatore per la corretta serializzazione e
 * deserializzazione di oggetti {@link LocalDate} in
 * formato JSON e viceversa.
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Trasforma l'oggetto {@link LocalDate} in una stringa JSON.
     * @param jsonWriter Il writer JSON.
     * @param localDate La data in questione.
     * @throws IOException in caso di errori.
     */
    @Override
    public void write(final JsonWriter jsonWriter, final LocalDate localDate)
            throws IOException {
        if (localDate == null) {
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDate.toString());
        }
    }

    /**
     * Trasforma la stringa JSON in un oggetto {@link LocalDate}.
     * @param jsonReader Il reader JSON.
     * @throws IOException in caso di errori.
     */
    @Override
    public LocalDate read(final JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        } else {
            return LocalDate.parse(jsonReader.nextString());
        }
    }
}

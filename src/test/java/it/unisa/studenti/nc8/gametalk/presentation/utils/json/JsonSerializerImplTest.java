package it.unisa.studenti.nc8.gametalk.presentation.utils.json;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonSerializerImplTest {

    private JsonSerializer jsonSerializer;

    @BeforeEach
    void setUp() {
        jsonSerializer = new JsonSerializerImpl();
    }

    @Test
    void testStringifyString() {
        String str = "Ciao a tutti";
        String result = jsonSerializer.stringify(str);
        assertEquals("\"Ciao a tutti\"", result);
    }

    @Test
    void testStringifyArray() {
        String[] array = {"primo", "secondo"};
        String result = jsonSerializer.stringify(array);
        assertEquals("[\"primo\",\"secondo\"]", result);
    }

    @Test
    void testStringifyObject() {
        JsonObject object = new JsonObject();
        object.addProperty("id", 1);
        object.addProperty("text", "Ciao a tutti");

        String result = jsonSerializer.stringify(object);
        assertEquals("{\"id\":1,\"text\":\"Ciao a tutti\"}", result);
    }

    @Test
    void testStringifyEmptyList() {
        List<String> list = new ArrayList<>();
        String result = jsonSerializer.stringify(list);
        assertEquals("[]", result);
    }

    @Test
    void testStringifyNull() {
        String result = jsonSerializer.stringify(null);
        assertEquals("null", result);
    }

    @Test
    void testParseString() {
        String json = "\"Ciao a tutti\"";
        String result = jsonSerializer.parse(json, String.class);
        assertEquals("Ciao a tutti", result);
    }

    @Test
    void testParseArray() {
        String json = "[\"primo\", \"secondo\"]";
        String[] result = jsonSerializer.parse(json, String[].class);
        assertArrayEquals(new String[]{"primo", "secondo"}, result);
    }

    @Test
    void testParseObject() {
        String json = "{\"id\":1, \"text\":\"Ciao a tutti\"}";
        JsonObject result = jsonSerializer.parse(json, JsonObject.class);
        assertNotNull(result);
        assertEquals(1, result.get("id").getAsInt());
        assertEquals("Ciao a tutti", result.get("text").getAsString());
    }

    @Test
    void testParseNull() {
        String json = "null";
        String result = jsonSerializer.parse(json, String.class);
        assertNull(result);
    }

    @Test
    void testParseInvalidJson() {
        String json = "{json errato}";
        assertThrows(com.google.gson.JsonSyntaxException.class, () ->
            jsonSerializer.parse(json, JsonObject.class)
        );
    }
}
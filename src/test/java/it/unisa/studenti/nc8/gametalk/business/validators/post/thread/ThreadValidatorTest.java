package it.unisa.studenti.nc8.gametalk.business.validators.post.thread;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.thread.Thread;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadValidatorTest {

    private ThreadValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ThreadValidator(new RegexMatcher());
    }

    /* TEST PER IL TITOLO */

    @Test
    void validateValidTitles() {
        String[] validTitles = {
                "Valid Title",
                "Titolo con numeri 123",
                "Caratteri speciali: -',!?.()",
                "A".repeat(32), // Lunghezza massima
                "A" // Lunghezza minima
        };
        for (String validTitle : validTitles) {
            Thread thread = new Thread();
            thread.setTitle(validTitle);
            thread.setBody("Valid body");

            assertDoesNotThrow(() -> validator.validate(thread));
        }
    }

    @Test
    void validateInvalidTitles() {
        String[] invalidTitles = {
                "Titolo#NonValido",       // Carattere non consentito
                "Titolo@NonValido",       // Carattere non consentito
                "Titolo$NonValido",       // Carattere non consentito
                "Titolo%NonValido",       // Carattere non consentito
                "Titolo&NonValido",       // Carattere non consentito
                "Titolo[NonValido]",      // Carattere non consentito
                "Titolo{NonValido}",      // Carattere non consentito
                "A".repeat(101),     // Supera lunghezza massima
        };
        for (String invalidTitle : invalidTitles) {
            Thread thread = new Thread();
            thread.setTitle(invalidTitle);
            thread.setBody("Valid body");

            assertThrows(ValidationException.class, () -> validator.validate(thread));
        }
    }

    @Test
    void validateInvalidTitleCharacters() {
        Thread thread = new Thread();
        thread.setTitle("Carattere#NonValido");
        thread.setBody("Valid body");

        assertThrows(ValidationException.class, () -> validator.validate(thread));
    }

    /* TEST PER IL CORPO */

    @Test
    void validateValidBodies() {
        String[] validBodies = {
                "Valid body",
                "Corpo con numeri 123!@£$%^&*()",
                "A".repeat(2000), // Lunghezza massima
                "A" //Lunghezza minima
        };

        for (String validBody : validBodies) {
            Thread thread = new Thread();
            thread.setTitle("Valid Title");
            thread.setBody(validBody);

            assertDoesNotThrow(() -> validator.validate(thread));
        }
    }

    @Test
    void validateBodyTooLong() {
        Thread thread = new Thread();
        thread.setTitle("Valid Title");
        thread.setBody("A".repeat(2001)); // Supera la lunghezza massima

        assertThrows(ValidationException.class, () -> validator.validate(thread));
    }
}
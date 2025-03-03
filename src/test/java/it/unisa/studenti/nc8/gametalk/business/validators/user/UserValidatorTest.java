package it.unisa.studenti.nc8.gametalk.business.validators.user;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.storage.entities.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    private UserValidator validator;
    private User validUser;

    @BeforeEach
    void setUp() {
        validator = new UserValidator(new RegexMatcher());
        validUser = new User();
        validUser.setUsername("ValidUser123");
        validUser.setPassword("ValidPass123!");
        validUser.setCreationDate(LocalDate.now());
    }

    /* TEST GENERALE */

    @Test
    void validateValidUser() {
        assertDoesNotThrow(() -> validator.validate(validUser));
    }

    /* TEST USERNAME */

    @Test
    void validateValidUsernames() {
        String[] validUsernames = {
                "Test-User",            // Carattere speciale
                "User_123",             // Carattere speciale
                "A".repeat(24),   // Lunghezza massima
                "User"                  //Lunghezza minima
        };

        for (String username : validUsernames) {
            validUser.setUsername(username);
            assertDoesNotThrow(() -> validator.validate(validUser));
        }

    }

    @Test
    void validateInvalidUsernames() {
        String[] invalidUsernames = {
                "   ",                    // Vuoto
                "Usr",                    // Troppo corto (3 caratteri)
                "A".repeat(25),     // Troppo lungo
                "Invalid#User",           // Carattere speciale non consentito
        };

        for (String username : invalidUsernames) {
            validUser.setUsername(username);
            assertThrows(ValidationException.class, () -> validator.validate(validUser));
        }
    }

    /* TEST PASSWORD */

    @Test
    void validateValidPasswords() {
        String[] validPasswords = {
                "Pass123!",         // Base case
                "A@b1cdef",         // Minima lunghezza
                "A1!a".repeat(25)   // Lunghezza massima
        };

        for (String password : validPasswords) {
        validUser.setPassword(password);
        assertDoesNotThrow(() -> validator.validate(validUser));
    }
    }

    @Test
    void validateInvalidPasswords() {
        String[] InvalidPasswords = {
                "pass123!",         // Manca maiuscola
                "PASS123!",         // Manca minuscola
                "Password!",        // Manca numero
                "Pass1234",         // Manca carattere speciale
                "A1!a",             // Troppo corto (7 caratteri)
                "A1!a".repeat(26)   // Troppo lungo (104 caratteri)
        };

        for (String password : InvalidPasswords) {
            validUser.setPassword(password);
            assertThrows(ValidationException.class, () -> validator.validate(validUser));
        }
    }

    /* TEST CREATION DATE */

    @Test
    void validateInvalidFutureDate() {
        validUser.setCreationDate(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> validator.validate(validUser));
    }

    @Test
    void validateInvalidNullDate() {
        validUser.setCreationDate(null);
        assertThrows(ValidationException.class, () -> validator.validate(validUser));
    }
}
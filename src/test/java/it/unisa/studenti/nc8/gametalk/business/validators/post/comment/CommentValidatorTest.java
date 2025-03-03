package it.unisa.studenti.nc8.gametalk.business.validators.post.comment;

import it.unisa.studenti.nc8.gametalk.business.exceptions.ValidationException;
import it.unisa.studenti.nc8.gametalk.business.utils.pattern.RegexMatcher;
import it.unisa.studenti.nc8.gametalk.storage.entities.post.comment.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentValidatorTest {
    private CommentValidator commentValidator;

    @BeforeEach
    void setUp() {
        // Usa l'implementazione recale invece del mock
        commentValidator = new CommentValidator(new RegexMatcher());
    }

    @Test
    void validateValidBodies() {
        String[] strings = {
                "a",
                "Testo valido con spazi",
                "Testo con numeri 123",
                "Testo con caratteri speciali: !@#$%^&*()",
                "A".repeat(1000) // Caso limite massimo
        };

        for (String validBody : strings) {

        Comment comment = new Comment();
        comment.setBody(validBody);

        assertDoesNotThrow(() -> commentValidator.validate(comment));
        }
    }

    @Test
    void validateInvalidBody() {
            Comment comment = new Comment();
            comment.setBody("A".repeat(1001));

            assertThrows(ValidationException.class, () -> commentValidator.validate(comment));
    }

    @Test
    void validateEdgeCases() {
        // Test esplicito per body nullo
        assertThrows(ValidationException.class, () -> {
            Comment comment = new Comment();
            comment.setBody(null);
            commentValidator.validate(comment);
        });

        // Test esplicito per lunghezza massima
        assertDoesNotThrow(() -> {
            Comment comment = new Comment();
            comment.setBody("A".repeat(1000));
            commentValidator.validate(comment);
        });

        // Test esplicito per superamento lunghezza
        assertThrows(ValidationException.class, () -> {
            Comment comment = new Comment();
            comment.setBody("A".repeat(1001));
            commentValidator.validate(comment);
        });
    }
}
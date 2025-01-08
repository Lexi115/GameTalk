package it.unisa.studenti.nc8.gametalk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MyTest {
    private static int multiplier;

    @BeforeAll
    public static void setUp() {
        multiplier = 2;
    }

    @Test
    public void multiply2() {
        Assertions.assertEquals(4, 2 * multiplier);
    }

    @Test
    public void multiply3() {
        Assertions.assertEquals(6, 3 * multiplier);
    }
}

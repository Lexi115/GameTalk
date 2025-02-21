import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test() {
        LOGGER.info("my info");
        LOGGER.error("my error");
        LOGGER.warn("my warn");
    }
}

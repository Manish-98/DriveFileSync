package utils;

import exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class UtilitiesTest {

    @Test
    void shouldReturnTheContentsOfFileAsInputStream() throws IOException {
        InputStream stream = Utilities.getResourceAsStream("/credentials.json");
        assert stream != null;
        String result = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        assertEquals("{\n  \"key\": \"value\"\n}\n", result);
    }

    @Test
    void shouldReturnNullIfResourceNotFound() {
        InputStream stream = Utilities.getResourceAsStream("/credentials_temp.json");
        assertNull(stream);
    }
}

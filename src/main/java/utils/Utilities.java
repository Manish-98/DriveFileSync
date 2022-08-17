package utils;

import java.io.InputStream;

public class Utilities {
    public static InputStream getResourceAsStream(String resourcePath) {
        return Utilities.class.getResourceAsStream(resourcePath);
    }
}

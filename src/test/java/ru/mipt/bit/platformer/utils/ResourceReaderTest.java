package ru.mipt.bit.platformer.utils;

import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.util.ResourceReader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceReaderTest {

    @Test
    void readsUtf8Resource() {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public java.io.InputStream getResourceAsStream(String name) {
                return new ByteArrayInputStream("карта".getBytes(StandardCharsets.UTF_8));
            }
        };

        ResourceReader reader = new ResourceReader(
                classLoader,
                StandardCharsets.UTF_8
        );

        assertEquals("карта", reader.read("maps/default.txt"));
    }

    @Test
    void reportsMissingResource() {
        ResourceReader reader = new ResourceReader(
                new ClassLoader() {
                },
                StandardCharsets.UTF_8
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> reader.read("missing.txt")
        );
    }
}

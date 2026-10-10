package ru.mipt.bit.platformer.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Reads text resources available on the application's classpath.
 */
public final class ResourceReader {

    private final ClassLoader classLoader;
    private final Charset charset;

    public ResourceReader() {
        this(ResourceReader.class.getClassLoader(), StandardCharsets.UTF_8);
    }

    public ResourceReader(ClassLoader classLoader, Charset charset) {
        this.classLoader = Objects.requireNonNull(classLoader, "classLoader");
        this.charset = Objects.requireNonNull(charset, "charset");
    }

    public String read(String resourceName) {
        Objects.requireNonNull(resourceName, "resourceName");

        try (InputStream resource = classLoader.getResourceAsStream(resourceName)) {
            if (resource == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            return new String(resource.readAllBytes(), charset);
        } catch (IOException exception) {
            throw new UncheckedIOException(
                    "Could not read resource: " + resourceName,
                    exception
            );
        }
    }
}

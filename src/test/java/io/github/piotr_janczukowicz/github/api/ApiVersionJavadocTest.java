package io.github.piotr_janczukowicz.github.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class ApiVersionJavadocTest {

    private static final Pattern VERSION = Pattern.compile("API version \\{@code ([0-9]{4}-[0-9]{2}-[0-9]{2})}");

    @Test
    void javadoc_shouldNameTheClientApiVersion_whenResourceMentionsAVersion() throws IOException {
        final List<String> mismatches;
        try (Stream<Path> files = Files.walk(Path.of("src/main/java"))) {
            mismatches = files.filter(path -> path.toString().endsWith(".java"))
                    .flatMap(ApiVersionJavadocTest::mismatchedVersions)
                    .toList();
        }

        assertThat(mismatches)
                .as("Javadoc API version differs from GitHubClient.API_VERSION")
                .isEmpty();
    }

    private static Stream<String> mismatchedVersions(final Path path) {
        try {
            final Matcher matcher = VERSION.matcher(Files.readString(path, StandardCharsets.UTF_8));
            final Stream.Builder<String> found = Stream.builder();
            while (matcher.find()) {
                if (!GitHubClient.API_VERSION.equals(matcher.group(1))) {
                    found.add(path + " names " + matcher.group(1));
                }
            }
            return found.build();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

package io.github.piotr_janczukowicz.github.api;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

@UnitTest
class ApiJavadocPathsTest {

    private static final Pattern TEMPLATE = Pattern.compile("PathTemplate\\.of\\(\"([^\"]+)\"\\)");
    private static final Pattern DOCUMENTED = Pattern.compile("\\{@code (?:GET|POST|PUT|PATCH|DELETE|HEAD) (/[^\\s]*)");

    @Test
    void javadoc_shouldNameAPathTemplateOfTheSameClass_whenItDocumentsAnEndpoint() throws IOException {
        final List<String> mismatches;
        try (Stream<Path> files = Files.walk(Path.of("src/main/java/io/github/piotr_janczukowicz/github/api"))) {
            mismatches = files.filter(path -> path.toString().endsWith("Api.java"))
                    .flatMap(ApiJavadocPathsTest::undeclaredPaths)
                    .toList();
        }

        assertThat(mismatches)
                .as("Javadoc path is not a PathTemplate of the class that documents it")
                .isEmpty();
    }

    private static Stream<String> undeclaredPaths(final Path path) {
        try {
            final String source = Files.readString(path, StandardCharsets.UTF_8);
            final Set<String> declared = new HashSet<>();
            final Matcher templates = TEMPLATE.matcher(source);
            while (templates.find()) {
                declared.add(normalized(templates.group(1)));
            }
            final Stream.Builder<String> found = Stream.builder();
            final Matcher documented = DOCUMENTED.matcher(source);
            while (documented.find()) {
                final String documentedPath = normalized(withoutClosingBrace(documented.group(1)));
                if (!declared.contains(documentedPath)) {
                    found.add(path.getFileName() + " documents " + documentedPath);
                }
            }
            return found.build();
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static String withoutClosingBrace(final String token) {
        int depth = 0;
        for (int i = 0; i < token.length(); i++) {
            final char symbol = token.charAt(i);
            if (symbol == '{') {
                depth++;
            } else if (symbol == '}') {
                if (depth == 0) {
                    return token.substring(0, i);
                }
                depth--;
            }
        }
        return token;
    }

    private static String normalized(final String path) {
        return path.replace("{+", "{");
    }
}

package io.github.piotr_janczukowicz.github.api.packages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubPackageTest {

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    @Test
    void constructor_shouldAllowNullOwnerAndRepository_whenAbsent() {
        final Instant timestamp = Instant.parse("2020-05-19T22:19:11Z");

        final GitHubPackage pkg = new GitHubPackage(
                1L,
                "pkg",
                GitHubPackageType.NPM,
                GitHubPackageVisibility.PUBLIC,
                "https://example.com/url",
                "https://example.com/html",
                0,
                null,
                null,
                timestamp,
                timestamp);

        assertSoftly(softly -> {
            softly.assertThat(pkg.owner()).isNull();
            softly.assertThat(pkg.repository()).isNull();
        });
    }

    private static Stream<Arguments> invalidConstructors() {
        final Instant timestamp = Instant.parse("2020-05-19T22:19:11Z");
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                null,
                                GitHubPackageType.NPM,
                                GitHubPackageVisibility.PUBLIC,
                                "url",
                                "html",
                                0,
                                null,
                                null,
                                timestamp,
                                timestamp),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                null,
                                GitHubPackageVisibility.PUBLIC,
                                "url",
                                "html",
                                0,
                                null,
                                null,
                                timestamp,
                                timestamp),
                        "packageType"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                GitHubPackageType.NPM,
                                null,
                                "url",
                                "html",
                                0,
                                null,
                                null,
                                timestamp,
                                timestamp),
                        "visibility"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                GitHubPackageType.NPM,
                                GitHubPackageVisibility.PUBLIC,
                                null,
                                "html",
                                0,
                                null,
                                null,
                                timestamp,
                                timestamp),
                        "url"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                GitHubPackageType.NPM,
                                GitHubPackageVisibility.PUBLIC,
                                "url",
                                null,
                                0,
                                null,
                                null,
                                timestamp,
                                timestamp),
                        "htmlUrl"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                GitHubPackageType.NPM,
                                GitHubPackageVisibility.PUBLIC,
                                "url",
                                "html",
                                0,
                                null,
                                null,
                                null,
                                timestamp),
                        "createdAt"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackage(
                                1L,
                                "pkg",
                                GitHubPackageType.NPM,
                                GitHubPackageVisibility.PUBLIC,
                                "url",
                                "html",
                                0,
                                null,
                                null,
                                timestamp,
                                null),
                        "updatedAt"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

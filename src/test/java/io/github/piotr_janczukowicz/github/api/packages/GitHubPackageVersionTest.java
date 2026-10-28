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
class GitHubPackageVersionTest {

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenRequiredFieldNull(final ConstructorCall call, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining(message);
    }

    @Test
    void constructor_shouldAllowNullableFields_whenAbsent() {
        final Instant timestamp = Instant.parse("2020-05-19T22:19:11Z");

        final GitHubPackageVersion version = new GitHubPackageVersion(
                1L, "v1", "url", "packageHtml", null, null, null, timestamp, timestamp, null, null);

        assertSoftly(softly -> {
            softly.assertThat(version.htmlUrl()).isNull();
            softly.assertThat(version.license()).isNull();
            softly.assertThat(version.description()).isNull();
            softly.assertThat(version.deletedAt()).isNull();
            softly.assertThat(version.metadata()).isNull();
        });
    }

    private static Stream<Arguments> invalidConstructors() {
        final Instant timestamp = Instant.parse("2020-05-19T22:19:11Z");
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackageVersion(
                                1L, null, "url", "packageHtml", null, null, null, timestamp, timestamp, null, null),
                        "name"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackageVersion(
                                1L, "v1", null, "packageHtml", null, null, null, timestamp, timestamp, null, null),
                        "url"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackageVersion(
                                1L, "v1", "url", null, null, null, null, timestamp, timestamp, null, null),
                        "packageHtmlUrl"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackageVersion(
                                1L, "v1", "url", "packageHtml", null, null, null, null, timestamp, null, null),
                        "createdAt"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubPackageVersion(
                                1L, "v1", "url", "packageHtml", null, null, null, timestamp, null, null, null),
                        "updatedAt"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}

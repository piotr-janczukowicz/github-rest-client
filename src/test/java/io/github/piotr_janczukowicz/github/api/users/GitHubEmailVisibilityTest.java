package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubEmailVisibilityTest {

    @ParameterizedTest
    @CsvSource({"public,PUBLIC", "private,PRIVATE"})
    void fromWire_shouldReturnType_whenKnownValue(final String wire, final GitHubEmailVisibility visibility) {
        final GitHubEmailVisibility decoded = GitHubEmailVisibility.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(visibility);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubEmailVisibility.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("hidden", IllegalArgumentException.class, "hidden"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}

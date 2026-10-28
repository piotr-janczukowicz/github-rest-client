package io.github.piotr_janczukowicz.github.api.migrations;

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
class GitHubImportUseLfsTest {

    @ParameterizedTest
    @CsvSource({"opt_in,OPT_IN", "opt_out,OPT_OUT"})
    void fromWire_shouldReturnPreference_whenKnownValue(final String wire, final GitHubImportUseLfs useLfs) {
        final GitHubImportUseLfs decoded = GitHubImportUseLfs.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(useLfs);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubImportUseLfs.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("always", IllegalArgumentException.class, "always"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}

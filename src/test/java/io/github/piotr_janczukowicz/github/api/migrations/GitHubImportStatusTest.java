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
class GitHubImportStatusTest {

    @ParameterizedTest
    @CsvSource({
        "auth,AUTH",
        "error,ERROR",
        "none,NONE",
        "detecting,DETECTING",
        "choose,CHOOSE",
        "auth_failed,AUTH_FAILED",
        "importing,IMPORTING",
        "mapping,MAPPING",
        "waiting_to_push,WAITING_TO_PUSH",
        "pushing,PUSHING",
        "complete,COMPLETE",
        "setup,SETUP",
        "unknown,UNKNOWN",
        "detection_found_multiple,DETECTION_FOUND_MULTIPLE",
        "detection_found_nothing,DETECTION_FOUND_NOTHING",
        "detection_needs_auth,DETECTION_NEEDS_AUTH"
    })
    void fromWire_shouldReturnStatus_whenKnownValue(final String wire, final GitHubImportStatus status) {
        final GitHubImportStatus decoded = GitHubImportStatus.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(status);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubImportStatus.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("invalid", IllegalArgumentException.class, "invalid"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}

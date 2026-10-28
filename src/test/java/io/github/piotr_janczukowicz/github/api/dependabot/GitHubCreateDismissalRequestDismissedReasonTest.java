package io.github.piotr_janczukowicz.github.api.dependabot;

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
class GitHubCreateDismissalRequestDismissedReasonTest {

    @ParameterizedTest
    @CsvSource({
        "fix_started,FIX_STARTED",
        "no_bandwidth,NO_BANDWIDTH",
        "tolerable_risk,TOLERABLE_RISK",
        "inaccurate,INACCURATE",
        "not_used,NOT_USED"
    })
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubCreateDismissalRequestDismissedReason value) {
        final GitHubCreateDismissalRequestDismissedReason decoded =
                GitHubCreateDismissalRequestDismissedReason.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubCreateDismissalRequestDismissedReason.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("not-a-value", IllegalArgumentException.class, "Unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}

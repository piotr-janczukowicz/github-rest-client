package io.github.piotr_janczukowicz.github.api.repositories;

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
class GitHubRepositoryRuleCodeScanningSecurityAlertsThresholdTest {

    @ParameterizedTest
    @CsvSource({
        "none,NONE",
        "critical,CRITICAL",
        "high_or_higher,HIGH_OR_HIGHER",
        "medium_or_higher,MEDIUM_OR_HIGHER",
        "all,ALL"
    })
    void fromWire_shouldReturnType_whenKnownValue(
            final String wire, final GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold value) {
        final GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold decoded =
                GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown =
                catchThrowable(() -> GitHubRepositoryRuleCodeScanningSecurityAlertsThreshold.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("errors", IllegalArgumentException.class, "errors"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}

package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubCodeScanningAlertRuleSummarySecuritySeverityLevelTest {

    @ParameterizedTest
    @CsvSource({"low,LOW", "medium,MEDIUM", "high,HIGH", "critical,CRITICAL"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel value) {
        final GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel decoded =
                GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.fromWire(wire);

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
                catchThrowable(() -> GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(Arguments.of(null, NullPointerException.class, "value"));
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel decoded =
                GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubCodeScanningAlertRuleSummarySecuritySeverityLevel.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

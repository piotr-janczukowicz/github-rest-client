package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubUpdatePublicMonitoringAlertForEnterpriseResolutionTest {

    @ParameterizedTest
    @CsvSource({"dismissed,DISMISSED", "fixed,FIXED"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubUpdatePublicMonitoringAlertForEnterpriseResolution value) {
        final GitHubUpdatePublicMonitoringAlertForEnterpriseResolution decoded =
                GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.fromWire("other"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("other");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdatePublicMonitoringAlertForEnterpriseResolution.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

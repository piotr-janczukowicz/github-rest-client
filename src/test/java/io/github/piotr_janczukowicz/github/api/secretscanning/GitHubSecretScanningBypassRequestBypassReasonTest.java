package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubSecretScanningBypassRequestBypassReasonTest {

    @ParameterizedTest
    @CsvSource({"used_in_tests,USED_IN_TESTS", "false_positive,FALSE_POSITIVE", "fix_later,FIX_LATER"})
    void fromWire_shouldReturnValue_whenKnownValue(
            final String wire, final GitHubSecretScanningBypassRequestBypassReason value) {
        final GitHubSecretScanningBypassRequestBypassReason decoded =
                GitHubSecretScanningBypassRequestBypassReason.fromWire(wire);

        assertThat(decoded).isEqualTo(value);
        assertThat(decoded.wireValue()).isEqualTo(wire);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSecretScanningBypassRequestBypassReason.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubSecretScanningBypassRequestBypassReason.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}

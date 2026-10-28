package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubAutomationMinuteUtcTest {

    @ParameterizedTest
    @CsvSource({"0,ZERO", "15,FIFTEEN", "30,THIRTY", "45,FORTY_FIVE"})
    void fromWire_shouldReturnValue_whenKnownValue(final int wire, final GitHubAutomationMinuteUtc value) {
        final GitHubAutomationMinuteUtc decoded = GitHubAutomationMinuteUtc.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubAutomationMinuteUtc.fromWire(7));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("7");
    }
}

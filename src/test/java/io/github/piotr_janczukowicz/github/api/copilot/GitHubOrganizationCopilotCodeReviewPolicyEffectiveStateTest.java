package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubOrganizationCopilotCodeReviewPolicyEffectiveStateTest {

    @ParameterizedTest
    @CsvSource({"enabled,ENABLED", "disabled,DISABLED"})
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubOrganizationCopilotCodeReviewPolicyEffectiveState value) {
        final GitHubOrganizationCopilotCodeReviewPolicyEffectiveState decoded =
                GitHubOrganizationCopilotCodeReviewPolicyEffectiveState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubOrganizationCopilotCodeReviewPolicyEffectiveState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubOrganizationCopilotCodeReviewPolicyEffectiveState decoded =
                GitHubOrganizationCopilotCodeReviewPolicyEffectiveState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubOrganizationCopilotCodeReviewPolicyEffectiveState.UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}

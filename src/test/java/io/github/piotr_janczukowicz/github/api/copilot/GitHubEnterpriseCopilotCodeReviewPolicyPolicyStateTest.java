package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubEnterpriseCopilotCodeReviewPolicyPolicyStateTest {

    @ParameterizedTest
    @CsvSource({
        "enabled_for_all_organizations,ENABLED_FOR_ALL_ORGANIZATIONS",
        "disabled_for_all_organizations,DISABLED_FOR_ALL_ORGANIZATIONS",
        "configured_by_organization_admins,CONFIGURED_BY_ORGANIZATION_ADMINS"
    })
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubEnterpriseCopilotCodeReviewPolicyPolicyState value) {
        final GitHubEnterpriseCopilotCodeReviewPolicyPolicyState decoded =
                GitHubEnterpriseCopilotCodeReviewPolicyPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubEnterpriseCopilotCodeReviewPolicyPolicyState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseCopilotCodeReviewPolicyPolicyState decoded =
                GitHubEnterpriseCopilotCodeReviewPolicyPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseCopilotCodeReviewPolicyPolicyState.UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}

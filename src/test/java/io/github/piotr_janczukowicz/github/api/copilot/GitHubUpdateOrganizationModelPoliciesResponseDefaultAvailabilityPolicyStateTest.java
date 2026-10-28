package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState decoded =
                GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

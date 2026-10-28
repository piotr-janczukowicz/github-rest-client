package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState decoded =
                GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(
                        "delegate_to_organizations"))
                .isEqualTo(
                        GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState
                                .DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState decoded =
                GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

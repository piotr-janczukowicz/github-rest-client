package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(
                        "delegate_to_organizations"))
                .isEqualTo(
                        GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState
                                .DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState decoded =
                GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

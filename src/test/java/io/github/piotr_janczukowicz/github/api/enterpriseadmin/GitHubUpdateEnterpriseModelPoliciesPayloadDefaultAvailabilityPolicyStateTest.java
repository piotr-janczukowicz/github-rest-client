package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.fromWire(
                        "delegate_to_organizations"))
                .isEqualTo(
                        GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState
                                .DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

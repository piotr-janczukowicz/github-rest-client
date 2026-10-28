package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesResponseModelPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.fromWire("not-a-known-value"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseModelPolicyState.UNKNOWN);
    }
}

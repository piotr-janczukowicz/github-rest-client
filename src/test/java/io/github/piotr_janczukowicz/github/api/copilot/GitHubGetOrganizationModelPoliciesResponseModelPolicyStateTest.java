package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrganizationModelPoliciesResponseModelPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        assertThat(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.fromWire("not-a-known-value"))
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseModelPolicyState.UNKNOWN);
    }
}

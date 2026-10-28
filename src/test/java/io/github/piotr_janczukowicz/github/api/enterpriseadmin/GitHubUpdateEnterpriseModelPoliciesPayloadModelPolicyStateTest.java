package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.fromWire("delegate_to_teams_and_apps"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.fromWire("delegate_to_organizations"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

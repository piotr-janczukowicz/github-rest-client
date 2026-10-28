package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_teams_and_apps"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_organizations"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseModelPoliciesResponsePolicyState decoded =
                GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

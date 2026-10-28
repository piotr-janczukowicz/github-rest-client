package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_teams_and_apps"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToOrganizations_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_organizations"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_ORGANIZATIONS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseModelPoliciesResponsePolicyState decoded =
                GitHubGetEnterpriseModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGetEnterpriseModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire(
                        "delegate_to_teams_and_apps"))
                .isEqualTo(
                        GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState
                                .DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState decoded =
                GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

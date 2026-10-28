package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire(
                        "delegate_to_teams_and_apps"))
                .isEqualTo(
                        GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState
                                .DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState decoded =
                GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

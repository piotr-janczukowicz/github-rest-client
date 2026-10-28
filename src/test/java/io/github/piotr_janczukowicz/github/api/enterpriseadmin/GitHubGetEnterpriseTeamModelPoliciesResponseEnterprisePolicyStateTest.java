package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire(
                        "delegate_to_teams_and_apps"))
                .isEqualTo(
                        GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState decoded =
                GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

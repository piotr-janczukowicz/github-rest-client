package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnDelegateToTeamsAndApps_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire(
                        "delegate_to_teams_and_apps"))
                .isEqualTo(
                        GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_TEAMS_AND_APPS);
    }

    @Test
    void fromWire_shouldReturnDelegateToDefault_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("delegate_to_default"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.DELEGATE_TO_DEFAULT);
    }

    @Test
    void fromWire_shouldReturnUnconfigured_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire("unconfigured"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.UNCONFIGURED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState decoded =
                GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

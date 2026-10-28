package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("optional"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState decoded =
                GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

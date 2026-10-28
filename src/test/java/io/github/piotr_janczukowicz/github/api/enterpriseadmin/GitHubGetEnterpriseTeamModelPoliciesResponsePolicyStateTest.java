package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseTeamModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.fromWire("optional"))
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState decoded =
                GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

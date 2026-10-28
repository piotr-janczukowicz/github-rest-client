package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("optional"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState decoded =
                GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

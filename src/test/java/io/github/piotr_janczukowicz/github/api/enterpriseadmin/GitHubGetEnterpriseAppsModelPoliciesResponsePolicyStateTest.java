package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseAppsModelPoliciesResponsePolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("enabled"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("disabled"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.fromWire("optional"))
                .isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState decoded =
                GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}

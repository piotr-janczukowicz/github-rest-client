package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseAppsModelPoliciesPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.fromWire("optional"))
                .isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

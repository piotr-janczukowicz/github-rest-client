package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamModelPoliciesPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldReturnOptional_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.fromWire("optional"))
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.OPTIONAL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

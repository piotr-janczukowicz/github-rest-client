package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesModelPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesModelPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesModelPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesModelPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesModelPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubUpdateOrganizationModelPoliciesModelPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

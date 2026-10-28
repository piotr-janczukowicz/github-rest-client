package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyStateTest {

    @Test
    void fromWire_shouldReturnEnabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.fromWire("enabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.ENABLED);
    }

    @Test
    void fromWire_shouldReturnDisabled_whenKnownValue() {
        assertThat(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.fromWire("disabled"))
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(
                () -> GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}

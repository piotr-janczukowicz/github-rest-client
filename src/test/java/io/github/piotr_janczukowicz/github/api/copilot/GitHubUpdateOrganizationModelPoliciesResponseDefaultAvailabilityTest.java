package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailability value =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesResponseDefaultAvailability();

        assertThat(value.policyState())
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
        assertThat(value.editable()).isTrue();
    }
}

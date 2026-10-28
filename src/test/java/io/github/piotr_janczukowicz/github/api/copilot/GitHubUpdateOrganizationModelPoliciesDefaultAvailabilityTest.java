package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubUpdateOrganizationModelPoliciesDefaultAvailability value =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesDefaultAvailability();

        assertThat(value.policyState())
                .isEqualTo(GitHubUpdateOrganizationModelPoliciesDefaultAvailabilityPolicyState.ENABLED);
    }
}

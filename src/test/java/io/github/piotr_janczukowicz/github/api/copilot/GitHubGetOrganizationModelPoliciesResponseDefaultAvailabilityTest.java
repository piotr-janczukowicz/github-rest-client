package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetOrganizationModelPoliciesResponseDefaultAvailability value =
                CopilotCatalogFixtures.getOrganizationModelPoliciesResponseDefaultAvailability();

        assertThat(value.policyState())
                .isEqualTo(GitHubGetOrganizationModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED);
        assertThat(value.editable()).isTrue();
    }
}

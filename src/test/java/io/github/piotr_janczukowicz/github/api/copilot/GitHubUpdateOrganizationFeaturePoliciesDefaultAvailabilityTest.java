package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationFeaturePoliciesDefaultAvailabilityTest {

    @Test
    void constructor_shouldThrow_whenPolicyStateNull() {
        final GitHubUpdateOrganizationFeaturePoliciesDefaultAvailabilityBuilder builder =
                GitHubUpdateOrganizationFeaturePoliciesDefaultAvailabilityBuilder.builder(
                                CopilotCatalogFixtures.updateOrganizationFeaturePoliciesDefaultAvailability())
                        .policyState(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("policyState");
    }
}

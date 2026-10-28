package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubUpdateOrganizationModelPoliciesResponse value =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesResponse();

        assertThat(value.defaultAvailability()).isNotNull();
        assertThat(value.models()).isNotEmpty();
    }
}

package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesModelTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubUpdateOrganizationModelPoliciesModel value =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.policyState()).isEqualTo(GitHubUpdateOrganizationModelPoliciesModelPolicyState.ENABLED);
    }
}

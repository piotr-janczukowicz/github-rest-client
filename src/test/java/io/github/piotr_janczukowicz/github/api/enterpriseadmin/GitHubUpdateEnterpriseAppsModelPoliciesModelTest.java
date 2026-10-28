package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseAppsModelPoliciesModelTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseAppsModelPoliciesModel value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.policyState()).isEqualTo(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.ENABLED);
    }

    @Test
    void constructor_shouldRejectModelName_whenInvalid() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseAppsModelPoliciesModelBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesModel())
                .modelName("")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

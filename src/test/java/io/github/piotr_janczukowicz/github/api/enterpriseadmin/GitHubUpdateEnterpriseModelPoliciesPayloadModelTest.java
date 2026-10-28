package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesPayloadModelTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseModelPoliciesPayloadModel value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesPayloadModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.policyState()).isEqualTo(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.ENABLED);
    }

    @Test
    void constructor_shouldRejectModelName_whenInvalid() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseModelPoliciesPayloadModelBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesPayloadModel())
                .modelName("")
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

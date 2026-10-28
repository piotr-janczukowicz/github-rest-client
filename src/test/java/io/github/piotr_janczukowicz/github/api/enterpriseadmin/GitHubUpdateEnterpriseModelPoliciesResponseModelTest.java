package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesResponseModelTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseModelPoliciesResponseModel value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesResponseModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.displayName()).isEqualTo("sample-display_name");
        assertThat(value.policyState()).isEqualTo(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.ENABLED);
    }
}

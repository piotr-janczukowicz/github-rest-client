package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamModelPoliciesResponseModelTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseTeamModelPoliciesResponseModel value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseTeamModelPoliciesResponseModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.displayName()).isEqualTo("sample-display_name");
        assertThat(value.policyState()).isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED);
        assertThat(value.enterprisePolicyState())
                .isEqualTo(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED);
        assertThat(value.editable()).isTrue();
    }
}

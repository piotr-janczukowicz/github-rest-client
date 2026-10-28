package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseTeamModelPoliciesResponseModelTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetEnterpriseTeamModelPoliciesResponseModel value =
                EnterpriseAdminCatalogFixtures.getEnterpriseTeamModelPoliciesResponseModel();

        assertThat(value.modelName()).isEqualTo("sample-model_name");
        assertThat(value.displayName()).isEqualTo("sample-display_name");
        assertThat(value.policyState()).isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED);
        assertThat(value.enterprisePolicyState())
                .isEqualTo(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED);
        assertThat(value.editable()).isTrue();
    }
}

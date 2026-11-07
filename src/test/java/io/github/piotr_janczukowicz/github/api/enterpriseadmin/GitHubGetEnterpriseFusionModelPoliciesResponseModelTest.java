package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseFusionModelPoliciesResponseModelTest {

    @Test
    void constructor_shouldThrow_whenModelNameNull() {
        final GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder builder =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.getEnterpriseFusionModelPoliciesResponseModel())
                        .modelName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("modelName");
    }

    @Test
    void constructor_shouldThrow_whenDisplayNameNull() {
        final GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder builder =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.getEnterpriseFusionModelPoliciesResponseModel())
                        .displayName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("displayName");
    }

    @Test
    void constructor_shouldThrow_whenPolicyStateNull() {
        final GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder builder =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.getEnterpriseFusionModelPoliciesResponseModel())
                        .policyState(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("policyState");
    }

    @Test
    void constructor_shouldThrow_whenEnterprisePolicyStateNull() {
        final GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder builder =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.getEnterpriseFusionModelPoliciesResponseModel())
                        .enterprisePolicyState(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enterprisePolicyState");
    }
}

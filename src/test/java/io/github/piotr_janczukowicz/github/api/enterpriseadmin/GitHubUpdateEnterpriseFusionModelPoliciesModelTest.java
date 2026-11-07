package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseFusionModelPoliciesModelTest {

    @Test
    void constructor_shouldThrow_whenModelNameNull() {
        final GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder builder =
                GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFusionModelPoliciesModel())
                        .modelName(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("modelName");
    }

    @Test
    void constructor_shouldThrow_whenPolicyStateNull() {
        final GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder builder =
                GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFusionModelPoliciesModel())
                        .policyState(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("policyState");
    }

    @Test
    void constructor_shouldThrow_whenModelNameTooShort() {
        final GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder builder =
                GitHubUpdateEnterpriseFusionModelPoliciesModelBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFusionModelPoliciesModel())
                        .modelName("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("modelName");
    }
}

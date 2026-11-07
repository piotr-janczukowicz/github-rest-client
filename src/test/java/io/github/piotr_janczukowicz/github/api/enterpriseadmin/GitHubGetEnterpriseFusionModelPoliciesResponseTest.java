package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetEnterpriseFusionModelPoliciesResponseTest {

    @Test
    void constructor_shouldThrow_whenModelsNull() {
        final GitHubGetEnterpriseFusionModelPoliciesResponseBuilder builder =
                GitHubGetEnterpriseFusionModelPoliciesResponseBuilder.builder(
                                EnterpriseAdminCatalogFixtures.getEnterpriseFusionModelPoliciesResponse())
                        .models(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("models");
    }
}

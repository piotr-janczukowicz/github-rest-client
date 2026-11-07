package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseFusionModelPoliciesPayloadTest {

    @Test
    void constructor_shouldThrow_whenModelsNull() {
        final GitHubUpdateEnterpriseFusionModelPoliciesPayloadBuilder builder =
                GitHubUpdateEnterpriseFusionModelPoliciesPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFusionModelPoliciesPayload())
                        .models(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("models");
    }

    @Test
    void constructor_shouldThrow_whenModelsTooSmall() {
        final GitHubUpdateEnterpriseFusionModelPoliciesPayloadBuilder builder =
                GitHubUpdateEnterpriseFusionModelPoliciesPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseFusionModelPoliciesPayload())
                        .models(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("models");
    }
}

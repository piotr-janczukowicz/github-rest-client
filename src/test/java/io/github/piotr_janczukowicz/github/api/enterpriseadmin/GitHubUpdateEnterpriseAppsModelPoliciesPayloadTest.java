package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseAppsModelPoliciesPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseAppsModelPoliciesPayload value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesPayload();

        assertThat(value.models()).isNotEmpty();
    }

    @Test
    void constructor_shouldRejectModels_whenInvalid() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseAppsModelPoliciesPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseAppsModelPoliciesPayload())
                .models(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamModelPoliciesPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseTeamModelPoliciesPayload value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseTeamModelPoliciesPayload();

        assertThat(value.models()).isNotEmpty();
    }

    @Test
    void constructor_shouldRejectModels_whenInvalid() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseTeamModelPoliciesPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.updateEnterpriseTeamModelPoliciesPayload())
                .models(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

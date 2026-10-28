package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseModelPoliciesPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubUpdateEnterpriseModelPoliciesPayload value =
                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesPayload();

        assertThat(value.defaultAvailability()).isNotNull();
        assertThat(value.models()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveModelsNull_whenNull() {
        final GitHubUpdateEnterpriseModelPoliciesPayload value =
                GitHubUpdateEnterpriseModelPoliciesPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.updateEnterpriseModelPoliciesPayload())
                        .models(null)
                        .build();

        assertThat(value.models()).isNull();
    }

    @Test
    void constructor_shouldReject_whenNoComponentSet() {
        final Throwable thrown = catchThrowable(() ->
                GitHubUpdateEnterpriseModelPoliciesPayloadBuilder.builder().build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}

package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrganizationModelPoliciesPayloadTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubUpdateOrganizationModelPoliciesPayload value =
                CopilotCatalogFixtures.updateOrganizationModelPoliciesPayload();

        assertThat(value.defaultAvailability()).isNotNull();
        assertThat(value.models()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveModelsNull_whenNull() {
        final GitHubUpdateOrganizationModelPoliciesPayload value =
                GitHubUpdateOrganizationModelPoliciesPayloadBuilder.builder(
                                CopilotCatalogFixtures.updateOrganizationModelPoliciesPayload())
                        .models(null)
                        .build();

        assertThat(value.models()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNoComponentSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateOrganizationModelPoliciesPayload(null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("at least one");
    }

    @Test
    void constructor_shouldAccept_whenOnlyDefaultAvailabilityIsSet() {
        final GitHubUpdateOrganizationModelPoliciesPayload value = new GitHubUpdateOrganizationModelPoliciesPayload(
                CopilotCatalogFixtures.updateOrganizationModelPoliciesDefaultAvailability(), null);

        assertThat(value.models()).isNull();
    }

    @Test
    void constructor_shouldAccept_whenOnlyModelsIsSet() {
        final GitHubUpdateOrganizationModelPoliciesPayload value = new GitHubUpdateOrganizationModelPoliciesPayload(
                null, List.of(CopilotCatalogFixtures.updateOrganizationModelPoliciesModel()));

        assertThat(value.defaultAvailability()).isNull();
    }
}

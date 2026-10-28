package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCodingAgentPermissionsOrganizationResponseTest {

    @Test
    void constructor_shouldThrow_whenEnabledRepositoriesNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubGetCopilotCodingAgentPermissionsOrganizationResponseBuilder.builder(
                                CopilotCatalogFixtures.getCopilotCodingAgentPermissionsOrganizationResponse())
                        .enabledRepositories(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledRepositories");
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoriesUrlNull_whenNull() {
        final GitHubGetCopilotCodingAgentPermissionsOrganizationResponse value =
                GitHubGetCopilotCodingAgentPermissionsOrganizationResponseBuilder.builder(
                                CopilotCatalogFixtures.getCopilotCodingAgentPermissionsOrganizationResponse())
                        .selectedRepositoriesUrl(null)
                        .build();

        assertThat(value.selectedRepositoriesUrl()).isNull();
    }
}

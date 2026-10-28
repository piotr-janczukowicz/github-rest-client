package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotCodingAgentPermissionsOrganizationPayloadTest {

    @Test
    void constructor_shouldThrow_whenEnabledRepositoriesNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSetCopilotCodingAgentPermissionsOrganizationPayloadBuilder.builder(
                                CopilotCatalogFixtures.setCopilotCodingAgentPermissionsOrganizationPayload())
                        .enabledRepositories(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enabledRepositories");
    }
}

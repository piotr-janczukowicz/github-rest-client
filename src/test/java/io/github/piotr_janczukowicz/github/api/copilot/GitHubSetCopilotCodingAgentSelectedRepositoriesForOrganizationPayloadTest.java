package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedRepositoryIdsNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayloadBuilder.builder(
                                CopilotCatalogFixtures
                                        .setCopilotCodingAgentSelectedRepositoriesForOrganizationPayload())
                        .selectedRepositoryIds(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedRepositoryIds");
    }
}

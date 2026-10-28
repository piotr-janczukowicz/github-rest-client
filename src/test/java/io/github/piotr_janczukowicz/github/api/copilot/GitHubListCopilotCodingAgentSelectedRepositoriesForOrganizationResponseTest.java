package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponseTest {

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponseBuilder.builder(
                                CopilotCatalogFixtures
                                        .listCopilotCodingAgentSelectedRepositoriesForOrganizationResponse())
                        .repositories(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }
}

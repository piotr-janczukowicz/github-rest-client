package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationToolsResponseMcpServerToolTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableRepoAutomationToolsResponseMcpServerToolBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseMcpServerToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseMcpServerTool())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }
}

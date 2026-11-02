package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationToolsResponseTest {

    @Test
    void constructor_shouldThrow_whenToolGroupsNull() {
        final GitHubListAvailableRepoAutomationToolsResponseBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponse())
                        .toolGroups(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("toolGroups");
    }

    @Test
    void constructor_shouldThrow_whenMcpServersNull() {
        final GitHubListAvailableRepoAutomationToolsResponseBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponse())
                        .mcpServers(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mcpServers");
    }
}

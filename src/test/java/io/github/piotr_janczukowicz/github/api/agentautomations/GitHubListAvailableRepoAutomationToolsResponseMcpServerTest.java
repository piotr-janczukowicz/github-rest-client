package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationToolsResponseMcpServerTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubListAvailableRepoAutomationToolsResponseMcpServerBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseMcpServerBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseMcpServer())
                        .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenToolsNull() {
        final GitHubListAvailableRepoAutomationToolsResponseMcpServerBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseMcpServerBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseMcpServer())
                        .tools(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tools");
    }
}

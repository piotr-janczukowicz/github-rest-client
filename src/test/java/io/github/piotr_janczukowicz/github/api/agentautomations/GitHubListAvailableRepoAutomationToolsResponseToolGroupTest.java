package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationToolsResponseToolGroupTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroup())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroup())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenToolsNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroup())
                        .tools(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tools");
    }
}

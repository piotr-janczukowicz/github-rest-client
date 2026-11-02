package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationToolsResponseToolGroupToolTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroupTool())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroupTool())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder builder =
                GitHubListAvailableRepoAutomationToolsResponseToolGroupToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationToolsResponseToolGroupTool())
                        .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }
}

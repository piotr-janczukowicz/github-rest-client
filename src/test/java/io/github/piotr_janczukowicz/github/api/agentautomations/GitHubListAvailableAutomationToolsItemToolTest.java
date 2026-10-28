package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationToolsItemToolTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableAutomationToolsItemToolBuilder builder =
                GitHubListAvailableAutomationToolsItemToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItemTool())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableAutomationToolsItemToolBuilder builder =
                GitHubListAvailableAutomationToolsItemToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItemTool())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubListAvailableAutomationToolsItemToolBuilder builder =
                GitHubListAvailableAutomationToolsItemToolBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItemTool())
                        .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }
}

package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationToolsItemTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableAutomationToolsItemBuilder builder =
                GitHubListAvailableAutomationToolsItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItem())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableAutomationToolsItemBuilder builder =
                GitHubListAvailableAutomationToolsItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItem())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenToolsNull() {
        final GitHubListAvailableAutomationToolsItemBuilder builder =
                GitHubListAvailableAutomationToolsItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItem())
                        .tools(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tools");
    }
}

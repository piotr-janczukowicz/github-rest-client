package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableAutomationTriggersItemBuilder builder =
                GitHubListAvailableAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableAutomationTriggersItemBuilder builder =
                GitHubListAvailableAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubListAvailableAutomationTriggersItemBuilder builder =
                GitHubListAvailableAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem())
                        .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenFieldsNull() {
        final GitHubListAvailableAutomationTriggersItemBuilder builder =
                GitHubListAvailableAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem())
                        .fields(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fields");
    }
}

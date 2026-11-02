package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationTriggersItemTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableRepoAutomationTriggersItemBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationTriggersItem())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final GitHubListAvailableRepoAutomationTriggersItemBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationTriggersItem())
                        .title(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final GitHubListAvailableRepoAutomationTriggersItemBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationTriggersItem())
                        .description(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenFieldsNull() {
        final GitHubListAvailableRepoAutomationTriggersItemBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableRepoAutomationTriggersItem())
                        .fields(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("fields");
    }
}

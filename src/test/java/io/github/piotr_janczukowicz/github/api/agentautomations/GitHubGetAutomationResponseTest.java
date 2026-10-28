package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetAutomationResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenRepositoryNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .repository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repository");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldLeaveToolsNull_whenNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .tools(null);

        final GitHubGetAutomationResponse sut = builder.build();

        assertThat(sut.tools()).isNull();
    }

    @Test
    void constructor_shouldLeaveAllowedCustomAgentsNull_whenNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .allowedCustomAgents(null);

        final GitHubGetAutomationResponse sut = builder.build();

        assertThat(sut.allowedCustomAgents()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubGetAutomationResponseBuilder builder = GitHubGetAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getAutomationResponse())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

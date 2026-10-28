package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetRepoAutomationResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldLeaveToolsNull_whenNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .tools(null);

        final GitHubGetRepoAutomationResponse sut = builder.build();

        assertThat(sut.tools()).isNull();
    }

    @Test
    void constructor_shouldLeaveAllowedCustomAgentsNull_whenNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .allowedCustomAgents(null);

        final GitHubGetRepoAutomationResponse sut = builder.build();

        assertThat(sut.allowedCustomAgents()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubGetRepoAutomationResponseBuilder builder = GitHubGetRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.getRepoAutomationResponse())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

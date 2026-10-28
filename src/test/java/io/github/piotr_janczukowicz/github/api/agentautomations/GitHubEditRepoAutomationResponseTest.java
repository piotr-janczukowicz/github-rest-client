package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldLeaveToolsNull_whenNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .tools(null);

        final GitHubEditRepoAutomationResponse sut = builder.build();

        assertThat(sut.tools()).isNull();
    }

    @Test
    void constructor_shouldLeaveAllowedCustomAgentsNull_whenNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .allowedCustomAgents(null);

        final GitHubEditRepoAutomationResponse sut = builder.build();

        assertThat(sut.allowedCustomAgents()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenCreatedByNull() {
        final GitHubEditRepoAutomationResponseBuilder builder = GitHubEditRepoAutomationResponseBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationResponse())
                .createdBy(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdBy");
    }
}

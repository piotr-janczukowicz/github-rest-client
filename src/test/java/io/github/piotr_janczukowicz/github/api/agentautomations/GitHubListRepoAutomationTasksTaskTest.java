package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationTasksTaskTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListRepoAutomationTasksTaskBuilder builder = GitHubListRepoAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationTasksTask())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubListRepoAutomationTasksTaskBuilder builder = GitHubListRepoAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationTasksTask())
                .userCollaborators(null);

        final GitHubListRepoAutomationTasksTask sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubListRepoAutomationTasksTaskBuilder builder = GitHubListRepoAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationTasksTask())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubListRepoAutomationTasksTaskBuilder builder = GitHubListRepoAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationTasksTask())
                .artifacts(null);

        final GitHubListRepoAutomationTasksTask sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubListRepoAutomationTasksTaskBuilder builder = GitHubListRepoAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listRepoAutomationTasksTask())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}

package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksForRepoTaskTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubListTasksForRepoTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksForRepoTask())
                        .id(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubListTasksForRepoTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksForRepoTask())
                        .state(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubListTasksForRepoTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksForRepoTask())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubListTasksForRepoTaskBuilder builder = GitHubListTasksForRepoTaskBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksForRepoTask())
                .userCollaborators(null);

        final GitHubListTasksForRepoTask sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubListTasksForRepoTaskBuilder builder = GitHubListTasksForRepoTaskBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksForRepoTask())
                .artifacts(null);

        final GitHubListTasksForRepoTask sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }
}

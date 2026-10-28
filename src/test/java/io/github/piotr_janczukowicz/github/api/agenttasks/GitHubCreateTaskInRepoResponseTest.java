package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTaskInRepoResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponse())
                .id(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponse())
                .state(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponse())
                .createdAt(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubCreateTaskInRepoResponseBuilder builder = GitHubCreateTaskInRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponse())
                .userCollaborators(null);

        final GitHubCreateTaskInRepoResponse sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubCreateTaskInRepoResponseBuilder builder = GitHubCreateTaskInRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponse())
                .artifacts(null);

        final GitHubCreateTaskInRepoResponse sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }
}

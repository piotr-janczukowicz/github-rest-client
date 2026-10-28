package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByRepoAndIdResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .id(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .state(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .createdAt(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubGetTaskByRepoAndIdResponseBuilder builder = GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .userCollaborators(null);

        final GitHubGetTaskByRepoAndIdResponse sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubGetTaskByRepoAndIdResponseBuilder builder = GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .artifacts(null);

        final GitHubGetTaskByRepoAndIdResponse sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }

    @Test
    void constructor_shouldLeaveSessionsNull_whenNull() {
        final GitHubGetTaskByRepoAndIdResponseBuilder builder = GitHubGetTaskByRepoAndIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponse())
                .sessions(null);

        final GitHubGetTaskByRepoAndIdResponse sut = builder.build();

        assertThat(sut.sessions()).isNull();
    }
}

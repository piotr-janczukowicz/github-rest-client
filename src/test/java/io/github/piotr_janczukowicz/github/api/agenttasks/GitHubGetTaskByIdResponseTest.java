package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByIdResponseTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGetTaskByIdResponseBuilder.builder(AgentTasksCatalogFixtures.getTaskByIdResponse())
                        .id(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGetTaskByIdResponseBuilder.builder(AgentTasksCatalogFixtures.getTaskByIdResponse())
                        .state(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGetTaskByIdResponseBuilder.builder(AgentTasksCatalogFixtures.getTaskByIdResponse())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubGetTaskByIdResponseBuilder builder = GitHubGetTaskByIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponse())
                .userCollaborators(null);

        final GitHubGetTaskByIdResponse sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubGetTaskByIdResponseBuilder builder = GitHubGetTaskByIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponse())
                .artifacts(null);

        final GitHubGetTaskByIdResponse sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }

    @Test
    void constructor_shouldLeaveSessionsNull_whenNull() {
        final GitHubGetTaskByIdResponseBuilder builder = GitHubGetTaskByIdResponseBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponse())
                .sessions(null);

        final GitHubGetTaskByIdResponse sut = builder.build();

        assertThat(sut.sessions()).isNull();
    }
}

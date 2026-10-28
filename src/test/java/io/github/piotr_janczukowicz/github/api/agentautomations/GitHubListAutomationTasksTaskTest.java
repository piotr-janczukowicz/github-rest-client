package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationTasksTaskTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAutomationTasksTaskBuilder builder = GitHubListAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationTasksTask())
                .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubListAutomationTasksTaskBuilder builder = GitHubListAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationTasksTask())
                .userCollaborators(null);

        final GitHubListAutomationTasksTask sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubListAutomationTasksTaskBuilder builder = GitHubListAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationTasksTask())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubListAutomationTasksTaskBuilder builder = GitHubListAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationTasksTask())
                .artifacts(null);

        final GitHubListAutomationTasksTask sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubListAutomationTasksTaskBuilder builder = GitHubListAutomationTasksTaskBuilder.builder(
                        AgentAutomationsCatalogFixtures.listAutomationTasksTask())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}

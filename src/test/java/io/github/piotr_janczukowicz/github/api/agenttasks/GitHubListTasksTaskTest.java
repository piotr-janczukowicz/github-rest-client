package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksTaskTest {

    @Test
    void decode_shouldKeepTheTask_whenStateIsNotKnown() {
        final String json = new String(
                        FileUtils.readBytes(GitHubListTasksTaskTest.class, "list-tasks-task.json"),
                        StandardCharsets.UTF_8)
                .replace("\"state\": \"queued\"", "\"state\": \"brand_new_state\"");

        final GitHubListTasksTask task =
                GitHubJsonCodecs.defaults().decode(json.getBytes(StandardCharsets.UTF_8), GitHubListTasksTask.class);

        assertThat(task.state()).isEqualTo(GitHubListTasksTaskState.UNKNOWN);
    }

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubListTasksTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksTask())
                        .id(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubListTasksTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksTask())
                        .state(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubListTasksTaskBuilder.builder(AgentTasksCatalogFixtures.listTasksTask())
                        .createdAt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldLeaveUserCollaboratorsNull_whenNull() {
        final GitHubListTasksTaskBuilder builder = GitHubListTasksTaskBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksTask())
                .userCollaborators(null);

        final GitHubListTasksTask sut = builder.build();

        assertThat(sut.userCollaborators()).isNull();
    }

    @Test
    void constructor_shouldLeaveArtifactsNull_whenNull() {
        final GitHubListTasksTaskBuilder builder = GitHubListTasksTaskBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksTask())
                .artifacts(null);

        final GitHubListTasksTask sut = builder.build();

        assertThat(sut.artifacts()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksTaskArtifactDataByRefTest {

    @Test
    void constructor_shouldThrow_whenHeadRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListTasksTaskArtifactDataByRefBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksTaskArtifactDataByRef())
                .headRef(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headRef");
    }

    @Test
    void constructor_shouldThrow_whenBaseRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListTasksTaskArtifactDataByRefBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksTaskArtifactDataByRef())
                .baseRef(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("baseRef");
    }
}

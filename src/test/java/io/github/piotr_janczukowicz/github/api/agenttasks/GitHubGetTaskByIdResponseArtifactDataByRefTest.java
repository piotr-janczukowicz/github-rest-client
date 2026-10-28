package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByIdResponseArtifactDataByRefTest {

    @Test
    void constructor_shouldThrow_whenHeadRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseArtifactDataByRefBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseArtifactDataByRef())
                .headRef(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headRef");
    }

    @Test
    void constructor_shouldThrow_whenBaseRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseArtifactDataByRefBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseArtifactDataByRef())
                .baseRef(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("baseRef");
    }
}

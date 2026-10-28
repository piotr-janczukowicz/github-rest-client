package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationTasksTaskArtifactDataByRefTest {

    @Test
    void constructor_shouldThrow_whenHeadRefNull() {
        final GitHubListAutomationTasksTaskArtifactDataByRefBuilder builder =
                GitHubListAutomationTasksTaskArtifactDataByRefBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationTasksTaskArtifactDataByRef())
                        .headRef(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("headRef");
    }

    @Test
    void constructor_shouldThrow_whenBaseRefNull() {
        final GitHubListAutomationTasksTaskArtifactDataByRefBuilder builder =
                GitHubListAutomationTasksTaskArtifactDataByRefBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationTasksTaskArtifactDataByRef())
                        .baseRef(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("baseRef");
    }
}

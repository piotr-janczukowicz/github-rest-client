package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationTasksTaskArtifactTest {

    @Test
    void constructor_shouldThrow_whenProviderNull() {
        final GitHubListAutomationTasksTaskArtifactBuilder builder =
                GitHubListAutomationTasksTaskArtifactBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationTasksTaskArtifact())
                        .provider(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("provider");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAutomationTasksTaskArtifactBuilder builder =
                GitHubListAutomationTasksTaskArtifactBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationTasksTaskArtifact())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenDataNull() {
        final GitHubListAutomationTasksTaskArtifactBuilder builder =
                GitHubListAutomationTasksTaskArtifactBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationTasksTaskArtifact())
                        .data(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("data");
    }
}

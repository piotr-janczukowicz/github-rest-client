package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTaskInRepoResponseArtifactTest {

    @Test
    void constructor_shouldThrow_whenProviderNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponseArtifact())
                .provider(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("provider");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponseArtifact())
                .type(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenDataNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateTaskInRepoResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.createTaskInRepoResponseArtifact())
                .data(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("data");
    }
}

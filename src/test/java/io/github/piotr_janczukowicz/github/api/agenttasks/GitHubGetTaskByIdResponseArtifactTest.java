package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByIdResponseArtifactTest {

    @Test
    void constructor_shouldThrow_whenProviderNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseArtifact())
                .provider(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("provider");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseArtifact())
                .type(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenDataNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseArtifactBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseArtifact())
                .data(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("data");
    }
}

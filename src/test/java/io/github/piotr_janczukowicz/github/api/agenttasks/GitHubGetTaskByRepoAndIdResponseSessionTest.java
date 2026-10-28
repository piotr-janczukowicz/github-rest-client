package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByRepoAndIdResponseSessionTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseSessionBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponseSession())
                .id(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseSessionBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponseSession())
                .state(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByRepoAndIdResponseSessionBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByRepoAndIdResponseSession())
                .createdAt(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }
}

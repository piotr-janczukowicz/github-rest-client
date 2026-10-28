package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksForRepoResponseTest {

    @Test
    void constructor_shouldThrow_whenTasksNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListTasksForRepoResponseBuilder.builder(
                        AgentTasksCatalogFixtures.listTasksForRepoResponse())
                .tasks(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tasks");
    }
}

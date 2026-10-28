package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListTasksResponseTest {

    @Test
    void constructor_shouldThrow_whenTasksNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubListTasksResponseBuilder.builder(AgentTasksCatalogFixtures.listTasksResponse())
                        .tasks(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tasks");
    }
}

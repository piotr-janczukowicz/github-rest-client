package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationTasksResponseTest {

    @Test
    void constructor_shouldThrow_whenTasksNull() {
        final GitHubListRepoAutomationTasksResponseBuilder builder =
                GitHubListRepoAutomationTasksResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.listRepoAutomationTasksResponse())
                        .tasks(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tasks");
    }
}

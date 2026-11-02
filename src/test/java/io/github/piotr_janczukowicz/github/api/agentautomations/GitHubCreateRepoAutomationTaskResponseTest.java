package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTaskResponseTest {

    @Test
    void constructor_shouldThrow_whenTaskIdNull() {
        final GitHubCreateRepoAutomationTaskResponseBuilder builder =
                GitHubCreateRepoAutomationTaskResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTaskResponse())
                        .taskId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("taskId");
    }

    @Test
    void constructor_shouldThrow_whenAutomationIdNull() {
        final GitHubCreateRepoAutomationTaskResponseBuilder builder =
                GitHubCreateRepoAutomationTaskResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTaskResponse())
                        .automationId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("automationId");
    }

    @Test
    void constructor_shouldThrow_whenEventNull() {
        final GitHubCreateRepoAutomationTaskResponseBuilder builder =
                GitHubCreateRepoAutomationTaskResponseBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTaskResponse())
                        .event(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("event");
    }
}

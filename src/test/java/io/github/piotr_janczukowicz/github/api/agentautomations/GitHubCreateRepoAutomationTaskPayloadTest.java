package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationTaskPayloadTest {

    @Test
    void constructor_shouldThrow_whenEventNull() {
        final GitHubCreateRepoAutomationTaskPayloadBuilder builder =
                GitHubCreateRepoAutomationTaskPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTaskPayload())
                        .event(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("event");
    }

    @Test
    void constructor_shouldThrow_whenContextTooLong() {
        final GitHubCreateRepoAutomationTaskPayloadBuilder builder =
                GitHubCreateRepoAutomationTaskPayloadBuilder.builder(
                                AgentAutomationsCatalogFixtures.createRepoAutomationTaskPayload())
                        .context("x".repeat(10_001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("context");
    }
}

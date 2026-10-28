package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTaskInRepoPayloadTest {

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTaskInRepoPayloadBuilder.builder(AgentTasksCatalogFixtures.createTaskInRepoPayload())
                        .prompt(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }
}

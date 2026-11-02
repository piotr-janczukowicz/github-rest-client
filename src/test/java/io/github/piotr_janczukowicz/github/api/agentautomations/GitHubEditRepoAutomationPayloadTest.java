package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationPayloadTest {

    @Test
    void constructor_shouldThrow_whenPromptTooLong() {
        final GitHubEditRepoAutomationPayloadBuilder builder = GitHubEditRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationPayload())
                .prompt("x".repeat(10_001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldAcceptNull_whenToolsNull() {
        final GitHubEditRepoAutomationPayload value = GitHubEditRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationPayload())
                .tools(null)
                .build();

        assertThat(value.tools()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenToolsGiven() {
        final List<String> source = new ArrayList<>(List.of("tools"));
        final GitHubEditRepoAutomationPayload value = GitHubEditRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationPayload())
                .tools(source)
                .build();

        source.add("tools");

        assertThat(value.tools()).hasSize(source.size() - 1);
    }
}

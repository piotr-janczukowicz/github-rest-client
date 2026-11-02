package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoAutomationPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateRepoAutomationPayloadBuilder builder = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPromptNull() {
        final GitHubCreateRepoAutomationPayloadBuilder builder = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .prompt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldThrow_whenPromptTooLong() {
        final GitHubCreateRepoAutomationPayloadBuilder builder = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .prompt("x".repeat(10_001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("prompt");
    }

    @Test
    void constructor_shouldAcceptNull_whenToolsNull() {
        final GitHubCreateRepoAutomationPayload value = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .tools(null)
                .build();

        assertThat(value.tools()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenToolsGiven() {
        final List<String> source = new ArrayList<>(List.of("tools"));
        final GitHubCreateRepoAutomationPayload value = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .tools(source)
                .build();

        source.add("tools");

        assertThat(value.tools()).hasSize(source.size() - 1);
    }
}

package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
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
    void constructor_shouldLeaveToolsNull_whenNull() {
        final GitHubCreateRepoAutomationPayloadBuilder builder = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .tools(null);

        final GitHubCreateRepoAutomationPayload sut = builder.build();

        assertThat(sut.tools()).isNull();
    }

    @Test
    void constructor_shouldLeaveAllowedCustomAgentsNull_whenNull() {
        final GitHubCreateRepoAutomationPayloadBuilder builder = GitHubCreateRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.createRepoAutomationPayload())
                .allowedCustomAgents(null);

        final GitHubCreateRepoAutomationPayload sut = builder.build();

        assertThat(sut.allowedCustomAgents()).isNull();
    }
}

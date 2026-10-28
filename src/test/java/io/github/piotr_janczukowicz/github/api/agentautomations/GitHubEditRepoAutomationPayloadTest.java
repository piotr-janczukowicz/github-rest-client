package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEditRepoAutomationPayloadTest {

    @Test
    void constructor_shouldLeaveToolsNull_whenNull() {
        final GitHubEditRepoAutomationPayloadBuilder builder = GitHubEditRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationPayload())
                .tools(null);

        final GitHubEditRepoAutomationPayload sut = builder.build();

        assertThat(sut.tools()).isNull();
    }

    @Test
    void constructor_shouldLeaveAllowedCustomAgentsNull_whenNull() {
        final GitHubEditRepoAutomationPayloadBuilder builder = GitHubEditRepoAutomationPayloadBuilder.builder(
                        AgentAutomationsCatalogFixtures.editRepoAutomationPayload())
                .allowedCustomAgents(null);

        final GitHubEditRepoAutomationPayload sut = builder.build();

        assertThat(sut.allowedCustomAgents()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCustomAgentsForEnterpriseResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetCopilotCustomAgentsForEnterpriseResponse value =
                CopilotCatalogFixtures.getCopilotCustomAgentsForEnterpriseResponse();

        assertThat(value.customAgents()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveCustomAgentsNull_whenNull() {
        final GitHubGetCopilotCustomAgentsForEnterpriseResponse value =
                GitHubGetCopilotCustomAgentsForEnterpriseResponseBuilder.builder(
                                CopilotCatalogFixtures.getCopilotCustomAgentsForEnterpriseResponse())
                        .customAgents(null)
                        .build();

        assertThat(value.customAgents()).isNull();
    }
}

package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubSetCopilotCustomAgentsSourceForEnterpriseResponse value =
                CopilotCatalogFixtures.setCopilotCustomAgentsSourceForEnterpriseResponse();

        assertThat(value.organization()).isNotNull();
        assertThat(value.repository()).isNotNull();
        assertThat(value.ruleset()).isNotNull();
    }
}

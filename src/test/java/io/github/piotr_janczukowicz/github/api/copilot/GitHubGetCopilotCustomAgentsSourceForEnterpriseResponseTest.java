package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetCopilotCustomAgentsSourceForEnterpriseResponse value =
                CopilotCatalogFixtures.getCopilotCustomAgentsSourceForEnterpriseResponse();

        assertThat(value.organization()).isNotNull();
        assertThat(value.repository()).isNotNull();
    }
}

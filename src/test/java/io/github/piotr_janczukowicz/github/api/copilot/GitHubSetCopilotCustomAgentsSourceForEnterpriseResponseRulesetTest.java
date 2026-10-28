package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRulesetTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubSetCopilotCustomAgentsSourceForEnterpriseResponseRuleset value =
                CopilotCatalogFixtures.setCopilotCustomAgentsSourceForEnterpriseResponseRuleset();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("sample-name");
        assertThat(value.enforcement()).isEqualTo("sample-enforcement");
    }
}

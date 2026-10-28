package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSetCopilotCustomAgentsSourceForEnterprisePayloadTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubSetCopilotCustomAgentsSourceForEnterprisePayload value =
                CopilotCatalogFixtures.setCopilotCustomAgentsSourceForEnterprisePayload();

        assertThat(value.organizationId()).isEqualTo(1L);
        assertThat(value.createRuleset()).isTrue();
    }
}

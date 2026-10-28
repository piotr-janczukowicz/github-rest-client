package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganizationTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseOrganization value =
                CopilotCatalogFixtures.getCopilotCustomAgentsSourceForEnterpriseResponseOrganization();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.login()).isEqualTo("sample-login");
    }
}

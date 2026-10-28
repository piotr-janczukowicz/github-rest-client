package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepositoryTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetCopilotCustomAgentsSourceForEnterpriseResponseRepository value =
                CopilotCatalogFixtures.getCopilotCustomAgentsSourceForEnterpriseResponseRepository();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.name()).isEqualTo("sample-name");
        assertThat(value.fullName()).isEqualTo("sample-full_name");
    }
}

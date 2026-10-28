package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayloadTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload value =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsPayload();

        assertThat(value.selectedEnterpriseTeams()).isNotEmpty();
    }
}

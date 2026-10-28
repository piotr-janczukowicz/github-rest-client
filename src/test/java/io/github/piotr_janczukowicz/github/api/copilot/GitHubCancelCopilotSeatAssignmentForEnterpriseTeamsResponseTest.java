package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse value =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsResponse();

        assertThat(value.seatsCancelled()).isEqualTo(1);
    }
}

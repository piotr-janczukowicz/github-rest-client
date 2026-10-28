package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCopilotSeatsResponseTest {

    @Test
    void constructor_shouldLeaveTotalSeatsNull_whenNull() {
        final GitHubListCopilotSeatsResponse value = new GitHubListCopilotSeatsResponse(null, null);

        assertThat(value.totalSeats()).isNull();
    }

    @Test
    void constructor_shouldLeaveSeatsNull_whenNull() {
        final GitHubListCopilotSeatsResponse value = new GitHubListCopilotSeatsResponse(1, null);

        assertThat(value.seats()).isNull();
    }

    @Test
    void constructor_shouldCopySeats_whenPresent() {
        final GitHubListCopilotSeatsResponse value = CopilotCatalogFixtures.listCopilotSeatsResponse();

        assertThat(value.seats()).containsExactly(CopilotCatalogFixtures.copilotSeatDetails());
    }
}

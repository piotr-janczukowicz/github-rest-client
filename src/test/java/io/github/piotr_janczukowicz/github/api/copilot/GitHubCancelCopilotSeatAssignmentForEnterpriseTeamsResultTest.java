package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResultTest {

    @Test
    void ofCancelled_shouldWrapResponse_whenCalled() {
        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse response =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsResponse();

        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult result =
                GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofCancelled(response);

        assertThat(result).isEqualTo(new GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Cancelled(response));
    }

    @Test
    void ofAccepted_shouldWrapResponse_whenCalled() {
        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse response =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse();

        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult result =
                GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofAccepted(response);

        assertThat(result).isEqualTo(new GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Accepted(response));
    }

    @Test
    void cancelled_shouldThrow_whenResponseNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Cancelled(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("response");
    }

    @Test
    void accepted_shouldThrow_whenResponseNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Accepted(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("response");
    }
}

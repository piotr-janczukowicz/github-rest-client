package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

/**
 * Result of {@code DELETE /enterprises/{enterprise}/copilot/billing/selected_enterprise_teams}: HTTP 200 with the seats
 * cancelled, or HTTP 202 when the cancellation is accepted for asynchronous processing.
 */
public sealed interface GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult
        permits GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Cancelled,
                GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.Accepted {

    /**
     * Seats cancelled (HTTP 200).
     *
     * @param response cancellation result
     * @return result
     */
    static GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult ofCancelled(
            final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse response) {
        return new Cancelled(response);
    }

    /**
     * Accepted cancellation (HTTP 202).
     *
     * @param response accepted-request body
     * @return result
     */
    static GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult ofAccepted(
            final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse response) {
        return new Accepted(response);
    }

    /**
     * Seats cancelled.
     *
     * @param response cancellation result
     */
    record Cancelled(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResponse response)
            implements GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult {
        public Cancelled {
            requireNonNull(response, "response");
        }
    }

    /**
     * Accepted cancellation.
     *
     * @param response accepted-request body
     */
    record Accepted(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse response)
            implements GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult {
        public Accepted {
            requireNonNull(response, "response");
        }
    }
}

package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update a Dependabot alert.
 *
 * @param state The state of the Dependabot alert. A {@code dismissed_reason} must be provided when setting the state to
 *     {@code dismissed}.
 * @param dismissedReason Required when {@code state} is {@code dismissed}. A reason for dismissing the alert.
 * @param dismissedComment An optional comment associated with dismissing the alert.
 * @param assignees Usernames to assign to this Dependabot Alert. Pass one or more user logins to replace the set of
 *     assignees on this alert. Send an empty array to clear all assignees from the alert.
 * @param agentAssignment Parameters for AI agent assignment. Only used when an agent bot login is included in
 *     {@code assignees}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/update-alert/requestBody")
public record GitHubUpdateAlertPayload(
        @Nullable GitHubDependabotAlertUpdateState state,
        @Nullable GitHubDependabotAlertDismissedReason dismissedReason,
        @Nullable String dismissedComment,
        @Nullable List<String> assignees,
        @Nullable GitHubDependabotAlertAgentAssignment agentAssignment) {

    private static final int MAX_DISMISSED_COMMENT_LENGTH = 280;

    public GitHubUpdateAlertPayload {
        requireAtLeastOne(state, assignees, "state or assignees");
        dismissedComment =
                requireMaxLengthIfPresent(dismissedComment, MAX_DISMISSED_COMMENT_LENGTH, "dismissedComment");
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
    }
}

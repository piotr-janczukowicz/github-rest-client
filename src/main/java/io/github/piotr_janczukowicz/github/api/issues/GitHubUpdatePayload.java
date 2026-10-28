package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update issue request body.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.repositories.GitHubUpdatePayload}.
 *
 * @param title issue title
 * @param body issue body
 * @param state open or closed state
 * @param stateReason reason for the state change
 * @param duplicateIssueId canonical duplicate issue id when closing as duplicate
 * @param milestone milestone reference
 * @param labels labels to replace on the issue
 * @param assignees assignees to replace on the issue
 * @param issueFieldValues custom field values
 * @param type issue type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/update/requestBody")
public record GitHubUpdatePayload(
        @Nullable OptionalNull<String> title,
        @Nullable OptionalNull<String> body,
        @Nullable GitHubIssueUpdateState state,
        @Nullable OptionalNull<GitHubIssueStateReason> stateReason,
        @Nullable Long duplicateIssueId,
        @Nullable OptionalNull<GitHubIssueMilestoneReference> milestone,
        @Nullable List<GitHubUpdateIssueLabel> labels,
        @Nullable List<GitHubUpdateIssueAssignee> assignees,
        @Nullable List<GitHubUpdateIssueFieldValue> issueFieldValues,
        @Nullable OptionalNull<GitHubUpdateIssueType> type) {

    public GitHubUpdatePayload {
        if (labels != null) {
            labels = List.copyOf(labels);
        }
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
        if (issueFieldValues != null) {
            issueFieldValues = List.copyOf(issueFieldValues);
        }
    }
}

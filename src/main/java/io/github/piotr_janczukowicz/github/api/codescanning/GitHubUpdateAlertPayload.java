package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeastOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Request body for {@code PATCH /repos/{owner}/{repo}/code-scanning/alerts/{alert_number}}.
 *
 * @param state Sets the state of the code scanning alert.
 * @param dismissedReason **Required when the state is dismissed.** The reason for dismissing or closing the alert.
 * @param dismissedComment The dismissal comment associated with the dismissal of the alert.
 * @param createRequest If `true`, attempt to create an alert dismissal request.
 * @param assignees The list of users to assign to the code scanning alert.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-scanning/update-alert/requestBody")
public record GitHubUpdateAlertPayload(
        @Nullable GitHubCodeScanningAlertSetState state,
        @Nullable GitHubCodeScanningAlertDismissedReason dismissedReason,
        @Nullable String dismissedComment,
        @Nullable Boolean createRequest,
        @Nullable List<String> assignees) {
    public GitHubUpdateAlertPayload {
        requireAtLeastOne(state, assignees, "state or assignees");
        dismissedComment = requireMaxLengthIfPresent(dismissedComment, 280, "dismissedComment");
        if (assignees != null) {
            assignees = List.copyOf(assignees);
        }
    }
}

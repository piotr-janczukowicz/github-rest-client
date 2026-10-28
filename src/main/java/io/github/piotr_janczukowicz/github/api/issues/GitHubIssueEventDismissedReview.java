package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue Event Dismissed Review.
 *
 * @param state State
 * @param reviewId Review id
 * @param dismissalMessage Dismissal message
 * @param dismissalCommitId Dismissal commit id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-event-dismissed-review")
@RecordBuilder
public record GitHubIssueEventDismissedReview(
        String state,
        long reviewId,
        @Nullable String dismissalMessage,
        @Nullable String dismissalCommitId) {

    public GitHubIssueEventDismissedReview {
        requireNonNull(state, "state");
    }
}

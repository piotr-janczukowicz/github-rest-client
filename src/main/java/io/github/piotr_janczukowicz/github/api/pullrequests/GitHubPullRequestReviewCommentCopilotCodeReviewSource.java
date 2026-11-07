package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request review comment copilot code review source.
 *
 * @param kind The kind of contributing source.
 * @param name The reported source name, or "custom instructions" for CUSTOM_INSTRUCTIONS.
 * @param path The validated repository-relative source path, if available. Commit OIDs are not exposed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pull-request-review-comment/properties/copilot_code_review_sources/items")
public record GitHubPullRequestReviewCommentCopilotCodeReviewSource(
        GitHubPullRequestReviewCommentCopilotCodeReviewSourceKind kind,
        String name,
        @Nullable String path) {
    public GitHubPullRequestReviewCommentCopilotCodeReviewSource {
        requireNonNull(kind, "kind");
        requireNonNull(name, "name");
    }
}

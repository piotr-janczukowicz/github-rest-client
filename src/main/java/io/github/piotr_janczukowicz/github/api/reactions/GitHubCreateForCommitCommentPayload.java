package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create a reaction to a commit comment.
 *
 * @param content The reaction type to add to the commit comment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/reactions/create-for-commit-comment/requestBody",
    "operations/reactions/create-for-issue-comment/requestBody",
    "operations/reactions/create-for-issue/requestBody",
    "operations/reactions/create-for-pull-request-review-comment/requestBody"
})
public record GitHubCreateForCommitCommentPayload(GitHubReactionContent content) {

    public GitHubCreateForCommitCommentPayload {
        requireNonNull(content, "content");
    }
}

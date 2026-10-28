package io.github.piotr_janczukowicz.github.api.reactions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters shared by the reaction list endpoints for commit comments, issue comments, issues and pull request
 * review comments ({@code GET /repos/{owner}/{repo}/comments/{comment_id}/reactions}, {@code GET
 * /repos/{owner}/{repo}/issues/comments/{comment_id}/reactions}, {@code GET
 * /repos/{owner}/{repo}/issues/{issue_number}/reactions} and {@code GET
 * /repos/{owner}/{repo}/pulls/comments/{comment_id}/reactions}), excluding pagination. The name is kept for
 * compatibility; the type is not specific to commit comments.
 *
 * @param content reaction type to return; {@code null} lists reactions of every type
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/reactions/list-for-commit-comment/parameters",
    "operations/reactions/list-for-issue-comment/parameters",
    "operations/reactions/list-for-issue/parameters",
    "operations/reactions/list-for-pull-request-review-comment/parameters"
})
@RecordBuilder
public record GitHubListForCommitCommentFilter(@Nullable GitHubReactionContent content) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListForCommitCommentFilter empty() {
        return GitHubListForCommitCommentFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (content == null) {
            return Map.of();
        }

        return Map.of("content", content.wireValue());
    }
}

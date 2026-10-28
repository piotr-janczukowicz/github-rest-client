package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssue;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Issue comment event payload.
 *
 * @param action The action that was performed
 * @param issue The issue the comment belongs to
 * @param comment The issue comment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-comment-event")
@RecordBuilder
public record GitHubIssueCommentEvent(String action, GitHubIssue issue, GitHubIssueComment comment)
        implements GitHubEventPayload {

    public GitHubIssueCommentEvent {
        requireNonNull(action, "action");
        requireNonNull(issue, "issue");
        requireNonNull(comment, "comment");
    }
}

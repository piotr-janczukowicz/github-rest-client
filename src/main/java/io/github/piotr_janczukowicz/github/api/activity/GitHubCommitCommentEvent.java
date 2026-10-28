package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Commit comment event payload.
 *
 * @param action The action that was performed
 * @param comment The commit comment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/commit-comment-event")
@RecordBuilder
public record GitHubCommitCommentEvent(@Nullable String action, GitHubCommitCommentEventComment comment)
        implements GitHubEventPayload {

    public GitHubCommitCommentEvent {
        requireNonNull(comment, "comment");
    }
}

package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Commit comment.
 *
 * @param htmlUrl HTML URL of the comment
 * @param url URL of the comment
 * @param id Comment id
 * @param nodeId GraphQL node id
 * @param body Comment body
 * @param path File path the comment refers to
 * @param position Position in the diff
 * @param line Line number in the file
 * @param commitId SHA of the commit the comment is on
 * @param user The user who wrote the comment
 * @param createdAt Creation time
 * @param updatedAt Last update time
 * @param reactions Reaction counts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/commit-comment-event/properties/comment")
@RecordBuilder
public record GitHubCommitCommentEventComment(
        @Nullable String htmlUrl,
        @Nullable String url,
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String body,
        @Nullable String path,
        @Nullable Integer position,
        @Nullable Integer line,
        @Nullable String commitId,
        @Nullable GitHubSimpleUser user,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt,
        @Nullable GitHubReactionRollup reactions,
        @Nullable GitHubAuthorAssociation authorAssociation) {}

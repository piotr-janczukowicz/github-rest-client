package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.issues.GitHubTimelineComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Commit comment.
 *
 * @param htmlUrl Html url
 * @param url Url
 * @param id Id
 * @param nodeId Node id
 * @param body Body
 * @param path Path
 * @param position Position
 * @param line Line
 * @param commitId Commit id
 * @param user User
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param authorAssociation Author association
 * @param reactions Reactions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/commit-comment")
@RecordBuilder
public record GitHubCommitComment(
        String htmlUrl,
        String url,
        long id,
        String nodeId,
        String body,
        @Nullable String path,
        @Nullable Integer position,
        @Nullable Integer line,
        String commitId,
        @Nullable GitHubSimpleUser user,
        Instant createdAt,
        Instant updatedAt,
        GitHubAuthorAssociation authorAssociation,
        @Nullable GitHubReactionRollup reactions)
        implements GitHubTimelineComment {

    public GitHubCommitComment {
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(body, "body");
        requireNonNull(commitId, "commitId");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(authorAssociation, "authorAssociation");
    }
}

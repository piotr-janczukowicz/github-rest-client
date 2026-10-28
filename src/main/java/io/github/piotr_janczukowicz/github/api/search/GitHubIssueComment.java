package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Issue Comment. Comments provide a way for people to collaborate on an issue.
 *
 * @param id Unique identifier of the issue comment
 * @param nodeId Node id
 * @param url URL for the issue comment
 * @param body Contents of the issue comment
 * @param bodyText Body text
 * @param bodyHtml Body html
 * @param htmlUrl Html url
 * @param user User
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param issueUrl Issue url
 * @param authorAssociation Author association
 * @param performedViaGithubApp Performed via github app
 * @param reactions Reactions
 * @param pin Pin
 * @param minimized Minimized
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-comment")
public record GitHubIssueComment(
        long id,
        String nodeId,
        String url,
        @Nullable String body,
        @Nullable String bodyText,
        @Nullable String bodyHtml,
        String htmlUrl,
        @Nullable GitHubSimpleUser user,
        Instant createdAt,
        Instant updatedAt,
        String issueUrl,
        @Nullable GitHubAuthorAssociation authorAssociation,
        @Nullable GitHubIntegration performedViaGithubApp,
        @Nullable GitHubReactionRollup reactions,
        @Nullable PinnedComment pin,
        @Nullable Minimized minimized) {

    public GitHubIssueComment {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(issueUrl, "issueUrl");
    }

    /**
     * Pinned Issue Comment. Context around who pinned an issue comment and when it was pinned.
     *
     * @param pinnedAt Pinned at
     * @param pinnedBy Pinned by
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/pinned-issue-comment")
    public record PinnedComment(Instant pinnedAt, @Nullable GitHubSimpleUser pinnedBy) {
        public PinnedComment {
            requireNonNull(pinnedAt, "pinnedAt");
        }
    }

    /**
     * Minimized Issue Comment. Details about why an issue comment was minimized.
     *
     * @param reason The reason the comment was minimized.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/issue-comment-minimized")
    public record Minimized(@Nullable String reason) {}
}

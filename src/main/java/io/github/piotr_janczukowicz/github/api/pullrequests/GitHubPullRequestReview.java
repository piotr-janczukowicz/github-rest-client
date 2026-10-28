package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/** Pull request review. */
@CatalogSchema("components/schemas/pull-request-review")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestReview(
        long id,
        String nodeId,
        @Nullable GitHubSimpleUser user,
        String body,
        String state,
        String htmlUrl,
        String pullRequestUrl,
        @JsonProperty("_links") GitHubPullRequestReviewLinks links,
        @Nullable Instant submittedAt,
        @Nullable String commitId,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        GitHubAuthorAssociation authorAssociation) {

    public GitHubPullRequestReview {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(body, "body");
        requireNonNull(state, "state");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(pullRequestUrl, "pullRequestUrl");
        requireNonNull(links, "links");
        requireNonNull(authorAssociation, "authorAssociation");
    }
}

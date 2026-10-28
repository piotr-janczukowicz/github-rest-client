package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request review comment links.
 *
 * @param self Self
 * @param html Html
 * @param pullRequest Pull request
 */
@CatalogSchema("components/schemas/pull-request-review-comment/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestReviewCommentLinks(GitHubLink self, GitHubLink html, GitHubLink pullRequest) {

    public GitHubPullRequestReviewCommentLinks {
        requireNonNull(self, "self");
        requireNonNull(html, "html");
        requireNonNull(pullRequest, "pullRequest");
    }
}

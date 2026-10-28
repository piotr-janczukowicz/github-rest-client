package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request review links.
 *
 * @param html Html review URL
 * @param pullRequest Pull request API URL
 */
@CatalogSchema("components/schemas/pull-request-review/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestReviewLinks(
        GitHubLink html, @JsonProperty("pull_request") GitHubLink pullRequest) {

    public GitHubPullRequestReviewLinks {
        requireNonNull(html, "html");
        requireNonNull(pullRequest, "pullRequest");
    }
}

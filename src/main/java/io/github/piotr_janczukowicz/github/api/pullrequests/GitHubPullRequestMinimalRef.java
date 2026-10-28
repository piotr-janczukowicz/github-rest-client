package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Minimal pull request branch reference.
 *
 * @param ref Branch ref
 * @param sha Commit SHA
 * @param repo Repository
 */
@CatalogSchema("components/schemas/pull-request-minimal/properties/head")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestMinimalRef(String ref, String sha, GitHubPullRequestMinimalRepo repo) {

    public GitHubPullRequestMinimalRef {
        requireNonNull(ref, "ref");
        requireNonNull(sha, "sha");
        requireNonNull(repo, "repo");
    }
}

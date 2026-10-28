package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Minimal pull request.
 *
 * @param id Pull request id
 * @param number Pull request number
 * @param url Pull request API URL
 * @param head Head branch
 * @param base Base branch
 */
@CatalogSchema("components/schemas/pull-request-minimal")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestMinimal(
        long id, int number, String url, GitHubPullRequestMinimalRef head, GitHubPullRequestMinimalRef base) {

    public GitHubPullRequestMinimal {
        requireNonNull(url, "url");
        requireNonNull(head, "head");
        requireNonNull(base, "base");
    }
}

package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request stack base reference.
 *
 * @param ref Base ref of the stack
 * @param sha Base SHA of the stack
 */
@CatalogSchema("components/schemas/pull-request-stack/properties/base")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestStackBase(String ref, String sha) {

    public GitHubPullRequestStackBase {
        requireNonNull(ref, "ref");
        requireNonNull(sha, "sha");
    }
}

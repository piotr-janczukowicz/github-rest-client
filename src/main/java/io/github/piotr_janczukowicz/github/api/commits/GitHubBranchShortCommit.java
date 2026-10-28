package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Commit reference on a short branch.
 *
 * @param sha Sha
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/branch-short/properties/commit")
@RecordBuilder
public record GitHubBranchShortCommit(String sha, String url) {

    public GitHubBranchShortCommit {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}

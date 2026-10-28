package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Short branch commit reference.
 *
 * @param sha Sha
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/short-branch/properties/commit")
public record GitHubShortBranchCommit(String sha, String url) {

    public GitHubShortBranchCommit {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}

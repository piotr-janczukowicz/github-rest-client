package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Commit tree reference.
 *
 * @param sha Sha
 * @param url Url
 */
@CatalogSchema("components/schemas/commit/properties/commit/properties/tree")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCommitTree(String sha, String url) {
    public GitHubCommitTree {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}

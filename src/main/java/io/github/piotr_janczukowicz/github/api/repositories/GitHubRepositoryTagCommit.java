package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Tag commit reference.
 *
 * @param sha Sha
 * @param url Url
 */
@CatalogSchema("components/schemas/tag/properties/commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubRepositoryTagCommit(String sha, String url) {

    public GitHubRepositoryTagCommit {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}

package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Short Blob.
 *
 * @param url Url
 * @param sha Sha
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/short-blob")
public record GitHubShortBlob(String url, String sha) {

    public GitHubShortBlob {
        requireNonNull(url, "url");
        requireNonNull(sha, "sha");
    }
}

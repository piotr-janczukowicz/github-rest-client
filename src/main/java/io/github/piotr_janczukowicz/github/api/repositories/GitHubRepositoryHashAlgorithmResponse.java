package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Repository hash algorithm response.
 *
 * @param hashAlgorithm The Git hash algorithm used by this repository.
 */
@CatalogSchema("components/schemas/repository-hash-algorithm")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryHashAlgorithmResponse(GitHubRepositoryHashAlgorithm hashAlgorithm) {
    public GitHubRepositoryHashAlgorithmResponse {
        requireNonNull(hashAlgorithm, "hashAlgorithm");
    }
}

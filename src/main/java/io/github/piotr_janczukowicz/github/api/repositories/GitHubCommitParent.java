package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Commit parent reference.
 *
 * @param sha Sha
 * @param url Url
 * @param htmlUrl Html url
 */
@CatalogSchema("components/schemas/commit/properties/parents/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCommitParent(
        String sha, String url, @Nullable String htmlUrl) {
    public GitHubCommitParent {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}

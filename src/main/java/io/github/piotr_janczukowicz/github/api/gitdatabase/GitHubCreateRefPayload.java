package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Create reference.
 *
 * @param ref The name of the fully qualified reference (ie: {@code refs/heads/master}). If it doesn't start with 'refs'
 *     and have at least two slashes, it will be rejected.
 * @param sha The SHA1 value for this reference.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/git/create-ref/requestBody")
public record GitHubCreateRefPayload(String ref, String sha) {

    public GitHubCreateRefPayload {
        requireNonNull(ref, "ref");
        requireNonNull(sha, "sha");
    }
}

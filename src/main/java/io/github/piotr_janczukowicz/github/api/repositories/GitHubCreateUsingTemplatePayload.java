package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create repository using a template.
 *
 * @param owner The organization or person who will own the new repository.
 * @param name The name of the new repository.
 * @param description A short description of the new repository.
 * @param includeAllBranches Set to {@code true} to include all branches from the template repository.
 * @param isPrivate Whether the new repository is private.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-using-template/requestBody")
public record GitHubCreateUsingTemplatePayload(
        @Nullable String owner,
        String name,
        @Nullable String description,
        @Nullable Boolean includeAllBranches,
        @Nullable @JsonProperty("private") Boolean isPrivate) {
    public GitHubCreateUsingTemplatePayload {
        requireNonNull(name, "name");
    }
}

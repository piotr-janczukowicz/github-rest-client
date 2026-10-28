package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit tree.
 *
 * @param url Url
 * @param sha Sha
 */
@CatalogSchema("components/schemas/file-commit/properties/commit/properties/tree")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitTree(
        @Nullable String url, @Nullable String sha) {}

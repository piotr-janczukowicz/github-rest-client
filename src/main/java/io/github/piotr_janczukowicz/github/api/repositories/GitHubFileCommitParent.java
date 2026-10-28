package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit parent.
 *
 * @param url Url
 * @param htmlUrl Html url
 * @param sha Sha
 */
@CatalogSchema("components/schemas/file-commit/properties/commit/properties/parents/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitParent(
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable String sha) {}

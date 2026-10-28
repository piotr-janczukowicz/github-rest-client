package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Commit diff stats.
 *
 * @param additions Additions
 * @param deletions Deletions
 * @param total Total
 */
@CatalogSchema("components/schemas/commit/properties/stats")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCommitStats(
        @Nullable Integer additions,
        @Nullable Integer deletions,
        @Nullable Integer total) {}

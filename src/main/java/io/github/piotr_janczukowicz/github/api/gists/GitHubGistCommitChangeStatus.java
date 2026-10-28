package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Line change counts of a gist commit.
 *
 * @param total Total
 * @param additions Additions
 * @param deletions Deletions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-commit/properties/change_status")
@RecordBuilder
public record GitHubGistCommitChangeStatus(
        @Nullable Integer total,
        @Nullable Integer additions,
        @Nullable Integer deletions) {}

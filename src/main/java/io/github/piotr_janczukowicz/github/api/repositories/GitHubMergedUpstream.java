package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Merged upstream.
 *
 * @param message Message
 * @param mergeType Merge type
 * @param baseBranch Base branch
 */
@CatalogSchema("components/schemas/merged-upstream")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubMergedUpstream(
        @Nullable String message,
        @Nullable GitHubMergedUpstreamMergeType mergeType,
        @Nullable String baseBranch) {}

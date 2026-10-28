package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Successful deletion of a code scanning analysis
 *
 * @param nextAnalysisUrl Next deletable analysis in chain, without last analysis deletion confirmation
 * @param confirmDeleteUrl Next deletable analysis in chain, with last analysis deletion confirmation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-analysis-deletion")
public record GitHubCodeScanningAnalysisDeletion(
        @Nullable String nextAnalysisUrl, @Nullable String confirmDeleteUrl) {}

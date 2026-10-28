package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning analysis tool.
 *
 * @param name The name of the tool used to generate the code scanning analysis.
 * @param version The version of the tool used to generate the code scanning analysis.
 * @param guid The GUID of the tool used to generate the code scanning analysis, if provided in the uploaded SARIF data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-analysis-tool")
public record GitHubCodeScanningAnalysisTool(
        @Nullable String name,
        @Nullable String version,
        @Nullable String guid) {}

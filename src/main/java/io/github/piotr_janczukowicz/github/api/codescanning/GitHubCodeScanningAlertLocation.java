package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Describe a region within a file for the alert.
 *
 * @param path Path.
 * @param startLine Start line.
 * @param endLine End line.
 * @param startColumn Start column.
 * @param endColumn End column.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-location")
public record GitHubCodeScanningAlertLocation(
        @Nullable String path,
        @Nullable Integer startLine,
        @Nullable Integer endLine,
        @Nullable Integer startColumn,
        @Nullable Integer endColumn) {}

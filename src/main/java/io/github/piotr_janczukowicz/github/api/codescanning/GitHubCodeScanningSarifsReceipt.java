package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning sarifs receipt.
 *
 * @param id An identifier for the upload.
 * @param url The REST API URL for checking the status of the upload.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-sarifs-receipt")
public record GitHubCodeScanningSarifsReceipt(
        @Nullable String id, @Nullable String url) {}

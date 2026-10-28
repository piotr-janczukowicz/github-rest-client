package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning sarifs status.
 *
 * @param processingStatus `pending` files have not yet been processed, while `complete` means results from the SARIF
 *     have been stored.
 * @param analysesUrl The REST API URL for getting the analyses associated with the upload.
 * @param errors Any errors that ocurred during processing of the delivery.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-sarifs-status")
public record GitHubCodeScanningSarifsStatus(
        @Nullable GitHubCodeScanningSarifsStatusProcessingStatus processingStatus,
        @Nullable String analysesUrl,
        @Nullable List<String> errors) {}

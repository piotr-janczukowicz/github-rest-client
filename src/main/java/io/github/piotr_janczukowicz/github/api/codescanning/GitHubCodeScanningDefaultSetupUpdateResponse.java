package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * You can use `run_url` to track the status of the run.
 *
 * @param runId ID of the corresponding run.
 * @param runUrl URL of the corresponding run.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-default-setup-update-response")
public record GitHubCodeScanningDefaultSetupUpdateResponse(
        @Nullable Long runId, @Nullable String runUrl) {}
